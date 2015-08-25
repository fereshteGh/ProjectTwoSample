import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {

    public void parse() {

        String filePath = "/src/resources/terminal.xml";
        // Read from file
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            if (xmlFile.exists()) {
                Document doc = db.parse(xmlFile);
                Element element = doc.getDocumentElement();
                Element el;
                NodeList customerNumberList = element.getElementsByTagName("deposit");
                if (customerNumberList != null && customerNumberList.getLength() > 0) {
                    for (int i = 0; i < customerNumberList.getLength(); i++) {
                        Node node = customerNumberList.item(i);
                        el = (Element) node;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


