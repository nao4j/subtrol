package com.nao4j.subtrol.core.service.slice;

import com.nao4j.subtrol.core.document.internal.ExactPeriod;
import com.nao4j.subtrol.core.document.internal.Quantity.QuantityType;
import com.nao4j.subtrol.core.document.internal.RightOpenPeriod;

import java.util.Collection;

public interface SubscriptionSlicer {

    Collection<ExactPeriod> slice(RightOpenPeriod subscriptionPeriod, ExactPeriod calculationPeriod, long stepSize);

    QuantityType dimension();

}
