package bu.met.cs622;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;

public abstract class InvestmentProperty {

    private final double squareFeet;
    private final int principal;
    private final int loanInYears;
    private final double taxes;
    private final double maintenance;
    private final double insurance;
    private final double utilities;
    private final int marketValue;
    private final double rentalIncome;

    // constructor
    public InvestmentProperty(double squareFeet, int principal, int loanInYears, double taxes, double maintenance,
                              double insurance, double utilities, int marketValue, double rentalIncome) {

        this.squareFeet = squareFeet;
        this.principal = principal;
        this.loanInYears = loanInYears;
        this.taxes = taxes;
        this.maintenance = maintenance;
        this.insurance = insurance;
        this.utilities = utilities;
        this.marketValue = marketValue;
        this.rentalIncome = rentalIncome;
    }

    // getter accessor methods
    public double getSquareFeet() { return squareFeet; }
    public int getPrincipal() { return principal; }
    public int getLoanInYears() { return loanInYears; }
    public double getTaxes() { return taxes; }
    public double getMaintenance() { return maintenance; }
    public double getInsurance() { return insurance; }
    public double getUtilities() { return utilities; }
    public int getMarketValue() { return marketValue; }
    public double getRentalIncome() { return  rentalIncome; }

    // display results from the investment analysis run on a property.
    public abstract void display(ArrayList<String> propertyAnalysis);

    // prints results from the investment analysis of a property.
//    public abstract void print(ArrayList<String> propertyAnalysis, String filePath);

    // banks adjust interest rates by property type (primary residence, second home, solely an investment, etc...)
    public abstract void mortgageInterestRate();

    /**
     * Concrete method that returns a property's size in square feet
     * @return      a string representing the total square feet of a house
     */
    public String propertySquareFootage() {
       return String.format("Square feet: %s", getSquareFeet());
    }

    /**
     * Returns the amount of money to be paid each month towards the property's mortgage.
     * @param  principal    the outstanding balance owed on the house
     * @param  loanInYears  the length of the loan in years
     * @param  interest     the interest rate
     * @return      the monthly mortgage payment
     */
    protected final double mortgagePayment(int principal, int loanInYears, double interest) {
        interest = interest / 100.0;  // turn interest rate into a decimal
        double monthlyInterestRate = interest / 12.0;   // interest rate per month
        int loanInMonths = loanInYears * 12;    // total length of the loan in months

        double mortgagePayment = (principal * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -loanInMonths));
        return mortgagePayment;
    }

    /**
     * Returns the Net Operating Income (NOI) of a property which is the
     * Gross Operating Income (GOI) minus any taxes, maintenance, and insurance, etc...
     * @param  grossIncome  the top line revenue of the investment
     * @param  taxes        the amount of money paid to the IRS
     * @param  maintenance  any repair costs for the property
     * @param  insurance    the money spent to insure a property
     * @param  utilities    the cost of water, gas, electric for the property
     * @return      the Net Operating Income (NOI) for a property
     */
    protected final double netOperatingIncome(double grossIncome, double taxes, double maintenance,
                                              double insurance, double utilities) {

        return grossIncome - (taxes + maintenance + insurance + utilities);
    }

    /**
     * Returns the capitalization by taking the Net Operating Income (NOI)
     * and dividing it by the property's value.
     * @param  noi  the net operating income (NOI)
     * @param  marketValue  the current value of the home if it were to be sold today
     * @return      the capitalization rate (cap rate) for a property
     */
    protected final double capitalizationRate(double noi, double marketValue) {
        return (noi / marketValue) * 100;  // return the value as a percentage so multiply by 100
    }

//    //TODO: Unit test
//    /**
//     * Implements abstract method inherited from the parent and outputs results to a file
//     * @param  propertyAnalysis    an ArrayList of strings that describe the property
//     */
//    public void print(ArrayList<String> propertyAnalysis, String filePath) {
//        Formatter outfile = null;
//
//        // get the current date and time and format it
//        LocalDateTime time = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formatDateTime = time.format(formatter);
//
//        // try and write to an output file
//        try {
//            outfile = new Formatter(filePath); // open file
//            outfile.format("********** report timestamp %s **********%n", formatDateTime);
//
//            // loop through items and add them to the report
//            for (String prop : propertyAnalysis) {
//                outfile.format("%s%n", prop);
//            }
//
//            // check if the file was successfully written to
//            Path path = Paths.get(filePath);
//            boolean pathExists = Files.exists(path);
//
//            //TODO:  Fix this
//            System.out.printf("TEST pathExists %s", pathExists);
//            if (pathExists) {
//                System.out.println("File successfully written to");
//            } else {
//                System.err.print("Could not write to file.  Check that the path was correct...");
//            }
//        }
//        catch (FileNotFoundException ex) {
//            System.err.println("Cannot open file... quitting");
//        }
//        outfile.close();
//    }


}
