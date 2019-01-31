package com.epam.brest.courses.tariff_prices;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;

public class TariffPriceForDistanceTest {

    TariffPriceForDistance tariffPriceForDistance;

    @Test
    void testDefineTariffPrice() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            Assertions.assertEquals(BigDecimal.valueOf(0.85), tariffPriceForDistance
                    .defineTariffPrice(51, 50, 120, 300));
        });

    }

}
