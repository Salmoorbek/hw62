package com.example.microgram.controller;

import com.example.microgram.dto.SubscriptionDto;
import com.example.microgram.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService service;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public SubscriptionDto addSubs(@RequestBody SubscriptionDto subscriptionDto) {
        return service.followForUser(subscriptionDto.getSubscribes(), subscriptionDto.getSubscribedTo());
    }
}
