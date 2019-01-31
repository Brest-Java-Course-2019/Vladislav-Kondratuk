package com.epam.brest.courses.tariff_prices;

import java.math.BigDecimal;

public interface TariffPriceForWeight {

    BigDecimal defineTariffPrice(TariffPrices tariffPriceWeight, InputParameterToCompare compareWeight);
}
