package hello.instacloneproject.service;

import hello.instacloneproject.domain.Post;
import hello.instacloneproject.domain.UploadFile;
import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.Post.PostUploadDto;
import hello.instacloneproject.file.FileStore;
import hello.instacloneproject.repository.PostRepository;
import hello.instacloneproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileStore fileStore;

    @Transactional
    public void uploadPost(PostUploadDto postUploadDto,String userEmail) throws IOException {
        Optional<User> findUser = userRepository.findByEmail(userEmail);
        UploadFile uploadFile = fileStore.storeFile(postUploadDto.getPostImgFile());
        Post post = new Post(uploadFile,postUploadDto.getTag(), postUploadDto.getText(),findUser.get());
        postRepository.save(post);
    }
}
