package com.nao4j.subtrol.service;

import com.nao4j.subtrol.document.internal.Quantity.QuantityType;
import com.nao4j.subtrol.service.slice.SubscriptionSliceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuantityServiceImpl implements QuantityService {

    private final SubscriptionSliceService sliceService;

    @Override
    public Set<QuantityType> getAll() {
        return sliceService.dimensions();
    }

}
