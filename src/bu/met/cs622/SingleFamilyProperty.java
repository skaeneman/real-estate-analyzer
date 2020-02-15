package bu.met.cs622;

import java.util.ArrayList;

public class SingleFamilyProperty extends InvestmentProperty {

    private double interestRate;

    // constructor
    public SingleFamilyProperty(double interestRate, double squareFeet, int principal, int loanInYears, double taxes,
                               double maintenance, double insurance, double utilities, int marketValue, double rentalIncome) {
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

    // override parent class method, return additional output for single-family interest rates
    @Override
    public String propertySquareFootage() {
        return String.format("%s %n%s %s%%", super.propertySquareFootage(),
                "single-family rate:", getInterestRate());
    }

    /**
     * Expects an Object to be passed in and returns an ArrayList of property details
     * @param  property    a single-family house object containing attributes that describe the investment property
     * @return             an ArrayList of strings that describe the property after analysis is complete
     */
    public ArrayList<String> analyzeSingleFamilyProperty(SingleFamilyProperty property) {
        // create an array list to hold the analysis output
        ArrayList<String> propertyAnalysisOutput = new ArrayList<>();

        // get data about the property
        int principal = property.getPrincipal();
        int loan = property.getLoanInYears();
        double rentalIncome = property.getRentalIncome();
        double taxes = property.getTaxes();
        double maintenance = property.getMaintenance();
        double insurance = property.getInsurance();
        double utilities = property.getUtilities();
        int marketValue = property.getMarketValue();
        double interest = property.getInterestRate();

        // call methods to run analysis on the property
        double mortgage = property.mortgagePayment(principal, loan, interest);
        double noi = property.netOperatingIncome(rentalIncome, taxes, maintenance, insurance, utilities);

        propertyAnalysisOutput.add("Single-Family mortgage payment " + mortgage);
        propertyAnalysisOutput.add("Single-Family Net Operating Income (NOI) " + noi);

        return propertyAnalysisOutput;
    }

    /**
     * Implement the abstract method inherited from the parent class to display output
     * @param  propertyAnalysis    an ArrayList of strings that describe the property
     * @return                     nothing
     */
    public void display(ArrayList<String> propertyAnalysis) {
        System.out.println("************** Single-Family Property Analysis ****************");

        propertyAnalysis.stream()
                .forEach(System.out::print);

//        for (String singleProp : propertyAnalysis) {
//            System.out.println(singleProp);
//        }
    }



}
