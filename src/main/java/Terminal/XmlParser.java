package Terminal;

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

    public Terminal parse() {

        Transaction transaction = null;
        String outLogPath = null;
        int port = 0;
        String serverIp = null;
        int transactionId;
        String depositType;
        BigDecimal amount;
        String deposit;
        String terminalId = null;
        String terminalType = null;

        // Read from file
        String filePath = "src/main/resources/terminal.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;



        try {
            db = dbf.newDocumentBuilder();
            if (xmlFile.exists()) {
                Document doc = db.parse(xmlFile);

                Element terminal = doc.getDocumentElement();
                 terminalId = terminal.getAttribute("id");
                 terminalType = terminal.getAttribute("type");

                Element server = (Element) terminal.getElementsByTagName("server").item(0);
                serverIp = server.getAttribute("ip");
                port = Integer.parseInt(server.getAttribute("port"));

                Element outLog = (Element) terminal.getElementsByTagName("outLog");
                outLogPath = outLog.getAttribute("path");

                Element transactions = (Element) terminal.getElementsByTagName("transactions");
                NodeList nodeList3 = transactions.getElementsByTagName("transaction");
                if (nodeList3 != null && nodeList3.getLength() > 0) {
                    for (int k = 0; k < nodeList3.getLength(); k++) {
                        Node node3 = nodeList3.item(k);
                        Element element3 = (Element) node3;
                        transactionId = Integer.parseInt(element3.getAttribute("id"));

                        depositType = element3.getAttribute("type");

                        amount = BigDecimal.valueOf(Long.parseLong(element3.getAttribute("amount")));

                        deposit =element3.getAttribute("deposit");

                         transaction = new Transaction(transactionId,depositType,amount,deposit);
                    }
                }
            }
            return new Terminal(port, serverIp, outLogPath, terminalId, terminalType,transaction);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}


