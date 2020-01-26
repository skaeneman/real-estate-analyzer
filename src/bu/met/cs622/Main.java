package bu.met.cs622;

/**
 * The RealEstateAnalyzer program will evaluate different types of real estate
 * investments and generate output to see if the investment is potentially profitable.
 *
 * @author  Scott Kaeneman
 * @version 1.0
 * @since   2020-22-01
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // get user input from Scanner
        Scanner input = new Scanner(System.in);

        System.out.println("Enter type of investment property:");
        String propertyType = input.nextLine();

        System.out.println("Enter price of investment property:");
        double propertyPrice = input.nextDouble();

        // run real estate analysis based upon the type of property the user entered
        if (propertyType.equals("m")) {
            MultiFamilyProperty multiFam = new MultiFamilyProperty();
            double mortgage = multiFam.mortgagePayment(300000, 30, 5);
            System.out.println("mortgage payment " + mortgage);
        }
        else if (propertyType.equals("s")) {
            // instantiate a new SingleFamilyProperty object and call its methods
            SingleFamilyProperty singleFam = new SingleFamilyProperty();
            double mortgage = singleFam.mortgagePayment(300000, 30, 5);
            System.out.println("mortgage payment " + mortgage);
        }

    } //main
}
