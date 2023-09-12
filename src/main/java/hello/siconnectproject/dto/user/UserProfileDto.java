package hello.siconnectproject.dto.user;

import hello.siconnectproject.domain.User;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProfileDto {

    private User user;
    private boolean loginUser;
    private boolean follow;
    private int followerCount;
    private int followingCount;
    private int postCount;


}
