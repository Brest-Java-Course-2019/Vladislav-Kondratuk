package com.epam.brest.courses;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Vladislav Kondratuk
 */

public class DeliveryCost {

    private double costPerKm;
    private double costPerKg;
    private double weight;
    private double distance;

    public void setCostPerKg(double costPerKg) {
        this.costPerKg = costPerKg;
    }

    public double getCostPerKg() {
        return costPerKg;
    }
    public void setCostPerKm(double costPerKm) {
        this.costPerKm = costPerKm;
    }

    public double getCostPerKm() {
        return costPerKm;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public double totalCost(double weight, double distance) {
        return getCostPerKg() * weight + getCostPerKm() * distance;
    }

    public void inputWeightAndDistance(){
        Scanner input = new Scanner(System.in);
        DeliveryCost dC = new DeliveryCost();

        FileInputStream inputFile;
        Properties inputProperty = new Properties();
        try {
            inputFile = new FileInputStream("/home/vlad/development/DeliveryCost/src/main/resources/tariffPrices.properties");
            inputProperty.load(inputFile);

            double minWeightTariff = Double.parseDouble(inputProperty.getProperty("minWeightTariff"));
            double averageWeightTariff = Double.parseDouble(inputProperty.getProperty("averageWeightTariff"));
            double maxWeightTariff = Double.parseDouble(inputProperty.getProperty("maxWeightTariff"));

            double minDistanceTariff = Double.parseDouble(inputProperty.getProperty("minDistanceTariff"));
            double averageDistanceTariff = Double.parseDouble(inputProperty.getProperty("averageDistanceTariff"));
            double maxDistanceTariff = Double.parseDouble(inputProperty.getProperty("maxDistanceTariff"));

            System.out.print("Enter weight: ");
            double weight = input.nextDouble();
            dC.setDistance(weight);

            System.out.print("Enter distance: ");
            double distance = input.nextDouble();
            dC.setWeight(distance);


            if (weight <= minWeightTariff) {
                dC.setCostPerKg(0.07);
            }
            if (weight >= minWeightTariff & weight <= averageWeightTariff) {
                dC.setCostPerKg(0.14);
            }
            if (weight >= maxWeightTariff) {
                dC.setCostPerKg(0.21);
            }

            if (distance <= minDistanceTariff) {
                dC.setCostPerKm(0.7);
            }
            if (distance >= minDistanceTariff & weight <= averageDistanceTariff) {
                dC.setCostPerKm(0.85);
            }
            if (distance >= maxDistanceTariff) {
                dC.setCostPerKm(0.9);
            }

            System.out.printf("Total delivery cost = %.2f rub", dC.totalCost(dC.getWeight(), dC.getDistance()));

        } catch (IOException e) {
            System.out.println("Input exception, file not found: " + e);
        }
    }

    public static void main(String args[]) {
        DeliveryCost dC = new DeliveryCost();
        dC.inputWeightAndDistance();
    }
}
