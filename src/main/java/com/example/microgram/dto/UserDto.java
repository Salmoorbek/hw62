package com.example.microgram.dto;

import com.example.microgram.entity.User;
import lombok.*;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class UserDto {
    private String name;
    private String email;
    private String accName;
    private Boolean enabled = Boolean.TRUE;
    private int countPublication;
    private int countSubscription;
    private int countFollower;
    public static UserDto from (User user){
        return builder()
                .name(user.getName())
                .email(user.getEmail())
                .accName(user.getAccName())
                .enabled(user.getEnabled())
                .countPublication(user.getCountPublication())
                .countSubscription(user.getCountSubscription())
                .countFollower(user.getCountFollower())
                .build();
    }
}
