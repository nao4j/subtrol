package com.nao4j.subtrol.service.slice;

import com.nao4j.subtrol.document.internal.ExactPeriod;
import com.nao4j.subtrol.document.internal.RightOpenPeriod;

import java.util.Collection;

public interface SubscriptionSlicer {

    Collection<ExactPeriod> slice(RightOpenPeriod subscriptionPeriod, ExactPeriod calculationPeriod, long stepSize);

}