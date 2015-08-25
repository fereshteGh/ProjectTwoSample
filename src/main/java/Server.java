import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    private Socket connection;
    private int counter = 1;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public void runServer() throws IOException {
        server = new ServerSocket(12345);
        do {
            waitForConnection();
            getStream();
            processConnection();
            closeConnection();
            counter++;
        } while (true);
    }

    private void waitForConnection() throws IOException {
        System.out.println("waiting for connection");
        connection = server.accept();
        System.out.println("Connection " + counter + " received from " + connection.getInetAddress().getHostName());
    }

    private void getStream() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
    }

    private void processConnection() {
        System.out.println("Connection successful");
        //should read the file sent by client and process it
    }

    private void closeConnection() throws IOException {
        System.out.println("Connection Terminated");
        output.close();
        input.close();
        connection.close();

    }
}
