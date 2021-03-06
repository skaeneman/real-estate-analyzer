package bu.met.cs622;

public class InterestRate extends Thread {
    private volatile int count = 3;
    private volatile static double mortgageInterestRate = 3.75; // 30-year fixed interest rate on 2/29/2020

    /**
     * Calculates a new interest rate based off of how many points the borrower buys
     * @param numberOfPoints
     * @return mortgageInterestRate
     */
    public synchronized double calculateMortgagePoints(int numberOfPoints) {
        try {
            double tempInterestRate;
            tempInterestRate = mortgageInterestRate;
            sleep(10);
            System.out.printf("%nThread %s: estimating mortgage points... current rate: %s", Thread.currentThread().getId(), mortgageInterestRate);

            // deduct 25 basis points for each mortgage point
            for (int i = 0; i < numberOfPoints; i++) {
                tempInterestRate = tempInterestRate - 0.25;  // 1 point is equal to 25 basis points (0.25%)
            }
            mortgageInterestRate = tempInterestRate;
            System.out.printf("%nThread %s: deducting %s mortgage points (%s%%), new interest rate: %s",
                    Thread.currentThread().getId(), numberOfPoints, numberOfPoints * 0.25, mortgageInterestRate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mortgageInterestRate;
    }

    /**
     * Calculates a new mortgage interest rate based off of credit scores
     * @param creditScore
     * @return mortgageInterestRate
     */
    public synchronized double calculateMortgageInterestRate(int creditScore) {
        try {
            double tempInterestRate;
            tempInterestRate = mortgageInterestRate;
            sleep(10);
            System.out.printf("%nThread %s: calculating mortgage interest... current rate: %s", Thread.currentThread().getId(), mortgageInterestRate);

            if (creditScore > 680 && creditScore < 740) {
                mortgageInterestRate = tempInterestRate + 0.25;  // 1 point is equal to 25 basis points (0.25%)
                System.out.printf("%nThread %s: adding 25 basis points, new interest rate: %s", Thread.currentThread().getId(), mortgageInterestRate);
            } else if (creditScore <= 680) {
                mortgageInterestRate = tempInterestRate + 0.75;
                System.out.printf("%nThread %s: adding 75 basis points, new interest rate: %s", Thread.currentThread().getId(), mortgageInterestRate);
            } else {
                //score is greater than 740
                System.out.printf("%nThread %s: interest rate: %s", Thread.currentThread().getId(), mortgageInterestRate);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mortgageInterestRate;
    }

    public synchronized void getMortgageInterestRate() {
        System.out.printf("%nThread %s: the mortgage interest rate is: %s", Thread.currentThread().getId(), mortgageInterestRate);
    }

    // runs the demo
    public void run() {
        try {
            System.out.printf("%nThread %s is executing...", Thread.currentThread().getId());
            calculateMortgageInterestRate(725);

            while (count > 0) {
                for (int i = 1; i <= count; i++) {
                    calculateMortgagePoints(i);
                }
                sleep(20);
                count--;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // demo is over exit program
        System.exit(0);
    }

}
