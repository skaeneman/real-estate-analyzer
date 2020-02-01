package bu.met.cs622;

/**
 * The RealEstateAnalyzer program will evaluate different types of real estate
 * investments and generate output to see if the investment is potentially profitable.
 *
 * @author  Scott Kaeneman
 * @version 1.0
 * @since   2020-22-01
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//      ArrayList<String> propertyReport;
        double propertyPrice;
        String propertyType = null;

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter 1 for manual input mode or 2 to load data from a file.");
        try {
            String userInput = in.next();

            switch (userInput) {
                case "1":
                    getUserKeyboardInput();
                    break;
                case "2":
                    getUserFileInput();
                    break;
                default:
                    System.err.printf("%s is not a valid option", userInput);
            }
        }
        catch (Exception ex) {
            System.err.printf("User input error...%s", ex.getMessage());
            ex.printStackTrace();
        }


    } //main

    //TODO: write JUnit test
    public static void getUserKeyboardInput() throws Exception {
        ArrayList<String> propertyReport;
        String propertyType;
        HashMap<Boolean, String> wantsToPrint = null;

        // get user input from Scanner
        Scanner input = new Scanner(System.in);

        System.out.println("Enter 'm' for multi-family or 's' for single-family investment property:");
        propertyType = input.nextLine();
        propertyType = propertyType.trim();

        // ensure input is valid
        if (propertyType.equals("m") || propertyType.equals("s")) {
            try {
                System.out.println("Enter price of investment property:");
                Integer propertyPrice = Integer.valueOf(input.nextLine());
                wantsToPrint = getPrintResponse();  // check if user wants to print

            } catch (NumberFormatException e) {
                System.err.println("Not a valid number. exiting program...");
                System.exit(0);
            } catch (IOException ex) {
                // catch the error thrown by getPrintResponse()
                System.err.println("Filepath or file name not valid...");
                ex.printStackTrace();
            }
        } else {
            System.err.println("Sorry that was not a valid choice.  Program ending.");
            System.exit(0);
        }

        // TODO: Break this off into its own method
        // run real estate analysis based upon the type of property the user entered
        if (propertyType.equals("m")) {
            // downcast
            InvestmentProperty multiFamProp = new MultiFamilyProperty(4.7, 3000, 300000,
                    30, 1200, 15000,
                    1200, 6000, 850000,
                    90000);

            if (multiFamProp instanceof MultiFamilyProperty) {
                propertyReport =
                        ((MultiFamilyProperty) multiFamProp).analyzeMultiFamilyProperty((MultiFamilyProperty) multiFamProp);
                ((MultiFamilyProperty) multiFamProp).display(propertyReport);

                // print the report if the HashMap key is set to true
                if (wantsToPrint.containsKey(true)) {
                    String filePathToPrint = wantsToPrint.get(true); // get the file path from the key\value pair
                    if (filePathToPrint != null && !filePathToPrint.isEmpty())  {
                        ((MultiFamilyProperty) multiFamProp).print(propertyReport, filePathToPrint);
                    }
                }
                // output property square feet
                System.out.println(multiFamProp.propertySquareFootage());
            }
        }
        else if (propertyType.equals("s")) {
            // downcast
            InvestmentProperty singleFamProp = new SingleFamilyProperty(3.9, 3230, 400000,
                    20, 12550, 12200,
                    1456, 5600, 950000,
                    75000);

            if (singleFamProp instanceof SingleFamilyProperty) {
                propertyReport = ((SingleFamilyProperty) singleFamProp).analyzeSingleFamilyProperty((SingleFamilyProperty) singleFamProp);
                ((SingleFamilyProperty) singleFamProp).display(propertyReport);
                System.out.println(singleFamProp.propertySquareFootage());
            }
        }
        input.close();
    }


    // TODO: Remove pathname /Users/scott/Desktop/testInput.txt
    // TODO: write JUnit test
    public static void getUserFileInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter full path including filename (e.g. /Users/scott/myFile.txt):");
        String userInputPath = in.nextLine();

        try{
           Scanner infile = new Scanner(new File(userInputPath.trim()));

            while (infile.hasNext())
            {
                System.out.printf("%s%n",
                        infile.nextLine());
                //          infile.next());

            }
            infile.close();
        } catch(FileNotFoundException e) {
            System.err.printf("Cannot open file: %s ", userInputPath.trim());
            System.exit(0);
        }
        in.close();
    }

    /**
     * Determines if the user wants to print the results to a file
     * @return      a HashMap of true/filePath if they want to print, or false\null
     */
    public static HashMap<Boolean, String> getPrintResponse() throws Exception {
        String filePath = null;
        String print;

        // create a HashMap that will hold the valued returned to the caller
        HashMap<Boolean, String> printMap = new HashMap<Boolean, String>();

        Scanner in = new Scanner(System.in);

        System.out.println("Write output to file (yes/no):");
        print = in.nextLine();

        // print to a file if user has selected to do so
        if ((print.equalsIgnoreCase("yes")) || (print.equalsIgnoreCase("y"))) {
            System.out.println("Enter file path and file name to generate output (e.g. C:\\report.txt)");
            filePath = in.nextLine();
            printMap.put(true, filePath);  // user wants to print so store the file path
        } else {
            // user does not want to print the results
            printMap.put(false, null);
        }
        return printMap; // return the HashMap to the caller
    }


}// class
