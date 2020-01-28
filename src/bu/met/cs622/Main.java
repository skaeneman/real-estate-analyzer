package bu.met.cs622;

/**
 * The RealEstateAnalyzer program will evaluate different types of real estate
 * investments and generate output to see if the investment is potentially profitable.
 *
 * @author  Scott Kaeneman
 * @version 1.0
 * @since   2020-22-01
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> propertyReport;

        // get user input from Scanner
        Scanner input = new Scanner(System.in);

        System.out.println("Enter type of investment property:");
        String propertyType = input.nextLine();

        System.out.println("Enter price of investment property:");
        double propertyPrice = input.nextDouble();

        // run real estate analysis based upon the type of property the user entered
        if (propertyType.equals("m")) {
            // downcast
            InvestmentProperty multiFamProp = new MultiFamilyProperty(4.7, 3000, 300000,
                                                                      30, 1200, 15000,
                                                                      1200, 6000, 850000,
                                                                      90000);

            if (multiFamProp instanceof MultiFamilyProperty) {
                propertyReport = ((MultiFamilyProperty) multiFamProp).analyzeMultiFamilyProperty((MultiFamilyProperty) multiFamProp);
                ((MultiFamilyProperty) multiFamProp).display(propertyReport);
                System.out.println(multiFamProp.propertySquareFootage());
            }
        }
        else if (propertyType.equals("s")) {
            // downcast
//            InvestmentProperty singleFamProp = new SingleFamilyProperty(4.5, 3500);
//            if (singleFamProp instanceof SingleFamilyProperty) {
//                propertyReport = ((SingleFamilyProperty) singleFamProp).analyzeSingleFamilyProperty((SingleFamilyProperty) singleFamProp);
//            }
        }

    } //main
}
