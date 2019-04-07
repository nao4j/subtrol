package com.nao4j.subtrol.service;

import com.nao4j.subtrol.document.internal.Costs;
import com.nao4j.subtrol.document.internal.ExactPeriod;
import org.springframework.security.access.annotation.Secured;

import static com.nao4j.subtrol.security.Roles.ADMIN;
import static com.nao4j.subtrol.security.Roles.USER;

public interface CostService {

    @Secured({USER, ADMIN})
    Costs calculateForPeriod(String userId, ExactPeriod period);

}
