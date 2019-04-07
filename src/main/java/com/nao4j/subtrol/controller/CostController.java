package com.nao4j.subtrol.controller;

import com.nao4j.subtrol.document.internal.Costs;
import com.nao4j.subtrol.document.internal.ExactPeriod;
import com.nao4j.subtrol.dto.UserCredentials;
import com.nao4j.subtrol.service.CostService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/costs")
public class CostController {

    private final CostService costService;

    @GetMapping
    public Costs getAllInPeriod(
            @RequestParam @DateTimeFormat(iso = DATE_TIME) final LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DATE_TIME) final LocalDateTime end
    ) {
        final var user = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return costService.calculateForPeriod(user.getId(), new ExactPeriod(start, end));
    }

}
