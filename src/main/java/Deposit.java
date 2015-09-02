
import java.math.BigDecimal;

public class Deposit {
    private String customerName;
    private String customerNumber;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;

    public String getCustomerNumber() {
        return customerNumber;
    }

    public Deposit(String customerName, String customerNumber, BigDecimal initialBalance, BigDecimal upperBound) {
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        this.initialBalance = initialBalance;
        this.upperBound = upperBound;
    }

    public BigDecimal deposit(BigDecimal amount) throws ExcessBoundException {
           synchronized (this) {
        BigDecimal potentialRemainingAmount = amount.add(initialBalance);
        try {
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + "  try to deposit");
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("amount : " + amount);
        System.out.println("initial : " + initialBalance);

        if (potentialRemainingAmount.compareTo(upperBound) == -1) {
            initialBalance = potentialRemainingAmount;
            System.out.println(Thread.currentThread().getName() + " is completed with deposit");
            return potentialRemainingAmount;
        }
        System.out.println(Thread.currentThread().getName() + " potentialRemaining amount more than upperBound amount");
        throw new ExcessBoundException();
         }
    }

    public BigDecimal withdraw(BigDecimal amount) throws ExcessBoundException {
         synchronized (this) {
        BigDecimal potentialRemainingAmount = initialBalance.subtract(amount);
        try {
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " try to withdraw");
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("amount : " + amount);
        System.out.println("initial : " + initialBalance);

        if (potentialRemainingAmount.compareTo(BigDecimal.ZERO) != -1) {
            initialBalance = potentialRemainingAmount;
            System.out.println(Thread.currentThread().getName() + " completed with withdrawn");
            System.out.println("potentialRemainingAmount = " + potentialRemainingAmount);
            return potentialRemainingAmount;
        } else {
            System.out.printf(Thread.currentThread().getName() + " entered amount more than balance");
            throw new ExcessBoundException();
        }
         }
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "customerName='" + customerName + '\'' +
                ", customerNumber='" + customerNumber + '\'' +
                ", initialBalance=" + initialBalance +
                ", upperBound=" + upperBound +
                '}';
    }
}
