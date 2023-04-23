package com.example.microgram.service;

import com.example.microgram.dao.SubscriptionDao;
import com.example.microgram.dto.SubscriptionDto;
import com.example.microgram.entity.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionDao subscriptionDao;

    public SubscriptionDto followForUser(int subsId, int subsToId) {
        var subs = Subscription.builder()
                .id(subscriptionDao.getAllSubs().size() + 1)
                .subscribes(subsId)
                .subscribedTo(subsToId)
                .subscribeTime(LocalDateTime.now())
                .build();
        subscriptionDao.save(subs);
        return SubscriptionDto.from(subs);
    }
}
