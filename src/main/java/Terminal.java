import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class Terminal extends Thread {
    private ObjectOutputStream output;

    private Socket socket;
    private int port;
    private String ip;
    private String outLogPath;
    private String terminalId;
    private String terminalType;
    private List<Transaction> transactions;
    private static final Boolean monitor = true;

    public Terminal(int port, String ip, String outLogPath, String terminalId, String terminalType, List<Transaction> trans) {
        this.port = port;
        this.ip = ip;
        this.outLogPath = outLogPath;
        this.terminalId = terminalId;
        this.terminalType = terminalType;
        this.transactions = trans;
    }

    public void connectToServer() throws IOException {
        System.out.println("Connection attempting!");
        socket = new Socket("localhost", port);
        System.out.println("Connected to " + socket.getInetAddress().getHostName());
    }

    public void getStream() throws IOException {

        try {

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new ObjectOutputStream(socket.getOutputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String message;
            //get transaction size
            dataOutputStream.writeInt(transactions.size());
            //get transaction list and send to server
            if (transactions != null && transactions.size() > 0) {
                for (int i = 0; i < transactions.size(); i++) {
                    System.out.println(transactions.get(i));
                    output.writeObject(transactions.get(i));
                    message = input.readLine();
                    System.out.println("Server message : " + message);
                    writeToXmlFile(message, transactions.get(i));
                    //write to file
                    writeToFile(terminalId);
                    writeToFile(terminalType);
                    writeToFile(ip);
                    writeToFile(String.valueOf(port));
                    writeToFile(outLogPath);
                    writeToFile(transactions.get(i).toString());
                    writeToFile(message);

                }
            }
            dataOutputStream.writeUTF("end");
            dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String log) {
        synchronized (monitor) {
            try {
                RandomAccessFile fileWrite = new RandomAccessFile(outLogPath, "rw");
                fileWrite.writeBytes("\n");
                fileWrite.writeBytes(log);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToXmlFile(String message, Transaction transaction) {
        synchronized (monitor) {
            try {
                File xmlFile = new File("src/main/resources/response.xml");
                Document document;
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                //append xml file
                if (xmlFile.exists()) {
                    document = db.parse(xmlFile);
                    document.getDocumentElement().normalize();
                    NodeList transactionNodeList = document.getElementsByTagName("transactions");
                    Element element;
                    for (int i = 0; i < transactionNodeList.getLength(); i++) {
                        element = (Element) transactionNodeList.item(i);
                        Element transactionElement = document.createElement("transaction");
                        Attr attr1 = document.createAttribute("id");
                        attr1.setValue(String.valueOf(transaction.getTransactionId()));
                        Attr attr2 = document.createAttribute("deposit");
                        attr2.setValue(transaction.getDeposit());
                        transactionElement.setAttributeNode(attr1);
                        transactionElement.setAttributeNode(attr2);
                        element.appendChild(transactionElement);
                        if (message.contains("done")) {
                            transactionElement.appendChild(document.createTextNode("done"));
                        } else {
                            transactionElement.appendChild(document.createTextNode("fail"));
                        }
                    }
                    document.getDocumentElement().normalize();
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource domSource = new DOMSource(document);
                    StreamResult streamResult = new StreamResult(xmlFile);
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.transform(domSource, streamResult);
                }
                //create xml file
                else {
                    document = db.newDocument();
                    Element terminal = document.createElement("terminal");
                    terminal.setAttribute("id", terminalId);
                    document.appendChild(terminal);
                    Element transactionsElement = document.createElement("transactions");
                    terminal.appendChild(transactionsElement);
                    Element transactionElement = document.createElement("transaction");
                    Attr attr1 = document.createAttribute("id");
                    attr1.setValue(String.valueOf(transaction.getTransactionId()));
                    Attr attr2 = document.createAttribute("deposit");
                    attr2.setValue(transaction.getDeposit());
                    transactionElement.setAttributeNode(attr1);
                    transactionElement.setAttributeNode(attr2);
                    transactionsElement.appendChild(transactionElement);
                    if (message.contains("done")) {
                        transactionElement.appendChild(document.createTextNode("done"));
                    } else {
                        transactionElement.appendChild(document.createTextNode("fail"));
                    }
                }
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(xmlFile);
                transformer.transform(domSource, streamResult);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() throws IOException {

        System.out.println("Closing connection");
        output.close();
        socket.close();
    }

    @Override
    public void run() {
        try {
            connectToServer();
            getStream();
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
