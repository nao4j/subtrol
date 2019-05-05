package com.nao4j.subtrol.core.service;

import com.nao4j.subtrol.core.dto.ShortService;
import org.springframework.security.access.annotation.Secured;

import java.util.Set;

public interface ServiceService {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Set<ShortService> getAllForUser(String userId);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ShortService create(String userId, ShortService service);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ShortService remove(String userId, ShortService service);

}
