package bu.met.cs622;

/**
 * The RealEstateAnalyzer program will evaluate different types of real estate
 * investments and generate output to see if the investment is potentially profitable.
 *
 * @author  Scott Kaeneman
 * @version 1.0
 * @since   2020-22-01
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


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
            System.err.printf("User input error...%nprinting stack trace...%n%s", ex.getMessage());
            ex.printStackTrace();
        }


    } //main

    public static void getUserKeyboardInput() throws Exception {
        ArrayList<String> propertyReport;
        String propertyType;
        String propertyCity;
        String propertyState;
        String fetchYelpBiz = null;
        int principal = 0;
        int loanInYears = 0;
        int propertyValue = 0;
        int propertyPrice = 0;
        double propertyTaxes = 0;
        double maintenance = 0;
        double propertyInsurance = 0;
        double utilities = 0;
        double rentalIncome = 0;
        double interestRate = 0;
        double squareFeet = 0;
        boolean saveToDatabase = false;
        HashMap<Boolean, String> wantsToPrint = null;

        // get user input from Scanner
        Scanner input = new Scanner(System.in);

        // show a demo of concurrency using multiple threads that are synchronized
        System.out.println("Would you like to see a demo of concurrency (yes/no):");
        String concurrencyDemo = input.nextLine();

        // execute the multi-threaded demo
        if ((concurrencyDemo.equalsIgnoreCase("yes")) || (concurrencyDemo.equalsIgnoreCase("y"))) {
            System.out.println("Starting concurrency demo...");
            InterestRate thread1 = new InterestRate();
            Thread thread2 = new Thread(thread1);
            Thread thread3 = new Thread(thread1);

            ArrayList<Thread> threadArray = new ArrayList<>(); // create array

            // add the threads to the array
            threadArray.addAll(Arrays.asList(thread1, thread2, thread3));

            // loop through array and start up all the threads
            for (Thread t : threadArray) {
                t.start();
            }
        }

        System.out.println("Enter 'm' for multi-family or 's' for single-family investment property:");
        propertyType = input.nextLine().trim();

        System.out.println("Enter the CITY where the property is located: ");
        propertyCity = input.nextLine().trim();

        System.out.println("Enter the STATE where the property is located: ");
        propertyState = input.nextLine().trim();

        // prompt for input and verify type is a double
        interestRate = verifyDoubleInput("Enter the INTEREST RATE of the loan: ");
        squareFeet = verifyDoubleInput("Enter the size of the property in SQUARE FEET: ");
        propertyTaxes = verifyDoubleInput("Enter the annual PROPERTY TAXES: ");
        maintenance = verifyDoubleInput("Enter the annual MAINTENANCE budget: ");
        propertyInsurance = verifyDoubleInput("Enter the annual PROPERTY INSURANCE cost: ");
        utilities = verifyDoubleInput("Enter the estimated annual cost of UTILITIES: ");
        rentalIncome = verifyDoubleInput("Enter the projected annual RENTAL INCOME: ");

        // prompt for input and verify type is an integer
        propertyValue = verifyIntInput("Enter the current MARKET VALUE of the property: ");
        principal = verifyIntInput("Enter the PRINCIPAL loan amount: ");
        loanInYears = verifyIntInput("Enter the length of the MORTGAGE LOAN in YEARS: ");

        // ensure input is valid
        if (propertyType.equals("m") || propertyType.equals("s")) {
            try {
                propertyPrice = verifyIntInput("Enter price of investment property: ");

                wantsToPrint = getPrintResponse();  // check if user wants to print

                System.out.println("Save results to database (yes/no): ");
                String saveToDb = input.nextLine().trim();

                // save content to database
                if (saveToDb.equalsIgnoreCase("yes") || saveToDb.equalsIgnoreCase("y")) {
                    saveToDatabase = true;
                }

                // get data from yelp API
                System.out.printf("Fetch data about local businesses (yes/no): ");
                fetchYelpBiz = input.nextLine().trim();

            } catch (NumberFormatException e) {
                System.err.printf("error: Not a valid number %s. %nexiting program...", e.getMessage());
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            System.err.println("Sorry that was not a valid choice.  Program ending.");
            System.exit(0);
        }

        // run real estate analysis based upon the type of property the user entered
        if (propertyType.equals("m")) {
            // downcast
            InvestmentProperty multiFamProp = new MultiFamilyProperty(interestRate, squareFeet, principal, loanInYears,
                    propertyTaxes, maintenance, propertyInsurance, utilities, propertyValue, rentalIncome);

            if (multiFamProp instanceof MultiFamilyProperty) {
                propertyReport =
                        ((MultiFamilyProperty) multiFamProp).analyzeMultiFamilyProperty((MultiFamilyProperty) multiFamProp);
                ((MultiFamilyProperty) multiFamProp).display(propertyReport);

                // output property square feet
                System.out.println(multiFamProp.propertySquareFootage());

                // print the report if the HashMap key is set to true
                if (wantsToPrint.containsKey(true)) {
                    String filePathToPrint = wantsToPrint.get(true); // get the file path from the key\value pair
                    if (filePathToPrint != null && !filePathToPrint.isEmpty())  {
                        print(propertyReport, filePathToPrint);
                    }
                }

                // get data from yelp API
                if (fetchYelpBiz.equalsIgnoreCase("yes") || fetchYelpBiz.equalsIgnoreCase("y")) {
                    System.out.printf("Fetching data from Yelp API...%n");
                    getYelpData(propertyCity, propertyState, saveToDatabase);
                }
            }
        }
        else if (propertyType.equals("s")) {
            // downcast
            InvestmentProperty singleFamProp = new SingleFamilyProperty(interestRate, squareFeet, principal, loanInYears,
                    propertyTaxes, maintenance, propertyInsurance, utilities, propertyValue, rentalIncome);

            if (singleFamProp instanceof SingleFamilyProperty) {
                propertyReport = ((SingleFamilyProperty) singleFamProp).analyzeSingleFamilyProperty((SingleFamilyProperty) singleFamProp);
                ((SingleFamilyProperty) singleFamProp).display(propertyReport);
                System.out.println(singleFamProp.propertySquareFootage());

                // print the report if the HashMap key is set to true
                if (wantsToPrint.containsKey(true)) {
                    String filePathToPrint = wantsToPrint.get(true); // get the file path from the key\value pair
                    if (filePathToPrint != null && !filePathToPrint.isEmpty())  {
                        print(propertyReport, filePathToPrint);
                    }
                }

                // get data from yelp API
                if (fetchYelpBiz.equalsIgnoreCase("yes") || fetchYelpBiz.equalsIgnoreCase("y")) {
                    System.out.printf("Fetching data from Yelp API...%n");
                    getYelpData(propertyCity, propertyState, saveToDatabase);
                }
            }
        }
        input.close();
    }

    /**
     * Verifies that the input is of type Double.  Keeps looping if not a double.
     * @param inputToValidate   the string response to validate is a double
     */
    public static double verifyDoubleInput(String inputToValidate) {
        Scanner sc = new Scanner(System.in);
        double number;
        boolean error = false;
        // keep looping if the input is not a double
        System.out.println(inputToValidate);
        while (!sc.hasNextDouble()) {
            System.out.println("Please enter a number...");
            sc.next();
            error = true;
        }
        error = false;
        number = sc.nextDouble();
        return number;
    }

    /**
     * Verifies that the input is of type integer.  Keeps looping if not a integer.
     * @param inputToValidate   the string response to validate is a integer
     */
    public static int verifyIntInput(String inputToValidate) {
        Scanner sc = new Scanner(System.in);
        int number;
        boolean error = false;
        // keep looping if the input is not a integer
        System.out.println(inputToValidate);
        while (!sc.hasNextInt()) {
            System.out.println("Please enter a number...");
            sc.next();
            error = true;
        }
        error = false;
        number = sc.nextInt();
        return number;
    }

    /**
     * Will call secondary methods to read-in a text file or a binary file
     */
    public static void getUserFileInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter full path including filename (e.g. /Users/scott/myFile.txt):");
        String userInputPath = in.nextLine();

        // check the extension on the file the user entered
        String extension = getFileExtension(userInputPath.trim());

        // if the extension is a .txt or .rtf then read-in as a text file
        if (extension.equals("txt") || extension.equals("rtf")) {
            System.out.println("File extension .txt or .rtf found....processing as text a file...");
            getTextFile(userInputPath);
        } else if (extension.equals("dat")) {
            System.out.println("File extension .dat found....processing as a data file...");
            getBinaryFile(userInputPath);
        }
        else {
            System.out.println("Please enter a file that hs an extension of .txt, .rtf, or .dat");
        }
        in.close();
    }

    /**
     * Reads in a binary object file to the application
     * @param userInputPath     the path to the binary file
     */
    public static void getBinaryFile(String userInputPath) {
        String input = userInputPath.trim();
        try (ObjectInputStream infile = new ObjectInputStream(new FileInputStream(input))) {
            while (true)
            {
                System.out.printf("%s%n", infile.readObject());
            }
        } catch (EOFException eof) {
            System.out.println("Done reading in binary file...");
        } catch (IOException | ClassNotFoundException ex) {
            System.err.printf("Cannot open binary file: %s ", userInputPath.trim());
            ex.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Reads in a text file to the application
     * @param userInputPath     the path to the text file
     */
    public static void getTextFile(String userInputPath) {
        try{
            Scanner infile = new Scanner(new File(userInputPath.trim()));

            while (infile.hasNext())
            {
                System.out.printf("%s%n", infile.nextLine());
                //          infile.next());
            }
            infile.close();
        } catch(FileNotFoundException e) {
            System.err.printf("Cannot open text file: %s ", userInputPath.trim());
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Determines if the user wants to print the results to a file
     * @return      a HashMap of true/filePath if they want to print, or false\null
     */
    public static HashMap<Boolean, String> getPrintResponse() {
        String filePath = null;
        String print;

        // create a HashMap that will hold the value returned to the caller
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

    /**
     * It will either print to a text file or write data to stream depending on user input
     * @param  propertyAnalysis    an ArrayList of strings that describe the property
     * @param  filePath            the path where the file should be sent to as output
     */
    public static void print(ArrayList<String> propertyAnalysis, String filePath) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter 1 for TEXT output or 2 for BINARY output");
        try {
            String userInput = in.next();

            switch (userInput) {
                case "1":
                    printText(propertyAnalysis, filePath);
                    break;
                case "2":
                    printBinary(propertyAnalysis, filePath);
                    break;
                default:
                    System.err.printf("%s is not a valid option", userInput);
            }
        }
        catch (Exception ex) {
            System.err.printf("User input error...%nprinting stack trace...%n%s", ex.getMessage());
            ex.printStackTrace();
        }
        in.close();
    }

    /**
     * Prints the output of the property analysis to a text file
     * @param  propertyAnalysis    an ArrayList of strings that describe the property
     * @param  filePath            the path where the file should be sent to as output
     */
    public static void printText(ArrayList<String> propertyAnalysis, String filePath) {
        Formatter outfile = null;

        // get the current date and time and format it
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = time.format(formatter);

        // try and write to an output file
        try {
            outfile = new Formatter(filePath); // open file
            outfile.format("******************************************%n", formatDateTime);
            outfile.format("Property Investment Report %n");
            outfile.format("Timestamp: %s%n", formatDateTime);
            outfile.format("******************************************%n%n", formatDateTime);

            // loop through items and add them to the report
            for (String prop : propertyAnalysis) {
                outfile.format("%s%n", prop);
            }

            // check if the file was successfully written to
            Path path = Paths.get(filePath);
            boolean pathExists = Files.exists(path);
            File file = new File(String.valueOf(path));

            if (pathExists) {
                System.out.printf("File successfully written to: %s%n", file.getAbsolutePath());
            } else {
                System.err.print("Could not write to file.  Check that the path is correct...");
            }
        }
        catch (FileNotFoundException ex) {
            System.err.printf("Uh-oh cannot write to file path: %s ...exiting", filePath);
            System.exit(0);
        }
        outfile.close();
    }

    /**
     * Prints an object output stream of the property analysis to a binary file
     * @param  propertyAnalysis    an ArrayList of strings that describe the property
     * @param  filePath            the path where the file should be sent to as output
     */
    public static void printBinary(ArrayList<String> propertyAnalysis, String filePath) {
        // check the extension on the file the user entered
        String fileExtension = getFileExtension(filePath);

        // route to the other method to print to a text file if the file extension is a .txt or .rtf
        if (fileExtension.equals("txt") || fileExtension.equals("rtf")) {
            System.out.println("Oops you entered a text file extension....printing to a text file...");
            printText(propertyAnalysis, filePath); // call method that handles printing text
        } else {
            // the file is a data file so print the object stream
            try {
                try (ObjectOutputStream outfile = new ObjectOutputStream(new FileOutputStream(filePath))) {

                    // loop through objects and add them to the output stream report
                    for (String propertyObj : propertyAnalysis) {
                        outfile.writeObject(propertyObj);
                    }
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Check the file extension of a file
     * @param filePath     the path to the file that will have the extension checked
     * @return             the file extension or an empty string
     */
    public static String getFileExtension(String filePath) {
        // filter for a "." then return the characters after the "."
        Optional<String> extension = Optional.ofNullable(filePath)
                .filter(file -> file.contains("."))  // find the extension
                .map(ext -> ext.substring(filePath.lastIndexOf(".") + 1));
        return extension.get(); // return just the String from the Optional
    }

    /**
     * Processes data returned from the Yelp API.  Uses a generic stack class to
     * store the data returned from yelp.
     * @param state     the state to search for businesses in
     * @param city      the city to search for businesses in
     * @param saveToDatabase boolean value that determines if results should be saved to a DB
     */
    public static void getYelpData(String city, String state, boolean saveToDatabase) throws IOException {
        // use googles gson library for handling json objects
        Gson gson = new Gson();

        // get data from yelp API
        YelpAPI yelpAPIData = new YelpAPI();
        String yelp = yelpAPIData.getYelpData(city, state);

        // process the json data with gson
        YelpBusinesses jsonYelp =  gson.fromJson(yelp, YelpBusinesses.class);
        List<YelpBusinesses> businesses = jsonYelp.getBusinesses();

        // instantiate a generic string stack
        GenericStack<String> businessInfo = new GenericStack<>();

        // instantiate an object of the database class
        RealEstateDB db = new RealEstateDB();

        for (int i = 0; i < businesses.size(); i++) {
            // convert meters to miles
            Double meters = businesses.get(i).getDistance();
            double miles = meters * 0.00062137119;

            // push elements onto the generic stack
            businessInfo.push("url: " + businesses.get(i).getUrl());
            businessInfo.push("distance: " + miles + " miles");
            businessInfo.push("business closed: " + businesses.get(i).getClosed());
            businessInfo.push("rating: " + businesses.get(i).getRating());
            businessInfo.push(businesses.get(i).getLocation().getCity() + ", "
                    + businesses.get(i).getLocation().getState() + " " + businesses.get(i).getLocation().getZipCode());
            businessInfo.push(businesses.get(i).getLocation().getAddress1());
            businessInfo.push(businesses.get(i).getName());
            businessInfo.push(" ");

            if (saveToDatabase) {
                // store the businesses data to the business and location database tables
                db.insertBusinessAndLocationTableData(businesses.get(i).getName(), businesses.get(i).getUrl(),
                        miles, businesses.get(i).getRating(), businesses.get(i).getClosed(),
                        businesses.get(i).getLocation().getCity(), businesses.get(i).getLocation().getCountry(),
                        businesses.get(i).getLocation().getAddress1(), businesses.get(i).getLocation().getState(),
                        businesses.get(i).getLocation().getZipCode());
            }
        }

        // find the closest business to the property
        YelpBusinesses closestBiz = yelpAPIData.findNearestBusiness(businesses);

        System.out.printf("%nFinding closest business to the investment property...%n");
        System.out.printf("'%s' is the shortest distance away (%s miles)%n", closestBiz.getName(),
                closestBiz.getDistance() * 0.00062137119); // convert to miles

        // output data by popping elements off the generic stack
        for (int i = 0; i < businessInfo.stackSize(); i++) {
            String output = businessInfo.pop();
            System.out.println(output);
        }
    }


}// class
