package Terminal;

import ExceptionPack.TransactionException;
import java.math.BigDecimal;

public class Transaction {
    private int transactionId;
    public TransactionType Ttype;
    private BigDecimal amount;
    private String deposit;


    private enum TransactionType {
        deposit, withdraw
    }

    public Transaction(int transactionId, String type, BigDecimal amount, String deposit) throws TransactionException {
        this.transactionId = transactionId;
        this.Ttype = transactionType(type);
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
}
