package com.example.x_ray.service.user;

import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.entity.User;
import com.example.x_ray.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserServiceImpl implements UserService{

    final BCryptPasswordEncoder passwordEncoder;
    final UserRepository userRepository;

    public UserDto entityToDto(User user){
        return new UserDto(
                user.getNickName(),
                user.getEmail(),
                user.getPassword()
        );
    }
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userRepository.createUser(userDto);
        return entityToDto(user);
    }

    @Override
    @Transactional
    public UserDto findUserByNickName(String nickName) {
        User user = userRepository.findUser(nickName);
        return entityToDto(user);
    }

    @Override
    @Transactional
    public UserDto validateLogin(UserDto userDto) {
        log.info("nickname = [{}]" , userDto.getNickName());
        UserDto targetUser = findUserByNickName(userDto.getNickName());
        if(targetUser == null){
            log.info("no User Available");
            return null;
        }
        if(passwordEncoder.matches(userDto.getPassword(), targetUser.getPassword())){
            return targetUser;
        }else{
            log.info("password misMatch");
            return null;
        }

    }

    @Override
    @Transactional
    public List<UserDto> findAllUsers(){
        return  userRepository.findAll().stream().map(v->
                entityToDto(v)).collect(Collectors.toList());
    }
}
