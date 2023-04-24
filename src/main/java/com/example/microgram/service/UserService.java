package com.example.microgram.service;

import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    public UserDto getUserByEmail(String email){
        return UserDto.from(userDao.findUserByEmail(email));
    }

    public UserDto getUserByName(String name){
        return UserDto.from(userDao.findUsersByName(name));
    }

    public UserDto getUserByNickName(String nickName){
        return UserDto.from(userDao.findUserByAccountName(nickName));
    }

    public String isRegisteredEmail(String email){
        return userDao.isRegisteredEmail(email);
    }

    public String login(String accname, String password) {
        UserDto userDtos = UserDto.from(userDao.login(accname, password));
        if (userDtos != null)
            return "Вы зашли";
        else
            return "Такого пользователя нет";
    }

    public UserDto register(String name, String email, String password) {
        var usr = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .enabled(Boolean.TRUE)
                .countFollower(0)
                .countPublication(0)
                .countSubscription(0)
                .build();

        userDao.register(usr);
        return UserDto.from(usr);
    }
    public boolean checkUser(String email){
        for (int i = 0; i < userDao.getAllUsers().size(); i++) {
            if(Objects.equals(email, userDao.getAllUsers().get(i).getEmail())){
                return false;
            }
        }
        return true;
    }
}

