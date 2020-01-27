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

    /**
     * Returns the Net Operating Income (NOI) of a property which is the
     * Gross Operating Income (GOI) minus any taxes, maintenance, and insurance, etc...
     *
     * @param  grossIncome  the top line revenue of the investment
     * @param  taxes        the amount of money paid to the IRS
     * @param  maintenance  any repair costs for the property
     * @param  insurance    the money spent to insure a property
     * @param  utilities    the cost of water, gas, electric for the property
     *
     * @return      the Net Operating Income (NOI) for a property
     */
    protected final double netOperatingIncome(double grossIncome, double taxes, double maintenance,
                                              double insurance, double utilities) {

        return grossIncome - (taxes + maintenance + insurance + utilities);
    }

    /**
     * Returns the capitalization rate for a property by first calculating the
     * Net Operating Income (NOI) and then dividing it by the property's value.
     * Capitalization Rate = Net Operating Income / Current Market Value
     *
     * @param  grossIncome  the top line revenue of the investment
     * @param  taxes        the amount of money paid to the IRS
     * @param  maintenance  any repair costs for the property
     * @param  insurance    the money spent to insure a property
     * @param  utilities    the cost of water, gas, electric for the property
     * @param  marketValue  the current value of the home if it were to be sold today
     *
     * @return      the capitalization rate (cap rate) for a property
     */
    protected final double capitalizationRate(double grossIncome, double taxes, double maintenance,
                                              double insurance, double utilities, double marketValue) {

        // return the value as a percentage so multiply by 100
        return ((grossIncome - (taxes + maintenance + insurance + utilities)) / marketValue) * 100;
    }


}
