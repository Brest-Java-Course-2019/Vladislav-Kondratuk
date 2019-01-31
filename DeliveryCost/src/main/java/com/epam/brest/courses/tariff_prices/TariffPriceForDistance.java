package com.epam.brest.courses.tariff_prices;

import java.math.BigDecimal;

public interface TariffPriceForDistance {

    BigDecimal defineTariffPrice(TariffPrices tariffPriceDistance, InputParameterToCompare compareDistance) ;
}
