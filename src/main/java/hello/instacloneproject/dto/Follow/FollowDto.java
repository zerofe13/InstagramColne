package hello.instacloneproject.dto.Follow;

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
    private String profileImgUrl;
    private boolean followState;
    private boolean loginUser;
}
