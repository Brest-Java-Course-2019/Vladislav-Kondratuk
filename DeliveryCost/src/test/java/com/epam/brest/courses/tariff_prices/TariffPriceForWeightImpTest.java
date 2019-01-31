package com.epam.brest.courses.tariff_prices;

import org.junit.jupiter.api.*;
import java.math.BigDecimal;

public class TariffPriceForWeightImpTest {

    TariffPriceForWeight tariff = new TariffPriceForWeightImp();

    TariffPrices tariffPriceWeight = new TariffPrices();
    InputParameterToCompare compareWeight = new InputParameterToCompare();

    @BeforeEach
    void init() {
        tariffPriceWeight = new TariffPrices();
        tariffPriceWeight.setMinWeightTariff(600);
        tariffPriceWeight.setAverageWeightTariff(2000);
        tariffPriceWeight.setMaxWeightTariff(5000);
    }

    @Test
    void testDefineMinWeightTariff() {
        compareWeight.setWeightToCompare(421);
        BigDecimal tariffPrice = tariff.defineTariffPrice(tariffPriceWeight, compareWeight);
        Assertions.assertEquals(BigDecimal.valueOf(0.07), tariffPrice);
    }

    @Test
    void testDefineAverageWeightTariff() {
        compareWeight.setWeightToCompare(1523);
        BigDecimal tariffPrice = tariff.defineTariffPrice(tariffPriceWeight, compareWeight);
        Assertions.assertEquals(BigDecimal.valueOf(0.14), tariffPrice);
    }

    @Test
    void testDefineMaxWeightTariff() {
        compareWeight.setWeightToCompare(2345);
        BigDecimal tariffPrice = tariff.defineTariffPrice(tariffPriceWeight, compareWeight);
        Assertions.assertEquals(BigDecimal.valueOf(0.21), tariffPrice);
    }

    @Test
    void testDefineMoreMaxWeightTariff() {
        compareWeight.setWeightToCompare(6451);
        BigDecimal tariffPrice = tariff.defineTariffPrice(tariffPriceWeight, compareWeight);
        Assertions.assertEquals(BigDecimal.valueOf(0.25), tariffPrice);
    }

}
