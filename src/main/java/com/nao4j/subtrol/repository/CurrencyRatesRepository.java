package com.nao4j.subtrol.repository;

import com.nao4j.subtrol.document.CurrencyRates;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CurrencyRatesRepository extends MongoRepository<CurrencyRates, String> {

    Optional<CurrencyRates> findTopByOrderByTimestampDesc();

    long deleteAllByDateBefore(LocalDate date);

}
