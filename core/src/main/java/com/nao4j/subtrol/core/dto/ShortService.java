package com.nao4j.subtrol.core.dto;

import com.nao4j.subtrol.core.document.internal.Subscription;
import lombok.Data;

@Data
public class ShortService {

    final String name;
    final Subscription currentSubscription;

}
