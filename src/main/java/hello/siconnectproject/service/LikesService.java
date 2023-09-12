package hello.siconnectproject.service;

import hello.siconnectproject.domain.Likes;
import hello.siconnectproject.domain.Post;
import hello.siconnectproject.domain.User;
import hello.siconnectproject.repository.LikesRepository;
import hello.siconnectproject.repository.PostRepository;
import hello.siconnectproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void like(long postId, String userEmail){
        Post findPost = postRepository.findById(postId);
        Optional<User> findUser = userRepository.findByEmail(userEmail);
        likesRepository.save(findPost,findUser.get());
    }

    @Transactional
    public void unlike(long postId,String userEmail){
        likesRepository.delete(postId,userEmail);
    }

    public boolean likeState(long postId,String userEmail){
        Optional<Likes> findLikes = likesRepository.findByPostIdAndUserEmail(postId, userEmail);
        return findLikes.isPresent();
    }

}
