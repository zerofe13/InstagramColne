package hello.instacloneproject.repository;


import hello.instacloneproject.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
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

    public void delete(Follow follow){
        em.remove(follow);
    }

}
