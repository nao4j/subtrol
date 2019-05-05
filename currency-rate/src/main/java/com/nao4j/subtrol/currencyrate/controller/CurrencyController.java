package com.nao4j.subtrol.currencyrate.controller;

import com.nao4j.subtrol.currencyrate.document.CurrencyRates;
import com.nao4j.subtrol.currencyrate.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public Collection<String> getAll() {
        return currencyService.getAll();
    }

    @PostMapping
    public ResponseEntity<CurrencyRates> loadData() {
        return currencyService.loadData()
                .map(ResponseEntity::ok)
                .orElseGet(() -> status(INTERNAL_SERVER_ERROR).build());
    }

    @DeleteMapping
    public Long deleteOld() {
        return currencyService.deleteOldest(now().minusDays(7));
    }

}
