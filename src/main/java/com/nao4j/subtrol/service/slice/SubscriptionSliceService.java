package com.nao4j.subtrol.service.slice;

import com.nao4j.subtrol.document.internal.ExactPeriod;
import com.nao4j.subtrol.document.internal.Subscription;

import java.util.Collection;

public interface SubscriptionSliceService {

    Collection<ExactPeriod> slice(Subscription subscription, ExactPeriod period);

}
