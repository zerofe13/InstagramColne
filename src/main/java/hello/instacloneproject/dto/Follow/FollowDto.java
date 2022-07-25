package hello.instacloneproject.dto.Follow;

import hello.instacloneproject.domain.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {

    private long id;
    private String email;
    private String name;
    private UploadFile profileFile;
    private boolean followState;
    private boolean loginUser;
}
