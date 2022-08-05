package hello.instacloneproject.repository;


import hello.instacloneproject.domain.Follow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FollowRepository {

    private final EntityManager em;


    public void save(Follow follow){
        em.persist(follow);
    }


    public List<Follow> findByFollowingEmail(String email){
        return em.createQuery("select f from Follow f where f.followingUser.email = :email",Follow.class)
                .setParameter("email",email)
                .getResultList();

    }



    public List<Follow> findByFollowedEmail(String email){
        return em.createQuery("select f from Follow f where f.followedUser.email = :email",Follow.class)
                .setParameter("email",email)
                .getResultList();

    }

    public Optional<Follow> findByFollowingEmailAndFollowerEmail(String followingEmail,String followerEmail){
        return em.createQuery("select f from Follow f where f.followedUser.email =:followerEamil and f.followingUser.email =:followingEmail",Follow.class)
                .setParameter("followerEamil",followerEmail)
                .setParameter("followingEmail",followingEmail)
                .getResultList().stream().findFirst();
    }

    public void delete(Follow follow){
        em.remove(follow);
    }

}
