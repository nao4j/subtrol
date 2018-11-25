package com.nao4j.subtrol.service.slicer

import com.nao4j.subtrol.model.ExactPeriod
import com.nao4j.subtrol.model.RightOpenPeriod
import org.assertj.core.api.WithAssertions
import org.json.JSONArray
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.aggregator.AggregateWith
import org.junit.jupiter.params.aggregator.ArgumentsAccessor
import org.junit.jupiter.params.aggregator.ArgumentsAggregator
import org.junit.jupiter.params.provider.CsvFileSource
import java.time.LocalDateTime

class MonthlySubscriptionSlicerTest : WithAssertions {

    lateinit var slicer: SubscriptionSlicer

    @BeforeEach
    fun setup() {
        slicer = MonthlySubscriptionSlicer()
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/monthly-slicer.csv"], delimiter = ';', numLinesToSkip = 1)
    fun shouldPass(@AggregateWith(TestDataAggregator::class) testData: TestData) {
        assertThat(slicer.slice(testData.subscriptionPeriod, testData.calculatePeriod, testData.stepSize))
                .isEqualTo(testData.expected)
    }

}

data class TestData(
        val subscriptionPeriod: RightOpenPeriod,
        val stepSize: Long,
        val calculatePeriod: ExactPeriod,
        val expected: Collection<ExactPeriod>
)

class TestDataAggregator: ArgumentsAggregator {

    override fun aggregateArguments(arguments: ArgumentsAccessor?, context: ParameterContext?): Any {
        val subscriptionStart = LocalDateTime.parse(arguments!!.getString(0))
        val subscriptionEnd = if (arguments!!.getString(1) != null) {
            LocalDateTime.parse(arguments!!.getString(1))
        } else {
            null
        }
        val stepSize = arguments.getLong(2)
        val calculateStart = LocalDateTime.parse(arguments!!.getString(3))
        val calculateEnd = LocalDateTime.parse(arguments!!.getString(4))
        val array = JSONArray(arguments!!.getString(5))
        val expected = mutableListOf<ExactPeriod>()
        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)
            expected.add(ExactPeriod(
                    LocalDateTime.parse(item.getString("start")),
                    LocalDateTime.parse(item.getString("end"))
            ))
        }
        return TestData(
                RightOpenPeriod(subscriptionStart, subscriptionEnd),
                stepSize,
                ExactPeriod(calculateStart, calculateEnd),
                expected
        )
    }

}
