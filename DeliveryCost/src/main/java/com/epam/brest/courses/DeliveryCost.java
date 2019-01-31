package com.epam.brest.courses;

import com.epam.brest.courses.calc.DataItem;
import com.epam.brest.courses.calc.CalculatorImp;
import com.epam.brest.courses.input_parametrs.InputParameterToCompare;
import com.epam.brest.courses.tariff_prices.TariffPriceForDistance;
import com.epam.brest.courses.tariff_prices.TariffPriceForWeight;
import com.epam.brest.courses.tariff_prices.TariffPrices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Vladislav Kondratuk
 */

public class DeliveryCost {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String args[]) {

        TariffPrices tariffPrices = new TariffPrices();
        DataItem dataItem = new DataItem();
        InputParameterToCompare inputParameter = new InputParameterToCompare();
        TariffPriceForWeight tariffPriceForWeight = new TariffPriceForWeight();
        TariffPriceForDistance tariffPriceForDistance = new TariffPriceForDistance();

        Scanner input = new Scanner(System.in);
        InputStream inputFile;
        Properties inputProperty = new Properties();

        inputFile = DeliveryCost.class.getResourceAsStream("/tariff_prices.properties");
        try {
            inputProperty.load(inputFile);
        } catch (IOException e) {
            LOGGER.error("Input exception, file not found: ", e);
        }

        try {
            tariffPrices.setMinWeightTariff(Double.parseDouble
                    (inputProperty.getProperty("minWeightTariff")));
            tariffPrices.setAverageWeightTariff(Double.parseDouble
                    (inputProperty.getProperty("averageWeightTariff")));
            tariffPrices.setMaxWeightTariff(Double.parseDouble
                    (inputProperty.getProperty("maxWeightTariff")));

            tariffPrices.setMinDistanceTariff(Double.parseDouble
                    (inputProperty.getProperty("minDistanceTariff")));
            tariffPrices.setAverageDistanceTariff(Double.parseDouble
                    (inputProperty.getProperty("averageDistanceTariff")));
            tariffPrices.setMaxDistanceTariff(Double.parseDouble
                    (inputProperty.getProperty("maxDistanceTariff")));
        } catch (NumberFormatException e) {
            LOGGER.error("Input mismatch type exception: ", e);
        }

        LOGGER.info("-> Total cost =  (Cost per km) * (Distance) + (Cost per kg) * (Weight)");

        try {
            System.out.print("Enter weight: ");
            inputParameter.setWeightToCompare(input.nextDouble());
            dataItem.setDistance(BigDecimal.valueOf(inputParameter.getWeightToCompare()));

            if (inputParameter.getWeightToCompare() <= 0) {
                LOGGER.info("-> Weight can't be less or equal 0");
            } else {
                dataItem.setCostPerKg(tariffPriceForWeight.defineTariffPrice
                        (inputParameter.getWeightToCompare(), tariffPrices.getMinWeightTariff(),
                                tariffPrices.getAverageWeightTariff(), tariffPrices.getMaxWeightTariff()));
            }

            System.out.print("Enter distance: ");
            inputParameter.setDistanceToCompare(input.nextDouble());
            dataItem.setWeight(BigDecimal.valueOf(inputParameter.getDistanceToCompare()));

            if (inputParameter.getDistanceToCompare() <= 0) {
                LOGGER.info("-> Distance can't be less or equal 0");
            } else {
                dataItem.setCostPerKm(tariffPriceForDistance.defineTariffPrice
                        (inputParameter.getDistanceToCompare(), tariffPrices.getMinDistanceTariff(),
                                tariffPrices.getAverageDistanceTariff(), tariffPrices.getMaxDistanceTariff()));
            }

            LOGGER.info("-> Cost per 1 kg = {}", dataItem.getCostPerKg()
                    .setScale(2, RoundingMode.CEILING));

            LOGGER.info("-> Cost per 1 km = {}", dataItem.getCostPerKm()
                    .setScale(2, RoundingMode.CEILING));

            BigDecimal calcResult = new CalculatorImp().calc(dataItem);

            LOGGER.info("-> Delivery cost = {}", calcResult
                    .setScale(2, RoundingMode.CEILING));

        } catch (InputMismatchException e) {
            LOGGER.error("Input type exception: ", e);
        }
    }
}
