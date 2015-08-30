package Server;

import ExceptionPack.DepositNotFoundException;
import ExceptionPack.ExcessBoundException;
import Terminal.Transaction;

import java.io.*;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket server;
    private Socket connection;

    private int counter = 1;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private int port;
    private ArrayList<Deposit> deposits;
    private String outLog;

    public Server(int port, ArrayList<Deposit> deposit, String outLog) {
        this.port = port;
        this.deposits = deposit;
        this.outLog = outLog;
    }

    private void waitForConnection() throws IOException {
        System.out.println("waiting for connection");
        connection = server.accept();
        System.out.println("Connection " + counter + " received from " + connection.getInetAddress().getHostName());
    }

    private void getStream() throws IOException, ClassNotFoundException, DepositNotFoundException {
        output = new ObjectOutputStream(connection.getOutputStream());
        input = new ObjectInputStream(connection.getInputStream());
        DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        int transactionSize = dataInputStream.readInt();
        System.out.println("transaction size : "+transactionSize);
        System.out.println("44444");
        for (int i = 0; i < transactionSize; i++) {
            System.out.println("i : " + i);
            System.out.println("transSize: "+transactionSize);
            System.out.println("55555");
            Transaction transaction = (Transaction) input.readObject();
            System.out.println("transaction : "+transaction);
            System.out.println("66666");
            System.out.println("^^^^^^"+transaction.getAmount());
            System.out.println("#####"+transaction.toString());
//            if (input.readObject() != null)
//                System.out.println(input.readObject());
            String type = String.valueOf(transaction.getTransactionType());
            String transId = String.valueOf(transaction.getDeposit());
            BigDecimal amount = transaction.getAmount();
            System.out.println("88888");
            if (type.compareTo("deposit") == 0) {
                System.out.println("77777");
                for (int j = 0; j < deposits.size(); j++) {
//                    System.out.println("deposits :"+deposits.get(j));
//                    System.out.println("99999");
                    if (transId.compareTo(deposits.get(j).getCustomerNumber()) == 0){
                        System.out.println("141414");
                        try {
                            System.out.println("!!!!!");
                            System.out.println("@@@@"+deposits.get(j).deposit(amount));
                            dataOutputStream.writeUTF("Transaction deposit is done");
                        } catch (ExcessBoundException e) {
                            e.printStackTrace();
                        }}
                }
            } else if (type.compareTo("withdraw") == 0){
                for (int j = 0; j < deposits.size(); j++) {
                    System.out.println("16589");
                    if (transId.compareTo(deposits.get(j).getCustomerNumber()) == 0)
                        try {
                            deposits.get(j).withdraw(transaction.getAmount());
                            dataOutputStream.writeChars("Transaction withdraw is done");
                        } catch (ExcessBoundException e) {
                            e.printStackTrace();
                        }}
                }
            System.out.println("58585");
        }
    }

    private void closeConnection() throws IOException {
        System.out.println("Connection Terminated");
        input.close();
        output.close();
        connection.close();

    }

    public void runServer() throws IOException, ClassNotFoundException, DepositNotFoundException {

        server = new ServerSocket(port);
        while (true) {
            waitForConnection();
            getStream();
            closeConnection();
            counter++;
        }
    }
}
