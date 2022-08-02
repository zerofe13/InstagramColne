package hello.instacloneproject.repository;

import hello.instacloneproject.domain.Post;
import hello.instacloneproject.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public User save(User user){
        em.persist(user);
        return user;
    }
    public User findById(Long id){
        return em.find(User.class, id);
    }

    public Optional<User> findByEmail(String email){
        return em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList().stream().findFirst();
    }

    public Optional<User> findByIdWithPost(Long id){
        return em.createQuery("select u from User u left join fetch u.postList where u.id =:id",User.class)
                .setParameter("id",id)
                .getResultList().stream().findFirst();
    }

    public User findByEmailWithPost(String email){
        return em.createQuery("select u from User u left join fetch u.postList p where u.email =: email",User.class)
                .setParameter("email",email)
                .getSingleResult();
    }

}
