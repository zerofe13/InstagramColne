package hello.instacloneproject.service;

import hello.instacloneproject.domain.Follow;
import hello.instacloneproject.dto.Follow.FollowDto;
import hello.instacloneproject.repository.FollowRepository;
import hello.instacloneproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        boolean bidirect = checkFollow(followedEmail,followingEmail);
        if(bidirect){
            Optional<Follow> findFollow = followRepository.findByFollowingEmailAndFollowerEmail(followedEmail, followingEmail);
            findFollow.get().setBidirectional(true);
        }

        Follow follow = Follow.builder()
                .followingUser(userRepository.findByEmail(followingEmail).get())
                .followedUser(userRepository.findByEmail(followedEmail).get())
                .bidirectional(bidirect)
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
        Optional<Follow> findFollow = followRepository.findByFollowingEmailAndFollowerEmail(followingEmail, followedEmail);
        return findFollow.isPresent();
    }

    public List<FollowDto> getFollowedList(String userEmail){
        List<FollowDto> result = new ArrayList<>();
        List<Follow> findFollows = followRepository.findByFollowedEmail(userEmail);

        for (Follow findFollow: findFollows) {
            FollowDto followDto = FollowDto.builder()
                    .id(findFollow.getFollowingUser().getId())
                    .profileImgUrl(findFollow.getFollowingUser().getProfileImgUrl())
                    .email(findFollow.getFollowingUser().getEmail())
                    .name(findFollow.getFollowingUser().getName())
                    .followState(findFollow.isBidirectional())
                    .loginUser(userEmail.equals(findFollow.getFollowingUser().getEmail()))
                    .build();
            result.add(followDto);

        }

        return result;

    }

    public List<FollowDto> getFollowingList(String userEmail){
        List<FollowDto> result = new ArrayList<>();
        List<Follow> findFollows = followRepository.findByFollowingEmail(userEmail);

        for (Follow findFollow: findFollows) {
            FollowDto followDto = FollowDto.builder()
                    .id(findFollow.getFollowedUser().getId())
                    .profileImgUrl(findFollow.getFollowedUser().getProfileImgUrl())
                    .email(findFollow.getFollowedUser().getEmail())
                    .name(findFollow.getFollowedUser().getName())
                    .followState(findFollow.isBidirectional())
                    .loginUser(userEmail.equals(findFollow.getFollowingUser().getEmail()))
                    .build();
            result.add(followDto);

        }

        return result;

    }

    @Transactional
    public void unFollow(String followingEmail, String followedEmail){
        List<Follow> findFollows = followRepository.findByFollowingEmail(followingEmail);
        for (Follow findFollow : findFollows) {
            if (findFollow.getFollowedUser().getEmail().equals(followedEmail)) {
                if (findFollow.isBidirectional()){
                    Optional<Follow> find = followRepository.findByFollowingEmailAndFollowerEmail(followedEmail, followingEmail);
                    find.get().setBidirectional(false);
                }
                followRepository.delete(findFollow);
                break;
            }
        }
    }

}
