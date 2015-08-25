import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;

public class JsonParser {
    public void Parse() {
        String filePath = "/src/resources/core.json";
        // ArrayList deposit= new ArrayList();
        try {
            FileReader fileReader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            int port = (Integer) jsonObject.get("port");
            System.out.println("Port : " + port);
            JSONArray deposit = (JSONArray) jsonObject.get("deposit");
            for (int i = 0; i < deposit.size(); i++)
                System.out.println("Deposit List : " + deposit.get(i));

            //take each value separately
            Iterator j = deposit.iterator();
            while (j.hasNext()) {
                JSONObject jsonObject1 = (JSONObject) j.next();
                System.out.println(" Deposit List with " + "customer " + jsonObject1.get("customer") + "and customerNumber : " + jsonObject1.get("customerNumber") + "and initialBalance" + jsonObject1.get("initialBalance") + "and upperBound " + jsonObject1.get("upperBound"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
