package com.epam.brest.courses.tariff_prices;

import org.junit.jupiter.api.*;
import java.math.BigDecimal;

public class TariffPriceForWeightTest {

    TariffPriceForWeight tariffPriceForWeight;

    @Test
    void testDefineTariffPrice() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            Assertions.assertEquals(new BigDecimal("0.21"), tariffPriceForWeight
                    .defineTariffPrice(3023, 600, 2000, 5000));
        });

    }

}
