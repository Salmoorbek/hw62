package com.example.microgram.controller;

import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import com.example.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user) {
        if (userService.checkUser(user.getEmail())){
            return new ResponseEntity<>(userService.register(user.getName(), user.getEmail(), user.getPassword()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<String> auth(Authentication authentication) {
        try {
            authentication.getPrincipal();
            return new ResponseEntity<>("Вы успешно авторизовались", HttpStatus.OK);
        } catch (NullPointerException npe) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
