package com.example.x_ray.service.user;

import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.entity.User;
import com.example.x_ray.repository.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    final BCryptPasswordEncoder passwordEncoder;
    final UserRepository userRepository;

    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userDto;
    }

    @Override
    public UserDto findUserByNickName(String nickName) {
        User user = userRepository.findUser(nickName);
        return new UserDto(
                user.getNickName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    @Override
    public UserDto validateLogin(UserDto userDto) {
        UserDto targetUser = findUserByNickName(userDto.getNickName());
        if(targetUser == null)
            return null;
        if(passwordEncoder.matches(userDto.getPassword(), targetUser.getPassword())){
            return targetUser;
        }else
            return null;
    }
}
