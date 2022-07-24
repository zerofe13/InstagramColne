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
        Optional<Follow> findFollow = followRepository.findByFollowingEmailAndFollowerEmail(followingEmail, followedEmail);
        return findFollow.isPresent();
    }

    /**
     * 현재 프로필에 대한 팔로워 리스트정보를 가지고오는 메소드
     * 로그인 된 유저가 해당 팔로워를 팔로잉 하고있는지 팔로워가 자기자신인지에 대한 정보도 제공해준다.
     * @param profileEmail
     * @param userEmail
     * @return
     */

    public List<FollowDto> getFollowedList(String profileEmail,String userEmail){
        List<FollowDto> result = new ArrayList<>();
        List<Follow> findFollows = followRepository.findByFollowedEmail(profileEmail);
        List<Follow> userFollows = followRepository.findByFollowingEmail(userEmail);


        for (Follow findFollow: findFollows) {
            boolean state = userFollows.stream().anyMatch(userFollow -> findFollow.getFollowingUser().getEmail().equals(userFollow.getFollowedUser().getEmail()));

            FollowDto followDto = FollowDto.builder()
                    .id(findFollow.getFollowingUser().getId())
                    .profileImgUrl(findFollow.getFollowingUser().getProfileImgUrl())
                    .email(findFollow.getFollowingUser().getEmail())
                    .name(findFollow.getFollowingUser().getName())
                    .followState(state)
                    .loginUser(userEmail.equals(findFollow.getFollowingUser().getEmail()))
                    .build();
            result.add(followDto);

        }

        return result;

    }

    public List<FollowDto> getFollowingList(String profileEmail,String userEmail){
        List<FollowDto> result = new ArrayList<>();
        List<Follow> findFollows = followRepository.findByFollowingEmail(profileEmail);
        List<Follow> userFollows = followRepository.findByFollowingEmail(userEmail);

        for (Follow findFollow: findFollows) {
            boolean state = userFollows.stream().anyMatch(userFollow -> findFollow.getFollowedUser().getEmail().equals(userFollow.getFollowedUser().getEmail()));
            FollowDto followDto = FollowDto.builder()
                    .id(findFollow.getFollowedUser().getId())
                    .profileImgUrl(findFollow.getFollowedUser().getProfileImgUrl())
                    .email(findFollow.getFollowedUser().getEmail())
                    .name(findFollow.getFollowedUser().getName())
                    .followState(state)
                    .loginUser(userEmail.equals(findFollow.getFollowedUser().getEmail()))
                    .build();
            result.add(followDto);

        }

        return result;

    }

    @Transactional
    public void unFollow(String followingEmail, String followedEmail){
        List<Follow> findFollows = followRepository.findByFollowingEmail(followingEmail);
        findFollows.stream().filter(findFollow -> findFollow.getFollowedUser().getEmail().equals(followedEmail)).findFirst().ifPresent(followRepository::delete);
    }

}
