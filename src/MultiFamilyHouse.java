public class MultiFamilyHouse extends InvestmentProperty {

    // implement the abstract method inherited from the superclass
    public double mortgagePayment(int principal, int loanInYears, double interest) {
        interest = interest / 100.0;  // turn interest rate into a decimal
        double monthlyInterestRate = interest / 12.0;   // interest rate per month
        int loanInMonths = loanInYears * 12;    // total length of the loan in months

        double mortgagePayment = (principal * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -loanInMonths));
        return mortgagePayment;
    }




}

