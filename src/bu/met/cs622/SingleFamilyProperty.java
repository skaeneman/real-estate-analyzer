package bu.met.cs622;

import java.util.ArrayList;

public class SingleFamilyProperty extends InvestmentProperty {

    // constructor
    public SingleFamilyProperty(double interestRate, double squareFeet, int principal, int loanInYears, double taxes,
                               double maintenance, double insurance, double utilities, int marketValue, double rentalIncome) {
        super(squareFeet, principal ,loanInYears, taxes, maintenance, insurance, utilities, marketValue, rentalIncome);
    }

    @Override
    public void mortgageInterestRate() {

    }


    /**
     * Expects an Object to be passed in and returns an ArrayList of property details
     *
     * @param  property    a single-family house object containing attributes that describe the investment property
     *
     * @return             an ArrayList of strings that describe the property after analysis is complete
     */
    public ArrayList<String> analyzeSingleFamilyProperty(SingleFamilyProperty property) {
        // create an array list to hold the analysis output
        ArrayList<String> propertyAnalysisOutput = new ArrayList<>();

//        double mortgage = property.mortgagePayment(property.principal, property.loanInYears, property.interestRate);
//
//        double noi = property.netOperatingIncome(property.rentalIncome, property.taxes, property.maintenance,
//                property.insurance, property.utilities);
//
//        propertyAnalysisOutput.add("mortgage payment " + mortgage);
//        propertyAnalysisOutput.add("Net Operating Income (NOI) " + noi);

        return propertyAnalysisOutput;
    }





    // implement the abstract method inherited from the parent class
    public void display(ArrayList<String> propertyAnalysis) {
//        SingleFamilyProperty singleFamily = new SingleFamilyProperty(5, 3000);
//        double mortgage = singleFamily.mortgagePayment(300000, 30, 5);
//        double noi = singleFamily.netOperatingIncome(90000, 12000,15000,
//                1200, 6000);
//
//        System.out.println("single-family house mortgage: " + mortgage);
//        System.out.println("single-family house Net Operating Income (NOI): " + noi);
    }


}
