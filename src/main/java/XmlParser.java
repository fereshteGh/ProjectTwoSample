import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {

    public Terminal parse(String filePath) {

        try {
        // Read from file
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
            db = dbf.newDocumentBuilder();
            if (xmlFile.exists()) {
               ArrayList<Transaction> transactions = new ArrayList();

                Document doc = db.parse(xmlFile);

                Element terminal = doc.getDocumentElement();
                String terminalId = terminal.getAttribute("id");
                String  terminalType = terminal.getAttribute("type");

                Element server = (Element) terminal.getElementsByTagName("server").item(0);
                String  serverIp = server.getAttribute("ip");
                int  port = Integer.parseInt(server.getAttribute("port"));

                Element outLog = (Element) terminal.getElementsByTagName("outLog").item(0);
                String outLogPath = outLog.getAttribute("path");

                Element transactions1 = (Element) terminal.getElementsByTagName("transactions").item(0);
                NodeList nodeList3 = transactions1.getElementsByTagName("transaction");
                if (nodeList3 != null && nodeList3.getLength() > 0) {
                    for (int k = 0; k < nodeList3.getLength(); k++) {
                        Node node3 = nodeList3.item(k);
                        Element element3 = (Element) node3;
                        int transactionId = Integer.parseInt(element3.getAttribute("id"));
                        String depositType = element3.getAttribute("type");
                        BigDecimal amount = BigDecimal.valueOf(Long.parseLong(element3.getAttribute("amount")));
                        String deposit =element3.getAttribute("deposit");
                        Transaction  transaction = new Transaction(transactionId,depositType,amount,deposit);
                        transactions.add(transaction);
                    }
                }
                return new Terminal(port, serverIp, outLogPath, terminalId, terminalType,transactions);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


