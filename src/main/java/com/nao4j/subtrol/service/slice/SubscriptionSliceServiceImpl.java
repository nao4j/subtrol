package com.nao4j.subtrol.service.slice;

import com.nao4j.subtrol.document.internal.ExactPeriod;
import com.nao4j.subtrol.document.internal.Quantity.QuantityType;
import com.nao4j.subtrol.document.internal.Subscription;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

@Service
public class SubscriptionSliceServiceImpl implements SubscriptionSliceService {

    private final Map<QuantityType, SubscriptionSlicer> slicers;

    public SubscriptionSliceServiceImpl(final Collection<SubscriptionSlicer> slicers) {
        requireNonNull(slicers, "Slicers cannot be null");
        if (slicers.isEmpty()) {
            throw new IllegalArgumentException("Slicers cannot be empty");
        }
        final var slicerMap = new EnumMap<QuantityType, SubscriptionSlicer>(QuantityType.class);
        for (final SubscriptionSlicer slicer : slicers) {
            final QuantityType dimension = slicer.dimension();
            if (slicerMap.put(dimension, slicer) != null) {
                throw new IllegalArgumentException("Slicer for " + dimension + " already registered");
            }
        }
        this.slicers = unmodifiableMap(slicerMap);
    }

    @Override
    public Collection<ExactPeriod> slice(final Subscription subscription, final ExactPeriod period) {
        final var dimension = subscription.getQuantity().getType();
        final var slicer = slicers.get(dimension);
        if (slicer != null) {
            return slicer.slice(subscription.getPeriod(), period, subscription.getQuantity().getCount());
        }
        // todo: may be throw exception?
        return emptyList();
    }

    @Override
    public Set<QuantityType> dimensions() {
        return slicers.keySet();
    }

}
