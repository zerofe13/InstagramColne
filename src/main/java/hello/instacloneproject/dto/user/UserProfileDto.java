package hello.instacloneproject.repository.dto.user;

import hello.instacloneproject.domain.User;
import lombok.*;

@Builder
@Data
public class UserProfileDto {

    private UserDto userDto;
    private boolean loginUser;
    private int followerCount;
    private int followingCount;

}
