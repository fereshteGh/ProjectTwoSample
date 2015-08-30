package Server;

import ExceptionPack.ExcessBoundException;

import java.math.BigDecimal;

public class Deposit {

    private BigDecimal initialBalance;
    private BigDecimal upperBound;


    public BigDecimal deposit(BigDecimal amount) throws ExcessBoundException {
        BigDecimal sum = amount.add(initialBalance);
        if (sum.compareTo(upperBound) == -1)
            return sum;
        throw new ExcessBoundException();
    }

    public BigDecimal withdraw(BigDecimal amount) throws ExcessBoundException {
        BigDecimal subtraction = initialBalance.subtract(amount);
        if (subtraction.compareTo(BigDecimal.ZERO) >= 1)
            return subtraction;
        throw new ExcessBoundException();
    }
}
