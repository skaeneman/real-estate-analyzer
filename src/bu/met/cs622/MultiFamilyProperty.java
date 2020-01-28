package bu.met.cs622;

public class MultiFamilyProperty extends InvestmentProperty {

    private double interestRate;

    // constructor
    public MultiFamilyProperty(double interestRate, double squareFeet) {
        super(squareFeet);  // inherit from parent class
        this.interestRate = interestRate;
    }

    // getter for interestRate
    public double getInterestRate() { return interestRate; }

    // setter for interestRate
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void mortgageInterestRate() {

    }

    @Override
    public String propertySquareFootage() {
        return String.format("%s %n%s %s%%", super.propertySquareFootage(),
                             "multi-family interest rate:", getInterestRate());
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

        // display interest rate with polymorphism
//        System.out.println(multiFam.currentInterestRate());

        System.out.println("***************************************************************");
    }

}





