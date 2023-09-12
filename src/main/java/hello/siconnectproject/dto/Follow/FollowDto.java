package hello.siconnectproject.dto.Follow;

import hello.siconnectproject.domain.UploadFile;
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
    private UploadFile profileImgFile;
    private boolean followState;
    private boolean loginUser;
}
