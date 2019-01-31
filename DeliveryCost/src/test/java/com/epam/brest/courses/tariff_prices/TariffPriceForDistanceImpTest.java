package com.epam.brest.courses.tariff_prices;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;

public class TariffPriceForDistanceImpTest {

    TariffPriceForDistance tariff = new TariffPriceForDistanceImp();

    TariffPrices tariffPriceDistance = new TariffPrices();
    InputParameterToCompare compareDistance = new InputParameterToCompare();

    @BeforeEach
    void init() {
        tariffPriceDistance = new TariffPrices();
        tariffPriceDistance.setMinDistanceTariff(50);
        tariffPriceDistance.setAverageDistanceTariff(120);
        tariffPriceDistance.setMaxDistanceTariff(300);
    }

    @Test
    void testDefineMinDistanceTariff() {
        compareDistance.setDistanceToCompare(42);
        BigDecimal tariffPrice = tariff.defineTariffPrice(tariffPriceDistance, compareDistance);
        Assertions.assertEquals(BigDecimal.valueOf(0.7), tariffPrice);
    }

    @Test
    void testDefineAverageDistanceTariff() {
        compareDistance.setDistanceToCompare(99);
        BigDecimal tariffPrice = tariff.defineTariffPrice(tariffPriceDistance, compareDistance);
        Assertions.assertEquals(BigDecimal.valueOf(0.85), tariffPrice);
    }

    @Test
    void testDefineMaxDistanceTariff() {
        compareDistance.setDistanceToCompare(234);
        BigDecimal tariffPrice = tariff.defineTariffPrice(tariffPriceDistance, compareDistance);
        Assertions.assertEquals(BigDecimal.valueOf(0.9), tariffPrice);
    }

    @Test
    void testDefineMoreMaxDistanceTariff() {
        compareDistance.setDistanceToCompare(432);
        BigDecimal tariffPrice = tariff.defineTariffPrice(tariffPriceDistance, compareDistance);
        Assertions.assertEquals(BigDecimal.valueOf(0.95), tariffPrice);
    }

}
