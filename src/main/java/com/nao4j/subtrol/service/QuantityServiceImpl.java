package com.nao4j.subtrol.service;

import com.nao4j.subtrol.document.internal.Quantity.QuantityType;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.nao4j.subtrol.document.internal.Quantity.QuantityType.MONTHS;

@Service
public class QuantityServiceImpl implements QuantityService {

    @Override
    public Set<QuantityType> getAll() {
        return Set.of(MONTHS);
    }

}
