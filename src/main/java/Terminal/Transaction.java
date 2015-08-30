package Terminal;

import ExceptionPack.TransactionException;
import Server.Deposit;

import java.io.Serializable;
import java.math.BigDecimal;

public class Transaction implements Serializable {
    private int transactionId;
    public TransactionType tType;
    private BigDecimal amount;
    private String deposit;


    private enum TransactionType {
        deposit, withdraw
    }

    public Transaction(int transactionId, String type, BigDecimal amount, String deposit) throws TransactionException {
        this.transactionId = transactionId;
        this.tType = transactionType(type);
        this.amount = amount;
        this.deposit = deposit;
    }

    public TransactionType transactionType(String transType) throws TransactionException {
        if(transType.compareTo("deposit")==0)
            return TransactionType.deposit;
        else if(transType.compareTo("withdraw")==0)
            return TransactionType.withdraw;
        else
            throw new TransactionException();
    }

    public TransactionType getTransactionType(){
        return tType;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public String getDeposit() {
        return deposit;
    }

    @Override
    public String toString() {
        return " Transaction { " +
                " transactionId = " + transactionId +
                " , tType = " + tType +
                " , amount = " + amount +
                " , deposit = '" + deposit + '\'' +
                '}';
    }


}
