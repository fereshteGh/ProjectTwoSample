import java.io.IOException;

public class TestTerminal {


    public static void main(String args[]) throws IOException {
        XmlParser xmlParser = new XmlParser();
        Terminal terminal1 = xmlParser.parse("src/main/resources/terminal1.xml");
        Terminal terminal2 = xmlParser.parse("src/main/resources/terminal2.xml");
        terminal1.start();
        terminal2.start();



    }
}
