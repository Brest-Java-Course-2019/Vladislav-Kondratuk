package com.epam.brest.courses.tariff_prices;

import java.math.BigDecimal;

public class TariffPriceForDistance {

    public BigDecimal defineTariffPrice
            (double distance, double minDistanceTariff, double averageDistanceTariff, double maxDistanceTariff) {

        if (distance < minDistanceTariff) {
            return BigDecimal.valueOf(0.7);
        } else if (distance >= minDistanceTariff
                && distance <= averageDistanceTariff) {
            return BigDecimal.valueOf(0.85);
        } else if (distance >= averageDistanceTariff
                && distance <= maxDistanceTariff) {
            return BigDecimal.valueOf(0.9);
        } else {
            return BigDecimal.valueOf(0.95);
        }
    }
}
