package com.nao4j.subtrol.controller;

import com.nao4j.subtrol.document.internal.Quantity.QuantityType;
import com.nao4j.subtrol.service.QuantityService;
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
    public Set<QuantityType> getAll() {
        return quantityService.getAll();
    }

}
