package com.nao4j.subtrol.core.service.slice;

import com.nao4j.subtrol.core.document.internal.ExactPeriod;
import com.nao4j.subtrol.core.document.internal.Quantity;
import com.nao4j.subtrol.core.document.internal.Subscription;

import java.util.Collection;
import java.util.Set;

public interface SubscriptionSliceService {

    Collection<ExactPeriod> slice(Subscription subscription, ExactPeriod period);

    Set<Quantity.QuantityType> dimensions();

}
