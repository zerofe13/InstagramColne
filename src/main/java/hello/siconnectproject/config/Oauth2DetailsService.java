package hello.siconnectproject.config;

import hello.siconnectproject.domain.User;
import hello.siconnectproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Oauth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> userMap = oAuth2User.getAttributes();
        String email = (String) userMap.get("email");
        String name = (String) userMap.get("name");
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());

        Optional<User> check = userRepository.findByEmail(email);
        if(!check.isPresent()){
            User user = User.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .phone(null)
                    .build();
            return new PrincipalDetails(userRepository.save(user),userMap);
        }else{
            return new PrincipalDetails(check.get());
        }
    }
}
