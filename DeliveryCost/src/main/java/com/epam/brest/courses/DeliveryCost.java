package com.epam.brest.courses;

import com.epam.brest.courses.calc.DataItem;
import com.epam.brest.courses.calc.CalculatorImp;
import com.epam.brest.courses.input_parametrs.InputDistance;
import com.epam.brest.courses.tariff_prices.DistanceTariffPrices;
import com.epam.brest.courses.input_parametrs.InputWeight;
import com.epam.brest.courses.tariff_prices.TariffPriceForDistance;
import com.epam.brest.courses.tariff_prices.TariffPriceForWeight;
import com.epam.brest.courses.tariff_prices.WeightTariffPrices;
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

        WeightTariffPrices weightPrices = new WeightTariffPrices();
        DistanceTariffPrices distancePrices = new DistanceTariffPrices();
        DataItem dataItem = new DataItem();
        InputDistance inputDistance = new InputDistance();
        InputWeight inputWeight = new InputWeight();
        TariffPriceForWeight tariffPriceForWeight = new TariffPriceForWeight();
        TariffPriceForDistance tariffPriceForDistance = new TariffPriceForDistance();

        Scanner input = new Scanner(System.in);
        InputStream inputFile;
        Properties inputProperty = new Properties();

        inputFile = DeliveryCost.class.getResourceAsStream("/tariffPrices.properties");
        try {
            inputProperty.load(inputFile);
        } catch (IOException e) {
            LOGGER.error("Input exception, file not found: ", e);
        }

        try {
            weightPrices.setMinWeightTariff(Double.parseDouble
                    (inputProperty.getProperty("minWeightTariff")));
            weightPrices.setAverageWeightTariff(Double.parseDouble
                    (inputProperty.getProperty("averageWeightTariff")));
            weightPrices.setMaxWeightTariff(Double.parseDouble
                    (inputProperty.getProperty("maxWeightTariff")));

            distancePrices.setMinDistanceTariff(Double.parseDouble
                    (inputProperty.getProperty("minDistanceTariff")));
            distancePrices.setAverageDistanceTariff(Double.parseDouble
                    (inputProperty.getProperty("averageDistanceTariff")));
            distancePrices.setMaxDistanceTariff(Double.parseDouble
                    (inputProperty.getProperty("maxDistanceTariff")));
        } catch (NumberFormatException e) {
            LOGGER.error("Input mismatch type exception: ", e);
        }

        LOGGER.info("-> Total cost =  (Cost per km) * (Distance) + (Cost per kg) * (Weight)");

        try {
            System.out.print("Enter weight: ");
            inputWeight.setWeight(input.nextDouble());
            dataItem.setDistance(BigDecimal.valueOf(inputWeight.getWeight()));

            if (inputWeight.getWeight() <= 0) {
                LOGGER.info("-> Weight can't be less or equal 0");
            } else {
                dataItem.setCostPerKg(tariffPriceForWeight.defineTariffPrice
                        (inputWeight.getWeight(), weightPrices.getMinWeightTariff(),
                                weightPrices.getAverageWeightTariff(), weightPrices.getMaxWeightTariff()));
            }

            System.out.print("Enter distance: ");
            inputDistance.setDistance(input.nextDouble());
            dataItem.setWeight(BigDecimal.valueOf(inputDistance.getDistance()));

            if (inputDistance.getDistance() <= 0) {
                LOGGER.info("-> Distance can't be less or equal 0");
            } else {
                dataItem.setCostPerKm(tariffPriceForDistance.defineTariffPrice
                        (inputDistance.getDistance(), distancePrices.getMinDistanceTariff(),
                                distancePrices.getAverageDistanceTariff(), distancePrices.getMaxDistanceTariff()));
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
