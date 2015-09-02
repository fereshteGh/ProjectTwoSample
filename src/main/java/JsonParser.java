import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;

public class JsonParser {
    public Server Parse() {
        try {
            ArrayList<Deposit> deposit =new ArrayList();
            String filePath = "src/main/resources/core.json";
            FileReader fileReader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) obj;
            int port = ((Long) jsonObject.get("port")).intValue();
            System.out.println("Port : " + port);
            JSONArray deposits = (JSONArray) jsonObject.get("deposits");
            Iterator j = deposits.iterator();
            while (j.hasNext()) {
                JSONObject jsonObject1 = (JSONObject) j.next();
                String customerName = (String) jsonObject1.get("customer");
                String customerNumber = (String) jsonObject1.get("id");
                BigDecimal initialBalance = new BigDecimal( jsonObject1.get("initialBalance").toString().replace(",",""));
                BigDecimal upperBound =  new BigDecimal( jsonObject1.get("upperBound").toString().replace(",",""));
                Deposit depos = new Deposit(customerName, customerNumber, initialBalance, upperBound);
                deposit.add(depos);
            }

           String outLog = (String) jsonObject.get("outLog");
            return new Server(port, deposit, outLog);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
