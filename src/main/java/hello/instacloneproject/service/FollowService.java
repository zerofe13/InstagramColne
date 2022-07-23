package hello.instacloneproject.service;

import hello.instacloneproject.domain.Follow;
import hello.instacloneproject.repository.FollowRepository;
import hello.instacloneproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(String followingEmail, String followedEmail){
        if(checkFollow(followingEmail,followedEmail)){
            throw new IllegalStateException("이미 팔로우중 입니다");
        }
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
        log.info("================checking===============");
        for (Follow f: findFollows) {
            log.info("f = {},{},{}",f.getId(),f.getFollowedUser(),f.getFollowingUser());

        }
        return findFollows.stream().anyMatch(findFollow -> findFollow.getFollowedUser().getEmail().equals(followedEmail));
    }

    @Transactional
    public void unFollow(String followingEmail, String followedEmail){
        List<Follow> findFollows = followRepository.findByFollowingEmail(followingEmail);
        findFollows.stream().filter(findFollow -> findFollow.getFollowedUser().getEmail().equals(followedEmail)).findFirst().ifPresent(followRepository::delete);
    }

}
