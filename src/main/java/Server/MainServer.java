package Server;

import ExceptionPack.DepositNotFoundException;

import java.io.IOException;

public class MainServer {
    public static void main(String args[]) throws IOException, ClassNotFoundException, DepositNotFoundException {
        JsonParser jsonParser = new JsonParser();

        Terminal.XmlParser xmlParser = new Terminal.XmlParser();
        while (true) {
            Server server = jsonParser.Parse();
            server.runServer();
            Thread thread = new Thread(xmlParser.parse());
            thread.start();

        }
    }
}