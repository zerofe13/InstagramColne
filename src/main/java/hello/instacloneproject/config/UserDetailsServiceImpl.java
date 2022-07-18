package hello.instacloneproject.config;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUser");
        Optional<User> findUser = userRepository.findByEmail(username);
        if(!findUser.isPresent()){
            log.info(" 실패");
            throw new UsernameNotFoundException("입력한 계정을 찾을 수 없습니다.");
        }
        return User.builder()
                .email(findUser.get().getEmail())
                .password(findUser.get().getPassword())
                .name(findUser.get().getName())
                .phone(findUser.get().getPhone())
                .profileImgUrl(findUser.get().getProfileImgUrl())
                .title(findUser.get().getTitle())
                .website(findUser.get().getWebsite())
                .build();


    }
}
