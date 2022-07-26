package hello.instacloneproject.service;
import hello.instacloneproject.domain.UploadFile;
import hello.instacloneproject.domain.User;
import hello.instacloneproject.file.FileStore;
import hello.instacloneproject.repository.UserRepository;
import hello.instacloneproject.dto.user.UserSignupDto;
import hello.instacloneproject.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileStore fileStore;

    @Value("@{file.dir}")
    private String fileDir;
    @Transactional
    public void join(UserSignupDto userLoginDto){
        validateDuplicateUser(userLoginDto);
        userRepository.save(dtoToUser(userLoginDto));
    }

    @Transactional
    public void update(UserUpdateDto userUpdateDto) throws IOException {
        User findUser = userRepository.findById(userUpdateDto.getId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(findUser.getProfileImgFile() != null){
            File file = new File(fileDir + findUser.getProfileImgFile().getStoreFileName());
            file.delete();
        }

        UploadFile uploadFile = fileStore.storeFile(userUpdateDto.getProfileImgFile());

        findUser.setPassword(encoder.encode(userUpdateDto.getPassword()));
        findUser.setPhone(userUpdateDto.getPhone());
        findUser.setName(userUpdateDto.getName());
        findUser.setProfileImgFile(uploadFile);
        findUser.setTitle(userUpdateDto.getTitle());

    }

    public User findByEmail(String email){
        Optional<User> findUser = userRepository.findByEmail(email);
        return findUser.get();
    }

    public User findById(Long id){
        return userRepository.findById(id);
    }

    public User findByIdWithPostList(Long id ){
        return userRepository.findByIdWithPost(id).get();
    }

    public User findByEmailWithPostList(String email){
        return userRepository.findByEmailWithPost(email);
    }

    private void validateDuplicateUser(UserSignupDto userLoginDto) {
        Optional<User> findUser = userRepository.findByEmail(userLoginDto.getEmail());
        if(findUser.isPresent()){
            throw new IllegalStateException("이미 존재하는 email입니다.");
        }
    }

    /**
     * @param dto
     * @return User
     * 회원가입 dto 를 이용하여 User 객체 생성
     */
    private User dtoToUser(UserSignupDto dto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .name(dto.getName())
                .profileImgFile(null)
                .title(null)
                .website(null)
                .build();

    }



}
