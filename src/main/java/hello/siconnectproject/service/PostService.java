package hello.siconnectproject.service;

import hello.siconnectproject.domain.*;
import hello.siconnectproject.dto.Post.*;
import hello.siconnectproject.file.FileStore;
import hello.siconnectproject.repository.LikesRepository;
import hello.siconnectproject.repository.PostRepository;
import hello.siconnectproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final CommentService commentService;
    private final FileStore fileStore;

    @Value("${file.dir}")
    private String fileDir;

    @Transactional
    public void uploadPost(PostUploadDto postUploadDto,String userEmail) throws IOException {
        Optional<User> findUser = userRepository.findByEmail(userEmail);
        UploadFile uploadFile = fileStore.storeFile(postUploadDto.getPostImgFile());
        Post post = new Post(uploadFile,postUploadDto.getTag(), postUploadDto.getText(),findUser.get());
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(PostUpdateDto postUpdateDto){
        Post findPost = postRepository.findById(postUpdateDto.getId());
        findPost.setTag(postUpdateDto.getTag());
        findPost.setText(postUpdateDto.getText());
    }

    @Transactional
    public void deletePost(long postId){
        Post findPost = postRepository.findById(postId);

        File file = new File(fileDir + findPost.getPostImgFile().getStoreFileName());
        file.delete();

        postRepository.delete(findPost);
    }

    public PostUpdateDto getPostDtoById(Long id){
        Post findPost = postRepository.findById(id);
        return PostUpdateDto.builder()
                .id(findPost.getId())
                .tag(findPost.getTag())
                .text(findPost.getText())
                .postImgFile(findPost.getPostImgFile())
                .build();
    }
    public Post findById(long id){
        return postRepository.findById(id);
    }

    public Post findByIdWithLike(long id){
        return postRepository.findByIdWithLikesList(id);
    }

    public Page<PostInfoDto> getStory(String userEmail, Pageable pageable){
        List<Post> storyList = postRepository.findStory(userEmail, pageable);
        List<PostInfoDto> result = getPostInfoDtos(userEmail, storyList);
        Page<PostInfoDto> page = new PageImpl<>(result);
        return page;
    }

    public Page<PostInfoDto> getTagSearch(String tag,String userEmail,Pageable pageable){
        List<Post> searchList = postRepository.tagSearch(tag, pageable);
        List<PostInfoDto> result = getPostInfoDtos(userEmail,searchList);
        Page<PostInfoDto> page = new PageImpl<>(result);
        return page;
    }

    public Page<PostDto> getLikePost(String userEmail,Pageable pageable){
        List<Post> likePosts = postRepository.findLikePost(userEmail, pageable);
        List<PostDto> result = new ArrayList<>();
        for(Post post:likePosts){
            result.add(PostDto.builder()
                    .id(post.getId())
                    .tag(post.getTag())
                    .text(post.getText())
                    .postImgFile(post.getPostImgFile())
                    .build());
        }
        Page<PostDto> page = new PageImpl<>(result);
        return page;
    }

    public List<PopularPostDto> getPopularPosts(){
        return postRepository.findPopularPost();

    }

    private List<PostInfoDto> getPostInfoDtos(String userEmail, List<Post> storyList) {
        List<PostInfoDto> result = new ArrayList<>();

        for(Post story: storyList){
            List<Likes> findLikes = likesRepository.findByPostId(story.getId());
            boolean state = likesRepository.findByPostIdAndUserEmail(story.getId(), userEmail).isPresent();
            List<Comment> commentList = commentService.getPostComments(story.getId());
            PostInfoDto dto = PostInfoDto.builder()
                    .id(story.getId())
                    .postImgUrl(story.getPostImgFile().getStoreFileName())
                    .dateTime(story.getDateTime())
                    .tag(story.getTag())
                    .text(story.getText())
                    .postUploader(story.getUser())
                    .uploader(story.getUser().getEmail().equals(userEmail))
                    .likeCount(findLikes.size())
                    .likeState(state)
                    .commentList(commentList)
                    .build();
            result.add(dto);
        }
        return result;
    }
}
