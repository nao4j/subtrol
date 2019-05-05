package com.nao4j.subtrol.core.service.slice;

import com.nao4j.subtrol.core.document.internal.ExactPeriod;
import com.nao4j.subtrol.core.document.internal.RightOpenPeriod;
import lombok.Data;
import lombok.SneakyThrows;
import org.assertj.core.api.WithAssertions;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static com.nao4j.subtrol.core.document.internal.Quantity.QuantityType.MONTHS;

class MonthlySubscriptionSlicerTest implements WithAssertions {

    @Data
    static class TestData {
        private final RightOpenPeriod subscriptionPeriod;
        private final Long stepSize;
        private final ExactPeriod calculatePeriod;
        private final Collection<ExactPeriod> expected;
    }

    static class TestDataAggregator implements ArgumentsAggregator {

        @Override
        @SneakyThrows
        public Object aggregateArguments(final ArgumentsAccessor arguments, final ParameterContext context) {
            final var subscriptionStart = LocalDateTime.parse(arguments.getString(0));
            final LocalDateTime subscriptionEnd;
            if (arguments.getString(1) != null) {
                subscriptionEnd = LocalDateTime.parse(arguments.getString(1));
            } else {
                subscriptionEnd = null;
            }
            final var stepSize = arguments.getLong(2);
            final var calculateStart = LocalDateTime.parse(arguments.getString(3));
            final var calculateEnd = LocalDateTime.parse(arguments.getString(4));
            final JSONArray array = new JSONArray(arguments.getString(5));
            final Collection<ExactPeriod> expected = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                final var item = array.getJSONObject(i);
                expected.add(new ExactPeriod(
                        LocalDateTime.parse(item.getString("start")),
                        LocalDateTime.parse(item.getString("end"))
                ));
            }
            return new TestData(
                    new RightOpenPeriod(subscriptionStart, subscriptionEnd),
                    stepSize,
                    new ExactPeriod(calculateStart, calculateEnd),
                    expected
            );
        }

    }

    private SubscriptionSlicer slicer;

    @BeforeEach
    void setup() {
        slicer = new MonthlySubscriptionSlicer();
    }

    @Nested
    class Dimension {

        @Test
        void shouldPass() {
            assertThat(slicer.dimension()).isEqualTo(MONTHS);
        }

    }

    @Nested
    class Slice {

        @ParameterizedTest
        @CsvFileSource(resources = "/monthly-slicer.csv", delimiter = ';', numLinesToSkip = 1)
        void shouldPass(@AggregateWith(TestDataAggregator.class) final TestData testData) {
            final RightOpenPeriod subscriptionPeriod = testData.getSubscriptionPeriod();
            final ExactPeriod calculationPeriod = testData.getCalculatePeriod();
            final long stepSize = testData.getStepSize();
            final Object expected = testData.getExpected();
            assertThat(slicer.slice(subscriptionPeriod, calculationPeriod, stepSize)).isEqualTo(expected);
        }

    }

}
