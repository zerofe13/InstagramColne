package hello.siconnectproject.service;

import hello.siconnectproject.domain.Comment;
import hello.siconnectproject.domain.Post;
import hello.siconnectproject.domain.User;
import hello.siconnectproject.dto.Comment.CommentDto;
import hello.siconnectproject.repository.CommentRepository;
import hello.siconnectproject.repository.PostRepository;
import hello.siconnectproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Transactional
    public CommentDto addComment(long postId, String userEmail , String text){
        Post findPost = postRepository.findById(postId);
        Optional<User> findUser = userRepository.findByEmail(userEmail);
        Comment comment = Comment.builder()
                .user(findUser.get())
                .post(findPost)
                .text(text)
                .build();

        Comment result = commentRepository.save(comment);
        return CommentDto.builder()
                .id(result.getId())
                .text(result.getText())
                .user(result.getUser())
                .build();

    }

    public Comment findById(long id){
        return commentRepository.findById(id);
    }

    public List<Comment> getPostComments(long postId){
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public void deleteComment(long id){
        Comment findComment = commentRepository.findById(id);
        commentRepository.delete(findComment);
    }
}
