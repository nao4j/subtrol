package com.nao4j.subtrol.document.internal;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Wither;

import java.util.Collection;

@Data
@Wither
public class Service {

    @NonNull
    private final String name;
    @NonNull
    private final Collection<Subscription> subscriptions;

}
