abstract class InvestmentProperty {

    /**
     * Returns an object representing the amount of money that needs
     * to be paid each month to the bank for a mortgage payment.
     *
     * @param  principal    the outstanding balance owed on the house
     * @param  loanInYears  the length of the loan in years
     * @param  interest     the interest rate
     *
     * @return      the monthly mortgage payment
     */
    public abstract double mortgagePayment(int principal, int loanInYears, double interest);
}
