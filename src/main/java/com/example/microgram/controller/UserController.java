package com.example.microgram.controller;

import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import com.example.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:63343")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }
    @GetMapping("/getUserByName/{name}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String name){
        return new ResponseEntity<>(userService.getUserByName(name), HttpStatus.OK);
    }
    @GetMapping("/getUserByNickName/{nickName}")
    public ResponseEntity<UserDto> getUserByNickName(@PathVariable String nickName){
        return new ResponseEntity<>(userService.getUserByNickName(nickName), HttpStatus.OK);
    }
    @GetMapping("/registered/{email}")
    public ResponseEntity<String> isRegistered(@PathVariable String email) {
        return new ResponseEntity<>(userService.isRegisteredEmail(email), HttpStatus.OK);
    }
    @GetMapping("/login/{accName}/{password}")
    public ResponseEntity<String> isLogin(@PathVariable String accName, @PathVariable String password) {
        return new ResponseEntity<>(userService.login(accName, password), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user) {
        System.out.println(user);
        UserDto registeredUser = userService.register(user.getName(), user.getEmail(), user.getPassword());
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
