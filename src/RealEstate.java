import java.util.Scanner;

public class RealEstate {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // get user input from Scanner
        System.out.println("Enter type of investment property:");
        String propertType = input.nextLine();

        System.out.println("Enter price of investment property:");
        double propertyPrice = input.nextDouble();

        // run real estate analysis based upon the type of property the user entered
        if (propertType.equals("m")) {
            // instantiate a new MultiFamilyHouse object and call its methods
            MultiFamilyHouse investment = new MultiFamilyHouse();
            double mortgage = investment.mortgagePayment(300000, 30, 5);
            System.out.println("mortgage payment " + mortgage);
        }
        else if (propertType == "single-family") {
            // instantiate a new SingleFamilyHouse object and call its methods
            SingleFamilyHouse investment = new SingleFamilyHouse();
        }



    } //main
}
