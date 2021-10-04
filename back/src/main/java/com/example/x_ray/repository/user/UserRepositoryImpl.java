package com.example.x_ray.repository.user;

import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @PersistenceContext
    private EntityManager em;


    @Override
    public User createUser(UserDto userDto) {
        User user = new User(
                userDto.getNickName(),
                userDto.getPassword(),
                userDto.getEmail(),
                new Date(),
                null
        );
        em.persist(user);
        return user;
    }

    public User findUser(String nickName){
        return em.createQuery("select u from User u where u.nickName like : nickName" , User.class)
                .setParameter("nickName", nickName)
                .getResultList().get(0);
    }
}
