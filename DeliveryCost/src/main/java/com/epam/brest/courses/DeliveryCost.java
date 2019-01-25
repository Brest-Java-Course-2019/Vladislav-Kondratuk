package com.epam.brest.courses;

import com.epam.brest.courses.calc.DataItem;
import com.epam.brest.courses.calc.CalculatorImp;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Vladislav Kondratuk
 */

public class DeliveryCost {

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

                if (weight < minWeightTariff) {
                    dataItem.setCostPerKg(BigDecimal.valueOf(0.07));
                } else if (weight >= averageWeightTariff & weight < maxWeightTariff) {
                    dataItem.setCostPerKg(BigDecimal.valueOf(0.14));
                } else if (weight >= maxWeightTariff) {
                    dataItem.setCostPerKg(BigDecimal.valueOf(0.21));
                } else {
                    dataItem.setCostPerKg(BigDecimal.valueOf(0));
                }

                if (distance < minDistanceTariff) {
                    dataItem.setCostPerKm(BigDecimal.valueOf(0.6));
                } else if (distance >= averageDistanceTariff & distance < maxDistanceTariff) {
                    dataItem.setCostPerKm(BigDecimal.valueOf(0.85));
                } else if (distance >= maxDistanceTariff) {
                    dataItem.setCostPerKm(BigDecimal.valueOf(0.9));
                } else {
                    dataItem.setCostPerKm(BigDecimal.valueOf(0));
                }

                System.out.format("Cost per 1 km  = %.2f rub%n", dataItem.getCostPerKm());
                System.out.format("Cost per 1 kg  = %.2f rub%n", dataItem.getCostPerKg());

                BigDecimal calcResult = new CalculatorImp().calc(dataItem);
                System.out.format("Delivery cost = %.2f rub%n", calcResult);

            } catch (java.util.InputMismatchException e) {
                System.out.println("Input type exception: " + e);
            }
        } catch (IOException e) {
            System.out.println("Input exception, file not found: " + e);
        }
    }
}
