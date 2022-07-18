package hello.instacloneproject.service;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.repository.UserRepository;
import hello.instacloneproject.repository.dto.UserDto;
import hello.instacloneproject.repository.dto.UserSignupDto;
import hello.instacloneproject.repository.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void join(UserSignupDto userLoginDto){
        validateDuplicateUser(userLoginDto);
        userRepository.save(dtoToUser(userLoginDto));
    }

    @Transactional
    public void update(UserUpdateDto userUpdateDto){
        User findUser = userRepository.findById(userUpdateDto.getId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        findUser.setPassword(encoder.encode(userUpdateDto.getPassword()));
        findUser.setPhone(userUpdateDto.getPhone());
        findUser.setName(userUpdateDto.getName());
        findUser.setProfileImgUrl(userUpdateDto.getProfileImgUrl());
        findUser.setTitle(userUpdateDto.getTitle());
        findUser.setTitle(userUpdateDto.getTitle());

    }

    public UserDto findByEmail(String Email){
        Optional<User> findUser = userRepository.findByEmail(Email);
        UserDto userDto = UserDto.builder()
                .email(findUser.get().getEmail())
                .id(findUser.get().getId())
                .name(findUser.get().getName())
                .phone(findUser.get().getPhone())
                .profileImgUrl(findUser.get().getProfileImgUrl())
                .title(findUser.get().getTitle())
                .website(findUser.get().getWebsite())
                .build();
        return userDto;
    }

    public UserDto findById(Long id){
        User findUser = userRepository.findById(id);
        UserDto userDto = UserDto.builder()
                .email(findUser.getEmail())
                .id(findUser.getId())
                .name(findUser.getName())
                .phone(findUser.getPhone())
                .profileImgUrl(findUser.getProfileImgUrl())
                .title(findUser.getTitle())
                .website(findUser.getWebsite())
                .build();
        return userDto;
    }

    private void validateDuplicateUser(UserSignupDto userLoginDto) {
        Optional<User> findUser = userRepository.findByEmail(userLoginDto.getEmail());
        if(findUser.isPresent()){
            throw new IllegalStateException("이미 존재하는 email입니다.");
        }
    }

    private User dtoToUser(UserSignupDto dto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .name(dto.getName())
                .profileImgUrl(null)
                .title(null)
                .website(null)
                .build();

    }

}
