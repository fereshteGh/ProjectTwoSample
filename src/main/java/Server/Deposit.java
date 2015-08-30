package Server;

import ExceptionPack.ExcessBoundException;
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
        System.out.println("987");
        System.out.println("amount : "+amount);
        System.out.println("initial : "+initialBalance);
        BigDecimal sum = amount.add(initialBalance);
        System.out.println("((((++++"+sum);
        if (sum.compareTo(upperBound) == -1) {
            initialBalance = sum;
            System.out.println("~~~~~~~~~"+sum);
            return sum;
        }
        throw new ExcessBoundException();

    }
    public BigDecimal withdraw(BigDecimal amount) throws ExcessBoundException {
        BigDecimal subtraction = initialBalance.subtract(amount);
        if (subtraction.compareTo(BigDecimal.ZERO) >= 1) {
            initialBalance = subtraction;
            return subtraction;
        }
        throw new ExcessBoundException();
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
