package hello.instacloneproject.service;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.repository.UserRepository;
import hello.instacloneproject.repository.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void join(UserLoginDto userLoginDto){
        validateDuplicateUser(userLoginDto);
        userRepository.save(dtoToUser(userLoginDto));
    }

    private void validateDuplicateUser(UserLoginDto userLoginDto) {
        Optional<User> findUser = userRepository.findByEmail(userLoginDto.getEmail());
        if(findUser.isPresent()){
            throw new IllegalStateException("이미 존재하는 email입니다.");
        }
    }
    private User dtoToUser(UserLoginDto dto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .name(dto.getName())
                .build();

    }

}
