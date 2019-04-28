package com.nao4j.subtrol.service.slice;

import com.nao4j.subtrol.document.internal.ExactPeriod;
import com.nao4j.subtrol.document.internal.Quantity;
import com.nao4j.subtrol.document.internal.Quantity.QuantityType;
import com.nao4j.subtrol.document.internal.RightOpenPeriod;
import com.nao4j.subtrol.document.internal.Subscription;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static com.nao4j.subtrol.document.internal.Quantity.QuantityType.DAYS;
import static com.nao4j.subtrol.document.internal.Quantity.QuantityType.MONTHS;
import static com.nao4j.subtrol.document.internal.Quantity.QuantityType.YEARS;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class SubscriptionSliceServiceTest implements WithAssertions {

    @Mock
    private SubscriptionSlicer monthsSlicer;
    @Mock
    private SubscriptionSlicer daysSlicer;

    private SubscriptionSliceService sliceService;

    @BeforeEach
    void setup() {
        doReturn(MONTHS).when(monthsSlicer).dimension();
        doReturn(DAYS).when(daysSlicer).dimension();
        sliceService = createSliceService(List.of(monthsSlicer, daysSlicer));
    }

    private SubscriptionSliceService createSliceService(final Collection<SubscriptionSlicer> slicers) {
        return new SubscriptionSliceServiceImpl(slicers);
    }

    @Nested
    class Constructor {

        @Test
        void shouldThrowExceptionIfSlicersIsNull() {
            assertThatThrownBy(() -> createSliceService(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("Slicers cannot be null");
        }

        @Test
        void shouldThrowExceptionIfSlicersIsEmpty() {
            assertThatThrownBy(() -> createSliceService(emptyList()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Slicers cannot be empty");
        }

        @Test
        void shouldThrowExceptionIfSlicersHasDuplicate(@Mock final SubscriptionSlicer slicer) {
            final QuantityType dimension = MONTHS;
            doReturn(dimension).when(slicer).dimension();
            assertThatThrownBy(() -> createSliceService(List.of(slicer, slicer)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Slicer for " + dimension + " already registered");
        }

    }

    @Nested
    class Slice {

        @Test
        void shouldResolveExistsSlicer(@Mock final Subscription subscription, @Mock final ExactPeriod period) {
            final long quantityCount = 1;
            final RightOpenPeriod subscriptionPeriod
                    = new RightOpenPeriod(LocalDateTime.parse("2019-04-24T00:00"), null);
            final Collection<ExactPeriod> result = singletonList(
                    new ExactPeriod(LocalDateTime.parse("2019-04-24T00:00"), LocalDateTime.parse("2019-05-24T00:00"))
            );

            final Quantity quantity = mock(Quantity.class);
            doReturn(MONTHS).when(quantity).getType();
            doReturn(quantityCount).when(quantity).getCount();
            doReturn(quantity).when(subscription).getQuantity();
            doReturn(subscriptionPeriod).when(subscription).getPeriod();
            doReturn(result).when(monthsSlicer).slice(any(RightOpenPeriod.class), any(ExactPeriod.class), anyLong());

            assertThat(sliceService.slice(subscription, period)).isEqualTo(result);

            verify(monthsSlicer).slice(subscriptionPeriod, period, quantityCount);
            verifyNoMoreInteractions(monthsSlicer, daysSlicer);
        }

        @Test
        void shouldReturnEmptyCollectionIfSlicerIsNotExists(
                @Mock final Subscription subscription,
                @Mock final ExactPeriod period
        ) {
            final Quantity quantity = mock(Quantity.class);
            doReturn(YEARS).when(quantity).getType();
            doReturn(quantity).when(subscription).getQuantity();

            assertThat(sliceService.slice(subscription, period)).isEmpty();

            verifyNoMoreInteractions(monthsSlicer, daysSlicer);
        }

    }

    @Nested
    class Dimensions {

        @Test
        void shouldReturnAllDimensionsIfSlicerExists() {
            assertThat(sliceService.dimensions()).containsExactlyInAnyOrder(MONTHS, DAYS);
        }

    }

}
