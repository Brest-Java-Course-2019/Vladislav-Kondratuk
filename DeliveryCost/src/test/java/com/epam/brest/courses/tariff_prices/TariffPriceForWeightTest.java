package com.epam.brest.courses.tariff_prices;

import org.junit.jupiter.api.*;
import java.math.BigDecimal;

public class TariffPriceForWeightTest {

    TariffPriceForWeight tariffPriceForWeight = new TariffPriceForWeight();

    @Test
    void testDefineMinTariff() {

        Assertions.assertEquals(BigDecimal.valueOf(0.07), tariffPriceForWeight
                .defineTariffPrice(423, 600, 2000, 5000));
    }

    @Test
    void testDefineAverageTariff() {

        Assertions.assertEquals(BigDecimal.valueOf(0.14),tariffPriceForWeight
                .defineTariffPrice(1326, 600, 2000, 5000));
    }

    @Test
    void testDefineMaxTariff() {

        Assertions.assertEquals(BigDecimal.valueOf(0.21), tariffPriceForWeight
                .defineTariffPrice(4321, 600, 2000, 5000));
    }

    @Test
    void testDefineMoreMaxTariff() {

        Assertions.assertEquals(BigDecimal.valueOf(0.25), tariffPriceForWeight
                .defineTariffPrice(7421, 600, 2000, 5000));
    }

}
