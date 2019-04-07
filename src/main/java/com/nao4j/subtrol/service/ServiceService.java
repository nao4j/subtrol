package com.nao4j.subtrol.service;

import com.nao4j.subtrol.dto.ShortService;
import org.springframework.security.access.annotation.Secured;

import java.util.Set;

import static com.nao4j.subtrol.security.Roles.ADMIN;
import static com.nao4j.subtrol.security.Roles.USER;

public interface ServiceService {

    @Secured({USER, ADMIN})
    Set<ShortService> getAllForUser(String userId);

    @Secured({USER, ADMIN})
    ShortService create(String userId, ShortService service);

    @Secured({USER, ADMIN})
    ShortService remove(String userId, ShortService service);

}
