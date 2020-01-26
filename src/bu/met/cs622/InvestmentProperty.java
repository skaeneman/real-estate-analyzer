package bu.met.cs622;

public abstract class InvestmentProperty {

    /**
     * Display results from the investment analysis run on a property.
     */
    public abstract void display();

    /**
     * Returns an object representing the amount of money to be
     * paid each month as a mortgage payment.
     *
     * @param  principal    the outstanding balance owed on the house
     * @param  loanInYears  the length of the loan in years
     * @param  interest     the interest rate
     *
     * @return      the monthly mortgage payment
     */
    protected final double mortgagePayment(int principal, int loanInYears, double interest) {
        interest = interest / 100.0;  // turn interest rate into a decimal
        double monthlyInterestRate = interest / 12.0;   // interest rate per month
        int loanInMonths = loanInYears * 12;    // total length of the loan in months

        double mortgagePayment = (principal * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -loanInMonths));
        return mortgagePayment;
    }


}
