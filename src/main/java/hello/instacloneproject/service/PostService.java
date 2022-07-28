package hello.instacloneproject.service;

import hello.instacloneproject.domain.Likes;
import hello.instacloneproject.domain.Post;
import hello.instacloneproject.domain.UploadFile;
import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.Post.PostDto;
import hello.instacloneproject.dto.Post.PostInfoDto;
import hello.instacloneproject.dto.Post.PostUpdateDto;
import hello.instacloneproject.dto.Post.PostUploadDto;
import hello.instacloneproject.file.FileStore;
import hello.instacloneproject.repository.LikesRepository;
import hello.instacloneproject.repository.PostRepository;
import hello.instacloneproject.repository.UserRepository;
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

    public PostDto getPostDtoById(Long id){
        Post findPost = postRepository.findById(id);
        return PostDto.builder()
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
        List<PostInfoDto> result = new ArrayList<>();

        for(Post story:storyList){
            boolean state = likesRepository.findByPostIdAndUserEmail(story.getId(), userEmail).isPresent();
            PostInfoDto dto = PostInfoDto.builder()
                    .id(story.getId())
                    .postImgUrl(story.getPostImgFile().getStoreFileName())
                    .dateTime(story.getDateTime())
                    .tag(story.getTag())
                    .text(story.getText())
                    .postUploader(story.getUser())
                    .uploader(story.getUser().getEmail().equals(userEmail))
                    .likeCount(story.getLikeList().size())
                    .likeState(state)
                    .build();
            result.add(dto);
        }
        Page<PostInfoDto> page = new PageImpl<>(result);
        return page;
    }
}
