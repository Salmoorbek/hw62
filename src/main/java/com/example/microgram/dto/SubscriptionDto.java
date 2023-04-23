package com.example.microgram.dto;

import com.example.microgram.entity.Subscription;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SubscriptionDto {
    private int subscribes;
    private int subscribedTo;
    private LocalDateTime subscribeTime;

    public static SubscriptionDto from(Subscription subs) {
        return builder()
                .subscribes(subs.getSubscribes())
                .subscribedTo(subs.getSubscribedTo())
                .subscribeTime(subs.getSubscribeTime())
                .build();
    }
}
