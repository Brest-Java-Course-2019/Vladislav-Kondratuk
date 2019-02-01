package com.epam.brest.courses.tariff_prices;

import java.math.BigDecimal;

public class TariffPriceForDistanceImp implements TariffPriceForDistance {

    @Override
    public BigDecimal defineTariffPrice(TariffPrices tariffPriceDistance, InputParameterToCompare compareDistance) {
        if (compareDistance.getDistanceToCompare() < tariffPriceDistance.getMinDistanceTariff()) {
            return BigDecimal.valueOf(0.7);
        } else if (compareDistance.getDistanceToCompare() >= tariffPriceDistance.getMinDistanceTariff()
                && compareDistance.getDistanceToCompare() < tariffPriceDistance.getAverageDistanceTariff()) {
            return BigDecimal.valueOf(0.85);
        } else if (compareDistance.getDistanceToCompare() >= tariffPriceDistance.getAverageDistanceTariff()
                && compareDistance.getDistanceToCompare() < tariffPriceDistance.getMaxDistanceTariff()) {
            return BigDecimal.valueOf(0.9);
        } else {
            return BigDecimal.valueOf(0.95);
        }
    }
}