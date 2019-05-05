package com.nao4j.subtrol.core.controller;

import com.nao4j.subtrol.core.document.internal.Costs;
import com.nao4j.subtrol.core.document.internal.ExactPeriod;
import com.nao4j.subtrol.core.service.CostService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @RequestParam @DateTimeFormat(iso = DATE_TIME) final LocalDateTime end,
            @AuthenticationPrincipal String userId
    ) {
        return costService.calculateForPeriod(userId, new ExactPeriod(start, end));
    }

}
