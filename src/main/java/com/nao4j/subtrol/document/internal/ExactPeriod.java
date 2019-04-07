package com.nao4j.subtrol.document.internal;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class ExactPeriod {

    @NonNull
    private final LocalDateTime start;
    @NonNull
    private final LocalDateTime end;

    public ExactPeriod(final LocalDateTime start, final LocalDateTime end) {
        this.start = start;
        this.end = end;

        if (end.isBefore(start)) {
            throw new IllegalArgumentException();
        }
    }

}
