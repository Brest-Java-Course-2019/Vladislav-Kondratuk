package com.epam.brest.courses;

import com.epam.brest.courses.calc.DataItem;
import com.epam.brest.courses.calc.CalculatorImp;
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

        DataItem dataItem = new DataItem();
        Scanner input = new Scanner(System.in);
        InputStream inputFile;
        Properties inputProperty = new Properties();

        System.out.println("Total cost =  (Cost per km) * (Distance) + (Cost per kg) * (Weight)");

        try {
            //relative path to file
            inputFile = DeliveryCost.class.getResourceAsStream("/tariffPrices.properties");
            inputProperty.load(inputFile);

            double minWeightTariff = Double.parseDouble(inputProperty.getProperty("minWeightTariff"));
            double averageWeightTariff = Double.parseDouble(inputProperty.getProperty("averageWeightTariff"));
            double maxWeightTariff = Double.parseDouble(inputProperty.getProperty("maxWeightTariff"));

            double minDistanceTariff = Double.parseDouble(inputProperty.getProperty("minDistanceTariff"));
            double averageDistanceTariff = Double.parseDouble(inputProperty.getProperty("averageDistanceTariff"));
            double maxDistanceTariff = Double.parseDouble(inputProperty.getProperty("maxDistanceTariff"));

            try {
                System.out.print("Enter weight: ");
                double weight = input.nextDouble();
                dataItem.setDistance(BigDecimal.valueOf(weight));

                System.out.print("Enter distance: ");
                double distance = input.nextDouble();
                dataItem.setWeight(BigDecimal.valueOf(distance));

                if (weight <= 0) {
                    LOGGER.info("-> Weight can be less or equal 0");
                    return;
                } else if (weight < minWeightTariff) {
                    dataItem.setCostPerKg(BigDecimal.valueOf(0.07));
                } else if (weight >= minWeightTariff & weight <= averageWeightTariff) {
                    dataItem.setCostPerKg(BigDecimal.valueOf(0.14));
                } else if (weight >= averageWeightTariff & weight <= maxWeightTariff) {
                    dataItem.setCostPerKg(BigDecimal.valueOf(0.21));
                } else {
                    dataItem.setCostPerKg(BigDecimal.valueOf(0.25));
                }

                if (distance <= 0) {
                    LOGGER.info("-> Distance can be less or equal 0");
                    return;
                } else if (distance < minDistanceTariff) {
                    dataItem.setCostPerKm(BigDecimal.valueOf(0.6));
                } else if (distance >= minDistanceTariff & distance <= averageDistanceTariff) {
                    dataItem.setCostPerKm(BigDecimal.valueOf(0.85));
                } else if (distance >= averageDistanceTariff & distance <= maxDistanceTariff) {
                    dataItem.setCostPerKm(BigDecimal.valueOf(0.9));
                } else {
                    dataItem.setCostPerKm(BigDecimal.valueOf(0.95));
                }

                LOGGER.info("-> Cost per 1 kg = {}", dataItem.getCostPerKg()
                        .setScale(2, RoundingMode.CEILING));

                LOGGER.info("-> Cost per 1 km = {}", dataItem.getCostPerKm()
                        .setScale(2, RoundingMode.CEILING));

                BigDecimal calcResult = new CalculatorImp().calc(dataItem);

                LOGGER.info("-> Delivery cost = {}", calcResult
                        .setScale(2, RoundingMode.CEILING));

            } catch (InputMismatchException e) {
                System.out.println("Input type exception: " + e);
            }
        } catch (IOException e) {
            System.out.println("Input exception, file not found: " + e);
        }
    }
}
