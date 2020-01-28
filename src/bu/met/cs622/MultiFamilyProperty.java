package bu.met.cs622;

public class MultiFamilyProperty extends InvestmentProperty {

    // constructor
    public MultiFamilyProperty(double interestRate, double squareFeet) {
        super(interestRate, squareFeet);
    }

    @Override
    public String currentInterestRate() {
        return String.format("calling super from child class in Override method: %s%n", super.currentInterestRate());
    }

    // implement the abstract method inherited from the parent class
    public void display() {
        MultiFamilyProperty multiFam = new MultiFamilyProperty(6.2, 2500);

//        InvestmentProperty multiFam = new MultiFamilyProperty(5.1, 3000);
//        if (multiFam instanceof MultiFamilyProperty) {
//            ((MultiFamilyProperty) multiFam).setInterestRate(3.7);
//        }


        double mortgage = multiFam.mortgagePayment(300000, 30, 5);

        double noi = multiFam.netOperatingIncome(90000, 12000,15000,
                1200, 6000);

        double capRate = multiFam.capitalizationRate(90000, 850000);

        System.out.println("********************* Multi-Family Analysis *******************");
        System.out.println("mortgage payment " + mortgage);
        System.out.println("Net Operating Income (NOI) " + noi);
        System.out.println("Capitalization Rate: " + capRate);

        System.out.println("Rate....: " + multiFam.currentInterestRate());

        System.out.println("***************************************************************");
    }


}





