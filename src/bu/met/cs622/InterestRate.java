package bu.met.cs622;

public class InterestRate extends Thread {
    private int count = 3;
    private static double mortgageInterestRate = 3.75; // 30-year fixed interest rate on 2/29/2020

    /**
     *
     * @param numberOfPoints
     * @return mortgageInterestRate
     */
    public synchronized double calculateMortgagePoints(int numberOfPoints) {
        try {
            double tempInterestRate;
            tempInterestRate = mortgageInterestRate;
            double newInterestRate;
            sleep(10);
            // deduct 25 basis points for mortgage each point
            for (int i = 0; i < numberOfPoints; i++) {
                mortgageInterestRate = tempInterestRate - 0.25;  // 1 point is equal to 25 basis points (0.25%)
                System.out.printf("%nin loop: %s", mortgageInterestRate);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%ndeducting 1 point (0.25%%), new interest rate: %s", mortgageInterestRate);
        return mortgageInterestRate;
    }

    /**
     *
     * @param creditScore
     * @return mortgageInterestRate
     */
    public synchronized double calculateMortgageInterestRate(int creditScore) {
        try {
            double tempInterestRate;
            tempInterestRate = mortgageInterestRate;
            sleep(10);

            if (creditScore > 680 && creditScore < 740) {
                System.out.printf("adding 25 basis points, interest rate: %s", mortgageInterestRate);
                mortgageInterestRate = tempInterestRate + 0.25;  // 1 point is equal to 25 basis points (0.25%)
            } else if (creditScore <= 680) {
                System.out.printf("adding 50 basis points, interest rate: %s", mortgageInterestRate);
                mortgageInterestRate = tempInterestRate + 0.50;
            } else {
                //score is greater than 740
                System.out.printf("interest rate: %s", mortgageInterestRate);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mortgageInterestRate;
    }

    public synchronized void getMortgageInterestRate() {
        System.out.printf("the mortgage interest rate is: %s", mortgageInterestRate);
    }

    public void run() {
        try {
//            do {
//                for (int i = 0; i < count; i++) {
//                    calculateMortgagePoints(3);
//                }
//                sleep(20);
//                count--;
//            } while (count > 0);

            sleep(20);
            calculateMortgagePoints(3);
        } catch (InterruptedException e) {
        }
    }

}
