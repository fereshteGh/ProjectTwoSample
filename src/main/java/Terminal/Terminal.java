package Terminal;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Terminal implements Runnable {
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private Socket terminal;
    private int port;
    private String ip;
    private String outLogPath;
    private String terminalId;
    private String terminalType;
    private List<Transaction> transactions;

    public Terminal(int port, String ip, String outLogPath, String terminalId, String terminalType, List<Transaction> trans) {
        this.port = port;
        this.ip = ip;
        this.outLogPath = outLogPath;
        this.terminalId = terminalId;
        this.terminalType = terminalType;
        this.transactions = trans;
    }

    public void connectToServer() throws IOException {

        System.out.println("Connection attempting!");
        //InetAddress.getByName(ip)
        terminal = new Socket("localhost", port);
        System.out.println("Connected to " + terminal.getInetAddress().getHostName());
    }

    public void getStream() throws IOException {

        try {
            output = new ObjectOutputStream(terminal.getOutputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(terminal.getOutputStream());
            input = new ObjectInputStream(terminal.getInputStream());
            dataOutputStream.writeInt(transactions.size());
            if (transactions != null && transactions.size() > 0) {
                for (int i = 0; i < transactions.size(); i++) {
                    System.out.println(transactions.get(i));
                    output.writeObject(transactions.get(i));
                    output.flush();
                    System.out.println("1111111");
//                    String message = input.readUTF();
                    System.out.println("22222");
                   // System.out.println("Server message : " + message);
                }
                System.out.println("111111");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void closeConnection() throws IOException {
        System.out.println("Closing connection");
        output.close();
        input.close();
        terminal.close();
    }

    @Override
    public void run() {
        try {
            connectToServer();
            getStream();
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
