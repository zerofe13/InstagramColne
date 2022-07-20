package hello.instacloneproject.service;

import hello.instacloneproject.domain.Follow;
import hello.instacloneproject.repository.FollowRepository;
import hello.instacloneproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(String followingEmail, String followedEmail){
        Follow follow = Follow.builder()
                .followingUser(userRepository.findByEmail(followingEmail).get())
                .followedUser(userRepository.findByEmail(followedEmail).get())
                .build();
        followRepository.save(follow);
    }

    public int countFollower(String email){
        List<Follow> byFollowedEmail = followRepository.findByFollowedEmail(email);
        return byFollowedEmail.size();
    }

    public int countFollowing(String email){
        List<Follow> byFollowingEmail = followRepository.findByFollowingEmail(email);
        return byFollowingEmail.size();
    }

    public boolean checkFollow(String followingEmail, String followedEmail){
        List<Follow> findFollows = followRepository.findByFollowingEmail(followingEmail);
        return findFollows.stream().anyMatch(findFollow -> findFollow.getFollowedUser().getEmail() == followedEmail);
    }

    @Transactional
    public void unFollow(String followingEmail, String followedEmail){
        List<Follow> findFollows = followRepository.findByFollowingEmail(followingEmail);
        findFollows.stream().filter(findFollow -> findFollow.getFollowedUser().getEmail() == followedEmail).findFirst().ifPresent(followRepository::delete);
    }

}
