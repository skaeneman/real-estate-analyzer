package bu.met.cs622;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;

public class MultiFamilyProperty extends InvestmentProperty {

    private double interestRate;

    // constructor
    public MultiFamilyProperty(double interestRate, double squareFeet, int principal, int loanInYears, double taxes,
                               double maintenance, double insurance, double utilities, int marketValue, double rentalIncome) {

        // inherit from parent class
        super(squareFeet, principal ,loanInYears, taxes, maintenance, insurance, utilities, marketValue, rentalIncome);
        this.interestRate = interestRate;
    }

    // getter accessor method
    public double getInterestRate() { return interestRate; }

    // setter mutator method
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void mortgageInterestRate() {

    }

    // override the parent class method and return additional output regarding interest rates
    @Override
    public String propertySquareFootage() {
        return String.format("%s %n%s %s%%", super.propertySquareFootage(),
                             "multi-family interest rate:", getInterestRate());
    }

    /**
     * Expects an Object to be passed in and returns an ArrayList of property details
     * @param  property    a multi-family house object containing attributes that describe the investment property
     * @return             an ArrayList of strings that describe the property after analysis is complete
     */
    public ArrayList<String> analyzeMultiFamilyProperty(MultiFamilyProperty property) {
        // create an array list to hold the analysis output
        ArrayList<String> propertyAnalysisOutput = new ArrayList<>();

        // get data about the property
        int principal = property.getPrincipal();
        int loan = property.getLoanInYears();
        double interest = property.getInterestRate();
        double rentalIncome = property.getRentalIncome();
        double taxes = property.getTaxes();
        double maintenance = property.getMaintenance();
        double insurance = property.getInsurance();
        double utilities = property.getUtilities();
        int marketValue = property.getMarketValue();

        // call methods to run analysis on the property
        double mortgage = property.mortgagePayment(principal, loan, interest);

        double noi = property.netOperatingIncome(rentalIncome, taxes, maintenance, insurance, utilities);

        double capRate = property.capitalizationRate(noi, marketValue);

        propertyAnalysisOutput.add("mortgage payment " + mortgage);
        propertyAnalysisOutput.add("Net Operating Income (NOI) " + noi);
        propertyAnalysisOutput.add("Capitalization Rate: " + capRate);

        return propertyAnalysisOutput;
    }

    /**
     * Implement the abstract method inherited from the parent class to display output
     * @param  propertyAnalysis    an ArrayList of strings that describe the property
     */
    public void display(ArrayList<String> propertyAnalysis) {
        System.out.println("********************* Multi-Family Analysis *******************");
        for (String prop : propertyAnalysis) {
            System.out.println(prop);
        }
//        System.out.println("***************************************************************");
    }

    /**
     * Implements abstract method inherited from the parent and outputs results to a file
     * @param  propertyAnalysis    an ArrayList of strings that describe the property
     */
    public void print(ArrayList<String> propertyAnalysis, String filePath) {
        Formatter outfile = null;

        // get the current date and time and format it
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = time.format(formatter);

        // try and write to an output file
        try {
            outfile = new Formatter(filePath); // open file
            outfile.format("********** report timestamp %s **********%n", formatDateTime);

            // loop through items and add them to the report
            for (String prop : propertyAnalysis) {
                outfile.format("%s%n", prop);
            }

            // check if the file was successfully written to
            Path path = Paths.get(filePath);
            boolean pathExists = Files.exists(path);

            //TODO:  Fix this
            System.out.printf("TEST pathExists %s", pathExists);
            if (pathExists) {
                System.out.println("File successfully written to");
            } else {
                System.err.print("Could not write to file.  Check that the path was correct...");
            }
        }
        catch (FileNotFoundException ex) {
            System.err.println("Cannot open file... quitting");
        }
        outfile.close();
    }

}//class





