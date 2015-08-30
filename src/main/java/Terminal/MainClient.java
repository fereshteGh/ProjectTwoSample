package Terminal;

import java.io.IOException;

public class MainClient {
    public static void main(String args[]) throws IOException {
        XmlParser xmlParser = new XmlParser();
        Terminal terminal = xmlParser.parse();
        terminal.run();
    }
}
