import java.util.Scanner;

public class RealEstate {

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);

        // get user input from Scanner
        System.out.println("Enter type of investment property:");
        String propertType = myObj.nextLine();

        System.out.println("Enter price of investment property:");
        double propertyPrice = myObj.nextDouble();

        // run real estate analysis based upon the type of property the user entered
        if (propertType.equals("m")) {
            MultiFamilyHouse investment = new MultiFamilyHouse();
            double price = investment.annualCost(100000, 30);
            System.out.println("price from MultiFamilyHouse: " + price);
        }
        else if (propertType == "single-family") {
            SingleFamilyHouse investment = new SingleFamilyHouse();
        }



    } //main
}
