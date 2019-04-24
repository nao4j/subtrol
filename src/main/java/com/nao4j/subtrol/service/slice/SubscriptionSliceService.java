package com.nao4j.subtrol.service.slice;

import com.nao4j.subtrol.document.internal.ExactPeriod;
import com.nao4j.subtrol.document.internal.Quantity.QuantityType;
import com.nao4j.subtrol.document.internal.Subscription;

import java.util.Collection;
import java.util.Set;

public interface SubscriptionSliceService {

    Collection<ExactPeriod> slice(Subscription subscription, ExactPeriod period);

    Set<QuantityType> dimensions();

}
