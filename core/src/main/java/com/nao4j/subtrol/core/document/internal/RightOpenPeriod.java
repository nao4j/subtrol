package com.nao4j.subtrol.core.document.internal;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Wither;

import java.time.LocalDateTime;

@Data
@Wither
public class RightOpenPeriod {

    @NonNull
    private final LocalDateTime start;
    private final LocalDateTime end;

    public RightOpenPeriod(final LocalDateTime start, final LocalDateTime end) {
        this.start = start;
        this.end = end;

        if (end != null && end.isBefore(start)) {
            throw new IllegalArgumentException();   // todo: message
        }
    }

}
