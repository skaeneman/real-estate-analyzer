package bu.met.cs622;

import java.util.ArrayList;

public class MultiFamilyProperty extends InvestmentProperty {

    private double interestRate;
    private final int principal;
    private final int loanInYears;
    private final double taxes;
    private final double maintenance;
    private final double insurance;
    private final double utilities;
    private final int marketValue;
    private final double rentalIncome;

    // constructor
    public MultiFamilyProperty(double interestRate, double squareFeet, int principal, int loanInYears, double taxes,
                               double maintenance, double insurance, double utilities, int marketValue, double rentalIncome) {

        super(squareFeet);  // inherit from parent class
        this.interestRate = interestRate;
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
    public double getInterestRate() { return interestRate; }
    public int getPrincipal() { return principal; }
    public int getLoanInYears() { return loanInYears; }
    public double getTaxes() { return taxes; }
    public double getMaintenance() { return maintenance; }
    public double getInsurance() { return insurance; }
    public double getUtilities() { return utilities; }
    public int getMarketValue() { return marketValue; }
    public double getRentalIncome() { return  rentalIncome; }

    // setter mutator method for interestRate
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
     *
     * @param  property    a multi-family house object containing attributes that describe the investment property
     *
     * @return             an ArrayList of strings that describe the property after analysis is complete
     */
    public ArrayList<String> analyzeProperty(MultiFamilyProperty property) {
        // create an array list to hold the analysis output
        ArrayList<String> propertyAnalysisOutput = new ArrayList<>();

        double mortgage = property.mortgagePayment(property.principal, property.loanInYears, property.interestRate);

        double noi = property.netOperatingIncome(property.rentalIncome, property.taxes, property.maintenance,
                                                 property.insurance, property.utilities);

        double capRate = property.capitalizationRate(noi, property.marketValue);

        propertyAnalysisOutput.add("mortgage payment " + mortgage);
        propertyAnalysisOutput.add("Net Operating Income (NOI) " + noi);
        propertyAnalysisOutput.add("Capitalization Rate: " + capRate);

        return propertyAnalysisOutput;
    }

    // implement the abstract method inherited from the parent class
    public void display(ArrayList<String> propertyAnalysis) {
        System.out.println("********************* Multi-Family Analysis *******************");
        for (String prop : propertyAnalysis) {
            System.out.println(prop);
        }
        System.out.println("***************************************************************");
    }

}//class





