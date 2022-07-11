package hello.instacloneproject.service;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.repository.UserRepository;
import hello.instacloneproject.repository.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void join(UserLoginDto userLoginDto){
//        validateDuplicateUser(user);
        userRepository.save(dtoToUser(userLoginDto));
    }

    private void validateDuplicateUser(User user) {
        List<User> findUser = userRepository.findByEmail(user.getEmail());
        if(!findUser.isEmpty()){
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
