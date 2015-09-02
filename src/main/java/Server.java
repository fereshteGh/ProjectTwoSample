import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket server;
    private Socket socket;
    private int port;
    public  ArrayList<Deposit> deposits;
    private String outLog;
    private static final Boolean monitor = true;

    public Server(int port, ArrayList<Deposit> deposit, String outLog) {
        this.port = port;
        this.deposits = deposit;
        this.outLog = outLog;
    }

    private void waitForConnection() throws IOException {
        int counter = 0;
        System.out.println("waiting for connection");
        writeToFile("waiting for connection");
        socket = server.accept();
        System.out.println("Connection " + counter + " received from " + socket.getInetAddress().getHostName());
    }

    public void writeToFile(String log) {
        synchronized (monitor) {
            try {
                RandomAccessFile fileWrite = new RandomAccessFile(outLog, "rw");
                fileWrite.writeBytes("\n");
                fileWrite.writeBytes(log);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeConnection() throws IOException {
        System.out.println("Connection Terminated");
        writeToFile("Connection Terminated");
    }

    public void runServer() throws IOException, ClassNotFoundException, DepositNotFoundException {
        server = new ServerSocket(port);
        while (true) {
            waitForConnection();
            Thread terminalHandler = new TerminalHandler(socket, this);
            terminalHandler.start();
            closeConnection();
        }
    }
}
