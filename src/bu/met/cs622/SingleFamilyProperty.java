package bu.met.cs622;

public class SingleFamilyProperty extends InvestmentProperty {

    // constructor
    public SingleFamilyProperty(double interestRate, double squareFeet) {
        super(squareFeet);
    }

    @Override
    public void mortgageInterestRate() {

    }

    // implement the abstract method inherited from the parent class
    public void display() {
        SingleFamilyProperty singleFamily = new SingleFamilyProperty(5, 3000);
        double mortgage = singleFamily.mortgagePayment(300000, 30, 5);
        double noi = singleFamily.netOperatingIncome(90000, 12000,15000,
                1200, 6000);

        System.out.println("single-family house mortgage: " + mortgage);
        System.out.println("single-family house Net Operating Income (NOI): " + noi);
    }


}
