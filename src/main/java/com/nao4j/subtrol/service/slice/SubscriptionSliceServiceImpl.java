package com.nao4j.subtrol.service.slice;

import com.nao4j.subtrol.document.internal.ExactPeriod;
import com.nao4j.subtrol.document.internal.Quantity.QuantityType;
import com.nao4j.subtrol.document.internal.Subscription;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

import static com.nao4j.subtrol.document.internal.Quantity.QuantityType.MONTHS;
import static java.util.Collections.emptyList;

@Service
public class SubscriptionSliceServiceImpl implements SubscriptionSliceService {

    private Map<QuantityType, SubscriptionSlicer> slicers;

    public SubscriptionSliceServiceImpl() {
        // todo: add implementations for other types
        this.slicers = Map.of(MONTHS, new MonthlySubscriptionSlicer());
    }

    @Override
    public Collection<ExactPeriod> slice(final Subscription subscription, final ExactPeriod period) {
        if (subscription.getQuantity().getType() == MONTHS) {
            return slicers.get(MONTHS).slice(subscription.getPeriod(), period, subscription.getQuantity().getCount());
        }
        return emptyList();
    }

}
