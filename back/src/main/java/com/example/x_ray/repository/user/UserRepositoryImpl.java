package com.example.x_ray.repository.user;

import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.entity.QUser;
import com.example.x_ray.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static com.example.x_ray.entity.QUser.*;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository{

    @PersistenceContext
    private EntityManager em;
    final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public User createUser(UserDto userDto) {
        if(findUser(userDto.getNickName()) != null){
            log.info("duplicated user = [{}] ", userDto.getNickName());
            User user = new User(
                    "duplicated user",
                    userDto.getPassword(),
                    userDto.getEmail(),
                    new Date(),
                    null
            );
            return user;
        }else{
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

    }

    public User findUser(String nickName){
        return queryFactory
                .selectFrom(QUser.user)
                .where(QUser.user.nickName.eq(nickName))
                .fetchFirst();
//        List<User> result = em.createQuery("select u from User u where u.nickName like : nickName", User.class)
//                .setParameter("nickName", nickName)
//                .getResultList();
//        if(result.isEmpty())
//            return null;
//        else
//            return result.get(0);
    }

    @Override
    public List<User> findAll() {
        return queryFactory
                .selectFrom(user)
                .fetch();
//        return em.createQuery("select u from User u", User.class)
//                .getResultList();
    }
}
