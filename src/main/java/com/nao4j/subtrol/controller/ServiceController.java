package com.nao4j.subtrol.controller;

import com.nao4j.subtrol.dto.ShortService;
import com.nao4j.subtrol.dto.UserCredentials;
import com.nao4j.subtrol.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Set<ShortService> getAll() {
        final var user = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return serviceService.getAllForUser(user.getId());
    }

    @PostMapping
    public ShortService create(@RequestBody final ShortService service) {
        final var user = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return serviceService.create(user.getId(), service);
    }

    @DeleteMapping
    public ShortService remove(@RequestBody final ShortService service) {
        final var user = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return serviceService.remove(user.getId(), service);
    }

}
