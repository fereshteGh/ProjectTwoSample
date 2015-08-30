package Terminal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Terminal implements Runnable {
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private Socket terminal;
    private int port;
    private String ip;
    private String outLogPath;
    private String terminalId;
    private String terminalType;
    private Transaction transactions;

    public Terminal(int port, String ip, String outLogPath, String terminalId, String terminalType,Transaction trans) {
        this.port = port;
        this.ip = ip;
        this.outLogPath = outLogPath;
        this.terminalId = terminalId;
        this.terminalType = terminalType;
        this.transactions = trans;
    }

    public void connectToServer() throws IOException {

        System.out.println("Connection attempting!");
        terminal = new Socket(InetAddress.getByName(ip), port);
        System.out.println("Connected to " + terminal.getInetAddress().getHostName());
    }

    public void getStream() throws IOException {

        output = new ObjectOutputStream(terminal.getOutputStream());
        output.flush();
        DataOutputStream dataOutput = new DataOutputStream(output);
        //dataOutput.writeBytes(list.toString());
        input = new ObjectInputStream(terminal.getInputStream());
        String filePath = "src/main/resources/response.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        if (xmlFile.exists()) {
            Document doc = db.parse(xmlFile);
Element element = doc.getDocumentElement();
        } }catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void processConnection() {

    }

    public void closeConnection() throws IOException {
        System.out.println("Closing connection");
        output.close();
        input.close();
        terminal.close();
    }

    @Override
    public void run() {
        try {
            connectToServer();
            getStream();
            processConnection();
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
