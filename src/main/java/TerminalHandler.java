import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;

public class TerminalHandler extends Thread {

    private Socket socket;
    Server server;

    public TerminalHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }
    //handle terminal transactions
    public void executeTransaction(){
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            //read transaction size
            int transactionSize = dataInputStream.readInt();
            System.out.println("transaction size : " + transactionSize);
            for (int i = 0; i < transactionSize; i++) {
                System.out.println("i : " + i);
                Transaction transaction;
                transaction = (Transaction) input.readObject();
                System.out.println("transaction : " + transaction);
                String type = String.valueOf(transaction.getTransactionType());
                String depositId = String.valueOf(transaction.getDeposit());
                BigDecimal amount = transaction.getAmount();
                //determine transaction type and execute transaction
                if (type.compareTo("deposit") == 0) {
                    for (int j = 0; j < server.deposits.size(); j++) {
                        if (depositId.compareTo(server.deposits.get(j).getCustomerNumber()) == 0) {
                            try {
                                System.out.println("deposit calculation");
                                System.out.println("Deposit amount : " +server. deposits.get(j).deposit(amount));
                                printWriter.println("Transaction deposit for customer " + depositId + " is done");
                                server.writeToFile("Transaction " + String.valueOf(i) + "for customer " + depositId + " is done! ");
                            } catch (ExcessBoundException e) {
                                printWriter.println("Transaction deposit for customer " + depositId + " deposit failed!");
                                server.writeToFile("Transaction " + String.valueOf(i) + "for customer " + depositId + " is failed! ");
                            }
                        }
                    }
                } else if (type.compareTo("withdraw") == 0) {
                    for (int j = 0; j < server.deposits.size(); j++) {

                        if (depositId.compareTo(server.deposits.get(j).getCustomerNumber()) == 0)
                            try {
                                System.out.println("withdraw calculation");
                                System.out.println("Withdraw amount : " + server.deposits.get(j).withdraw(amount));
                                printWriter.println("Transaction withdraw for customer " + depositId + " is done");
                                server.writeToFile("Transaction " + String.valueOf(i) + "for customer " + depositId + " is done! ");

                            } catch (ExcessBoundException e) {
                                printWriter.println("Transaction withdraw failed!");
                                server.writeToFile("Transaction " + String.valueOf(i) + "for customer " + depositId + " is failed! ");
                            }
                    }
                }
            }
            System.out.println("end " + Thread.currentThread().getName());
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void run() {
        executeTransaction();
    }
}
