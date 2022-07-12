package hello.instacloneproject.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "USER_TABLE")
public class User implements UserDetails { // 스프링 시큐리티를 위해 UserDetails 구현

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    private String email;
    private String password;
    private String phone;
    private String name;

    @Builder
    public User(long id, String email, String password, String phone, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
