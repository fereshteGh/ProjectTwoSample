
import java.io.IOException;

public class MainServer {
    public static void main(String args[]) throws IOException, ClassNotFoundException, DepositNotFoundException {
        JsonParser jsonParser = new JsonParser();
        Server server = jsonParser.Parse();
        server.runServer();

    }
}