package com.epam.brest.courses.tariff_prices;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;

public class TariffPriceForDistanceTest {

    TariffPriceForDistance tariffPriceForDistance = new TariffPriceForDistance();

    @Test
    void testDefineMinTariff() {

        Assertions.assertEquals(BigDecimal.valueOf(0.7), tariffPriceForDistance
                .defineTariffPrice(42, 50, 120, 300));
    }

    @Test
    void testDefineAverageTariff() {

        Assertions.assertEquals(BigDecimal.valueOf(0.85), tariffPriceForDistance
                .defineTariffPrice(99, 50, 120, 300));
    }

    @Test
    void testDefineMaxTariff() {

        Assertions.assertEquals(BigDecimal.valueOf(0.9), tariffPriceForDistance
                .defineTariffPrice(231, 50, 120, 300));
    }

    @Test
    void testDefineMoreMaxTariff() {

        Assertions.assertEquals(BigDecimal.valueOf(0.9), tariffPriceForDistance
                .defineTariffPrice(432, 50, 120, 300));
    }

}
