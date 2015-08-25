import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket client;
    public void runClient() throws IOException {
        connectToServer();
        getStream();
        processConnection();
        closeConnection();
    }
    public void connectToServer() throws IOException {
        System.out.println("Connection attempting!");
        client = new Socket(InetAddress.getByName("localhost"),12345);
        System.out.println("Connected to " + client.getInetAddress().getHostName());
    }
    public void getStream() throws IOException {
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();
        input = new ObjectInputStream(client.getInputStream());
    }
    public void processConnection(){

    }
    public void closeConnection() throws IOException {
        System.out.println("Closing connection");
        output.close();
        input.close();
        client.close();
    }
}
