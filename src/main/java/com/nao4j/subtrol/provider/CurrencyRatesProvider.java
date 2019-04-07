package com.nao4j.subtrol.provider;

import com.nao4j.subtrol.document.CurrencyRates;

import java.util.Optional;

public interface CurrencyRatesProvider {

    Optional<CurrencyRates> latest();

}
