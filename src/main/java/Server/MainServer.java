package Server;




import java.io.IOException;

public class MainServer {
    public static void main(String args[]) throws IOException {
        JsonParser jsonParser = new JsonParser();
        jsonParser.Parse();
        Terminal.XmlParser xmlParser = new Terminal.XmlParser();
        while (true) {
            Server server = new Server();
            server.runServer();
            Thread thread = new Thread(xmlParser.parse());
            thread.start();

        }
    }
}