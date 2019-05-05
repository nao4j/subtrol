package com.nao4j.subtrol.core.service;

import com.nao4j.subtrol.core.service.slice.SubscriptionSliceService;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.nao4j.subtrol.core.document.internal.Quantity.QuantityType.DAYS;
import static com.nao4j.subtrol.core.document.internal.Quantity.QuantityType.MONTHS;
import static java.util.Collections.emptySet;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class QuantityServiceTest implements WithAssertions {

    @Mock
    private SubscriptionSliceService sliceService;

    private QuantityService quantityService;

    @BeforeEach
    void setup() {
        quantityService = new QuantityServiceImpl(sliceService);
    }

    @Nested
    class GetAll {

        @Test
        void shouldReturnResultIfSlicerExists() {
            doReturn(Set.of(MONTHS, DAYS)).when(sliceService).dimensions();
            assertThat(quantityService.getAll()).containsExactlyInAnyOrder(MONTHS, DAYS);
            verify(sliceService).dimensions();
            verifyNoMoreInteractions(sliceService);
        }

        @Test
        void shouldReturnEmptyIfSlicerNotExists() {
            doReturn(emptySet()).when(sliceService).dimensions();
            assertThat(quantityService.getAll()).isEmpty();
            verify(sliceService).dimensions();
            verifyNoMoreInteractions(sliceService);
        }

    }

}
