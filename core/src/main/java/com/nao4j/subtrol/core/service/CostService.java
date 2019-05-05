package com.nao4j.subtrol.core.service;

import com.nao4j.subtrol.core.document.internal.Costs;
import com.nao4j.subtrol.core.document.internal.ExactPeriod;
import org.springframework.security.access.annotation.Secured;

public interface CostService {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Costs calculateForPeriod(String userId, ExactPeriod period);

}
