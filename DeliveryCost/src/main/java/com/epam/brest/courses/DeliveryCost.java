package com.epam.brest.courses;

import com.epam.brest.courses.calc.DataItem;
import com.epam.brest.courses.calc.CalculatorImp;
import com.epam.brest.courses.tariff_prices.TariffPriceForDistanceImp;
import com.epam.brest.courses.tariff_prices.TariffPriceForWeightImp;
import com.epam.brest.courses.tariff_prices.TariffPrices;
import com.epam.brest.courses.tariff_prices.InputParameterToCompare;
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

    public static void main(String[] args) {

        TariffPrices tariffPriceWeight = new TariffPrices();
        TariffPrices tariffPriceDistance = new TariffPrices();
        InputParameterToCompare compareWeight = new InputParameterToCompare();
        InputParameterToCompare compareDistance = new InputParameterToCompare();
        DataItem dataItem = new DataItem();
        InputParameterToCompare inputParameter = new InputParameterToCompare();

        Scanner input = new Scanner(System.in);
        InputStream inputFile;
        Properties inputProperty = new Properties();

        inputFile = DeliveryCost.class.getResourceAsStream("/tariff_prices.properties");
        try {
            inputProperty.load(inputFile);
        } catch (IOException e) {
            LOGGER.error("Input exception, file not found: \n", e);
        }

        try {
            tariffPriceWeight.setMinWeightTariff(Double.parseDouble
                    (inputProperty.getProperty("minWeightTariff")));
            tariffPriceWeight.setAverageWeightTariff(Double.parseDouble
                    (inputProperty.getProperty("averageWeightTariff")));
            tariffPriceWeight.setMaxWeightTariff(Double.parseDouble
                    (inputProperty.getProperty("maxWeightTariff")));

            tariffPriceDistance.setMinDistanceTariff(Double.parseDouble
                    (inputProperty.getProperty("minDistanceTariff")));
            tariffPriceDistance.setAverageDistanceTariff(Double.parseDouble
                    (inputProperty.getProperty("averageDistanceTariff")));
            tariffPriceDistance.setMaxDistanceTariff(Double.parseDouble
                    (inputProperty.getProperty("maxDistanceTariff")));
        } catch (NumberFormatException e) {
            LOGGER.error("Input mismatch type exception: \n", e);
        }

        LOGGER.info("-> Total cost =  (Cost per km) * (Distance) + (Cost per kg) * (Weight)\n");

        try {
            LOGGER.info("Enter weight: ");
            inputParameter.setWeightToCompare(input.nextDouble());
            dataItem.setDistance(BigDecimal.valueOf(inputParameter.getWeightToCompare()));

            if (inputParameter.getWeightToCompare() <= 0) {
                LOGGER.warn("-> Weight can't be less or equal 0\n");
            } else {
                dataItem.setCostPerKg
                        (new TariffPriceForWeightImp().defineTariffPrice(tariffPriceWeight, compareWeight));
            }


            LOGGER.info("Enter distance: ");
            inputParameter.setDistanceToCompare(input.nextDouble());
            dataItem.setWeight(BigDecimal.valueOf(inputParameter.getDistanceToCompare()));

            if (inputParameter.getDistanceToCompare() <= 0) {
                LOGGER.warn("-> Distance can't be less or equal 0\n");
            } else {
                dataItem.setCostPerKm
                        (new TariffPriceForDistanceImp().defineTariffPrice(tariffPriceDistance, compareDistance));
            }

            LOGGER.info("-> Cost per 1 kg = {}\n", dataItem.getCostPerKg()
                    .setScale(2, RoundingMode.CEILING));

            LOGGER.info("-> Cost per 1 km = {}\n", dataItem.getCostPerKm()
                    .setScale(2, RoundingMode.CEILING));

            BigDecimal calcResult = new CalculatorImp().calc(dataItem);

            LOGGER.info("-> Delivery cost = {}\n", calcResult
                    .setScale(2, RoundingMode.CEILING));

        } catch (InputMismatchException e) {
            LOGGER.error("Input type exception: \n", e);
        }
    }
}
