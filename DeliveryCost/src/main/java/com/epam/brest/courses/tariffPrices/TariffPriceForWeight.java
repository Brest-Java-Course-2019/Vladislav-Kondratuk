package com.epam.brest.courses.tariffPrices;

import java.math.BigDecimal;

public class TariffPriceForWeight {

    public BigDecimal defineTariffPrice
            (double weight, double minWeightTariff, double averageWeightTariff, double maxWeightTariff) {

        if (weight < minWeightTariff) {
            return BigDecimal.valueOf(0.07);
        } else if (weight >= minWeightTariff
                && weight <= averageWeightTariff) {
            return BigDecimal.valueOf(0.14);
        } else if (weight >= averageWeightTariff
                && weight <= maxWeightTariff) {
            return BigDecimal.valueOf(0.21);
        } else {
            return BigDecimal.valueOf(0.25);
        }
    }
}
