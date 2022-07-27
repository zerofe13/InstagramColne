package hello.instacloneproject.service;

import hello.instacloneproject.domain.Likes;
import hello.instacloneproject.domain.Post;
import hello.instacloneproject.domain.User;
import hello.instacloneproject.repository.LikesRepository;
import hello.instacloneproject.repository.PostRepository;
import hello.instacloneproject.repository.UserRepository;
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
