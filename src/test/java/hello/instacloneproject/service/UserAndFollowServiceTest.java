package hello.instacloneproject.service;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.Follow.FollowDto;
import hello.instacloneproject.repository.FollowRepository;
import hello.instacloneproject.repository.UserRepository;
import hello.instacloneproject.dto.user.UserSignupDto;
import hello.instacloneproject.dto.user.UserUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class UserAndFollowServiceTest {

    @Autowired
    FollowService followService;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @BeforeEach
    void createUser(){
        UserSignupDto userA = UserSignupDto.builder().email("a@a.com")
                .password("123")
                .name("a")
                .phone("123123")
                .build();

        UserSignupDto userB = UserSignupDto.builder().email("b@b.com")
                .password("123")
                .name("b")
                .phone("123123")
                .build();

        UserSignupDto userC = UserSignupDto.builder().email("c@c.com")
                .password("123")
                .name("c")
                .phone("123123")
                .build();

        UserSignupDto userD = UserSignupDto.builder().email("d@d.com")
                .password("123")
                .name("d")
                .phone("123123")
                .build();
        userService.join(userA);
        userService.join(userB);
        userService.join(userC);
        userService.join(userD);

    }
    @Test
    void signup() {
        //given
        String email = "t@t.com";
        UserSignupDto userT = UserSignupDto.builder().email(email)
                .password("123")
                .name("t")
                .phone("0000")
                .build();
        //when
        userService.join(userT);
        //then
        User findUser = userService.findByEmail(email);
        User findByIdUser = userService.findById(findUser.getId());
        assertThat(userService.findByEmail(userT.getEmail())).isEqualTo(findUser);
        assertThat(userService.findByEmail(email).getPhone()).isEqualTo("0000");
        assertThat(findUser).isEqualTo(findByIdUser);
    }

    @Test
    void overlapId(){
        //given
        String email = "a@a.com";
        UserSignupDto userT = UserSignupDto.builder().email(email)
                .password("123")
                .name("t")
                .phone("123123")
                .build();
        //when
        Throwable thrown = catchThrowable(()->userService.join(userT));
        //then
        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasMessageContaining("이미 존재하는 email입니다.");
    }
//
//    @Test
//    void update() throws IOException {
//        //given
//        Optional<User> findUser = userRepository.findByEmail("d@d.com");
//        UserUpdateDto updateDto = UserUpdateDto.builder()
//                .id(findUser.get().getId())
//                .email(findUser.get().getEmail())
//                .password(findUser.get().getPassword())
//                .name("update Test") //이름변경
//                .phone(findUser.get().getPhone())
//                .build();
//        //when
//        userService.update(updateDto);
//        //then
//        assertThat(userService.findByEmail("d@d.com").getName()).isEqualTo("update Test");
//
//
//    }

    @Test
    void follow(){
        //given
        String email = "t@t.com";
        UserSignupDto userT = UserSignupDto.builder().email(email)
                .password("123")
                .name("t")
                .phone("123123")
                .build();

        userService.join(userT);
        //when
        followService.save(email,"a@a.com");
        followService.save(email,"b@b.com");
        followService.save(email,"c@c.com");
        followService.save("a@a.com",email);
        followService.save("d@d.com",email);
        followService.save("a@a.com","d@d.com");
        followService.save("a@a.com","c@c.com");
        followService.save("b@b.com","a@a.com");
        //then
        assertThat(followService.checkFollow(email,"a@a.com")).isTrue();
        assertThat(followService.countFollowing(email)).isEqualTo(3);
        assertThat(followService.countFollower(email)).isEqualTo(2);
        assertThat(followService.countFollower("a@a.com")).isEqualTo(2);



    }

    @Test
    void unFollow(){
        String email = "t@t.com";
        UserSignupDto userT = UserSignupDto.builder().email(email)
                .password("123")
                .name("t")
                .phone("123123")
                .build();

        userService.join(userT);
        //when
        followService.save(email,"a@a.com");
        followService.save(email,"b@b.com");
        followService.save(email,"c@c.com");
        followService.save("b@b.com","a@a.com");
        followService.save("a@a.com",email);

        followService.unFollow(email,"a@a.com");//unfollow
        //then
        assertThat(followService.checkFollow(email,"a@a.com")).isFalse();
        assertThat(followService.countFollowing(email)).isEqualTo(2);
        assertThat(followService.countFollower(email)).isEqualTo(1);
        assertThat(followService.countFollower("a@a.com")).isEqualTo(1);


    }

    @Test
    void getFollowers(){
        //given
        String email = "t@t.com";
        UserSignupDto userT = UserSignupDto.builder().email(email)
                .password("123")
                .name("t")
                .phone("123123")
                .build();

        userService.join(userT);
        //when
        followService.save(email,"a@a.com");
        followService.save(email,"b@b.com");
        followService.save(email,"c@c.com");
        followService.save("a@a.com",email);
        followService.save("d@d.com",email);
        followService.save("a@a.com","d@d.com");
        followService.save("a@a.com","c@c.com");
        followService.save("b@b.com","a@a.com");

        //then
        List<FollowDto> followingList = followService.getFollowingList(email,email);
        assertThat(followingList).extracting("email").contains("a@a.com","b@b.com","c@c.com");
        List<FollowDto> followedList = followService.getFollowedList(email,email);
        assertThat(followedList).hasSize(2);

    }
}