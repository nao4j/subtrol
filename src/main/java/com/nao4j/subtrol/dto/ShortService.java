package com.nao4j.subtrol.dto;

import com.nao4j.subtrol.document.internal.Subscription;
import lombok.Data;

@Data
public class ShortService {

    final String name;
    final Subscription currentSubscription;

}
