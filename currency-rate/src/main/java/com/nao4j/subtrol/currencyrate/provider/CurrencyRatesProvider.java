package com.nao4j.subtrol.currencyrate.provider;

import com.nao4j.subtrol.currencyrate.document.CurrencyRates;

import java.util.Optional;

public interface CurrencyRatesProvider {

    Optional<CurrencyRates> latest();

}
