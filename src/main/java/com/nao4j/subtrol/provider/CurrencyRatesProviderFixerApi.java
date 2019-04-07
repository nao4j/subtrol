package com.nao4j.subtrol.provider;

import com.nao4j.subtrol.document.CurrencyRates;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CurrencyRatesProviderFixerApi implements CurrencyRatesProvider {

    private static final String FIXER_LASTES_URI = "http://data.fixer.io/api/latest?access_key={accessKey}";

    private final RestTemplate restTemplate;
    @Value("${api.fixer.key}")
    private String accessKey;

    @Override
    public Optional<CurrencyRates> latest() {
        return Optional.ofNullable(restTemplate.getForEntity(FIXER_LASTES_URI, CurrencyRates.class, accessKey))
                .map(ResponseEntity::getBody);
    }

}
