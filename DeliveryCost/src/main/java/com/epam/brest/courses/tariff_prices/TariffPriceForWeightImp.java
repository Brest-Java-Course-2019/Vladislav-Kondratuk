package com.epam.brest.courses.tariff_prices;

import java.math.BigDecimal;

public class TariffPriceForWeightImp implements TariffPriceForWeight {

    @Override
    public BigDecimal defineTariffPrice(TariffPrices tariffPriceWeight, InputParameterToCompare compareWeight) {
        if (compareWeight.getWeightToCompare() < tariffPriceWeight.getMinWeightTariff()) {
            return BigDecimal.valueOf(0.07);
        } else if (compareWeight.getWeightToCompare() >= tariffPriceWeight.getMinWeightTariff()
                && compareWeight.getWeightToCompare() <= tariffPriceWeight.getAverageWeightTariff()) {
            return BigDecimal.valueOf(0.14);
        } else if (compareWeight.getWeightToCompare() >= tariffPriceWeight.getAverageWeightTariff()
                && compareWeight.getWeightToCompare() <= tariffPriceWeight.getMaxWeightTariff()) {
            return BigDecimal.valueOf(0.21);
        } else {
            return BigDecimal.valueOf(0.25);
        }
    }
}
