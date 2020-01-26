public class SingleFamilyProperty extends InvestmentProperty {

    // implement the abstract method inherited from the superclass
    public void display() {
        System.out.println("this will eventually display something...");
    }

    SingleFamilyProperty singleFamily = new SingleFamilyProperty();
    double monthlyMortgage = singleFamily.mortgagePayment(100, 12, 34);


}
