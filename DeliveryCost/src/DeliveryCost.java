import java.util.Scanner;

/**
 * @author Vladislav Kondratuk
 */

public class DeliveryCost {

    static private double costPerKm = 0.85;
    static private double costPerKg = 0.07;
    private double weight;
    private double distance;

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
        return costPerKg * weight + costPerKm * distance;
    }

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        DeliveryCost dC = new DeliveryCost();
        System.out.println("Total delivery cost = "+ costPerKg +"(cost per kg) * weight + "+ costPerKm +"(cost per km) * distance");
        try {
            System.out.print("Enter weight: ");
            double w = input.nextDouble();
            System.out.print("Enter distance: ");
            double d = input.nextDouble();
            dC.setDistance(w);
            dC.setWeight(d);
            System.out.printf("Total delivery cost = %.2f rub", dC.totalCost(dC.getWeight(), dC.getDistance()));
        } catch (java.util.InputMismatchException e) {
            System.out.println("Input exception: " + e);
        }
    }
}
