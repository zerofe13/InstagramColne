package hello.siconnectproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_TABLE")
public class User { // 스프링 시큐리티를 위해 UserDetails 구현

    @Id @GeneratedValue
    @Column(name = "user_id")
    private long id;

    private String email;
    private String password;
    private String phone;
    private String name;

    @Embedded
    private UploadFile profileImgFile;

    private String title;
    private  String website;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();


    @Builder
    public User(long id, String email, String password, String phone, String name,UploadFile profileImgFile, String title, String website) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.profileImgFile = profileImgFile;
        this.title = title;
        this.website = website;
    }



}
