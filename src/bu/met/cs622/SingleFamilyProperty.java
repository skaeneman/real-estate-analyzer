package bu.met.cs622;

public class SingleFamilyProperty extends InvestmentProperty {

    public SingleFamilyProperty() {

    }

    // implement the abstract method inherited from the parent class
    public void display() {
        SingleFamilyProperty singleFamily = new SingleFamilyProperty();
        double mortgage = singleFamily.mortgagePayment(300000, 30, 5);
        double noi = singleFamily.netOperatingIncome(90000, 12000,15000,
                1200, 6000);

        System.out.println("single-family house mortgage: " + mortgage);
        System.out.println("single-family house Net Operating Income (NOI): " + noi);
    }
}
