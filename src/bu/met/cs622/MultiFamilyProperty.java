package bu.met.cs622;

public class MultiFamilyProperty extends InvestmentProperty {

    public MultiFamilyProperty() {

    }

    // implement the abstract method inherited from the parent class
    public void display() {
        MultiFamilyProperty multiFam = new MultiFamilyProperty();
        double mortgage = multiFam.mortgagePayment(300000, 30, 5);

        double noi = multiFam.netOperatingIncome(90000, 12000,15000,
                1200, 6000);

        double capRate = multiFam.capitalizationRate(90000, 850000);

        System.out.println("********************* Multi-Family Analysis *******************");
        System.out.println("mortgage payment " + mortgage);
        System.out.println("Net Operating Income (NOI) " + noi);
        System.out.println("Capitalization Rate: " + capRate);
        System.out.println("***************************************************************");
    }


}





