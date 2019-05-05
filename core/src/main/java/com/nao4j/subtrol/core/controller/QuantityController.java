package com.nao4j.subtrol.core.controller;

import com.nao4j.subtrol.core.document.internal.Quantity;
import com.nao4j.subtrol.core.service.QuantityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quantities")
public class QuantityController {

    private final QuantityService quantityService;

    @GetMapping
    public Set<Quantity.QuantityType> getAll() {
        return quantityService.getAll();
    }

}
