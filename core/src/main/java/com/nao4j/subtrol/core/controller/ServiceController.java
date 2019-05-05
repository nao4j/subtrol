package com.nao4j.subtrol.core.controller;

import com.nao4j.subtrol.core.dto.ShortService;
import com.nao4j.subtrol.core.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping
    public Set<ShortService> getAll(@AuthenticationPrincipal String userId) {
        return serviceService.getAllForUser(userId);
    }

    @PostMapping
    public ShortService create(@RequestBody final ShortService service, @AuthenticationPrincipal String userId) {
        return serviceService.create(userId, service);
    }

    @DeleteMapping
    public ShortService remove(@RequestBody final ShortService service, @AuthenticationPrincipal String userId) {
        return serviceService.remove(userId, service);
    }

}
