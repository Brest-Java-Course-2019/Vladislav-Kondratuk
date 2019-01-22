package com.epam.brest.courses;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Vladislav Kondratuk
 */

public class DeliveryCost {

    private BigDecimal costPerKm;
    private BigDecimal costPerKg;
    private BigDecimal weight;
    private BigDecimal distance;

    public void setCostPerKg(BigDecimal costPerKg) {
        this.costPerKg = costPerKg;
    }

    public BigDecimal getCostPerKg() {
        return costPerKg;
    }
    public void setCostPerKm(BigDecimal costPerKm) {
        this.costPerKm = costPerKm;
    }

    public BigDecimal getCostPerKm() {
        return costPerKm;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void inputWeightAndDistance(){
        BigDecimal costForWeight, costForDistance, totalCost;
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

            System.out.print("Enter weight: ");
            double weight = input.nextDouble();
            setDistance(BigDecimal.valueOf(weight));

            System.out.print("Enter distance: ");
            double distance = input.nextDouble();
            setWeight(BigDecimal.valueOf(distance));


            if (weight < minWeightTariff) {
                setCostPerKg(BigDecimal.valueOf(0.07));
            }
            else if  (weight >= minWeightTariff | weight <= averageWeightTariff) {
                setCostPerKg(BigDecimal.valueOf(0.14));
            }
            else if (weight >= maxWeightTariff) {
                setCostPerKg(BigDecimal.valueOf(0.21));
            }else {
                setCostPerKg(BigDecimal.valueOf(0));
            }

            if (distance < minDistanceTariff) {
                setCostPerKm(BigDecimal.valueOf(0.6));
            }
            else if (distance >= minDistanceTariff | weight <= averageDistanceTariff) {
                setCostPerKm(BigDecimal.valueOf(0.85));
            }
            else if (distance >= maxDistanceTariff) {
                setCostPerKm(BigDecimal.valueOf(0.9));
            }else {
                setCostPerKm(BigDecimal.valueOf(0));
            }

            costForDistance = getCostPerKm().multiply(getDistance());
            costForDistance = costForDistance.setScale(2, RoundingMode.CEILING);
            System.out.println("Cost for distance: " + costForDistance + " rub");

            costForWeight = getCostPerKg().multiply(getWeight());
            costForWeight = costForWeight.setScale(2, RoundingMode.CEILING);
            System.out.println("Cost for weight: " + costForWeight + " rub");

            //BigDecimal output
            totalCost = costForDistance.add(costForWeight);
            totalCost = totalCost.setScale(2, RoundingMode.CEILING);
            System.out.println("Total cost =  " + getCostPerKm() + "(Cost per km) * " + getDistance() + "(Distance) + "
                    + getCostPerKg() + "(Cost per kg) * " + getWeight() + "(Weight)");
            System.out.println("Total cost: " + totalCost + " rub");


        } catch (IOException e) {
            System.out.println("Input exception, file not found: " + e);
        }
    }



    public static void main(String args[]) {
        DeliveryCost dC = new DeliveryCost();
        dC.inputWeightAndDistance();
    }
}