package hello.instacloneproject.repository.dto.user;

import hello.instacloneproject.domain.User;
import lombok.*;

@Builder
@Data
public class UserProfileDto {
    private User user;
    private boolean loginUser;
}
