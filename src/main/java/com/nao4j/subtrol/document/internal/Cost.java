package com.nao4j.subtrol.document.internal;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Wither;

@Data
@Wither
public class Cost {

    @NonNull
    private final String name;
    @NonNull
    private final Price price;
    @NonNull
    private final ExactPeriod period;

    private final boolean isAccurate;

}
