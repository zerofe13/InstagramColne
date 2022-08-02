package hello.instacloneproject.dto.Post;

import hello.instacloneproject.domain.UploadFile;
import lombok.Data;

@Data
public class PopularPostDto {

    private long id;
    private UploadFile postImgFile;
    private long likesCount;

    public PopularPostDto(long id, UploadFile postImgFile, long likesCount) {
        this.id = id;
        this.postImgFile = postImgFile;
        this.likesCount = likesCount;
    }
}
