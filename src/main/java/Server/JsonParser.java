package Server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;

public class JsonParser {
    public ArrayList Parse() {
        String filePath = "src/main/resources/core.json";
        Long  port;
         ArrayList list= new ArrayList();
        try {
            FileReader fileReader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) obj;
            port = (Long)jsonObject.get("port");
            list.add(port);
            System.out.println("Port : " + port);
            JSONArray deposit = (JSONArray) jsonObject.get("deposits");
            for (int i = 0; i < deposit.size(); i++)
                System.out.println("deposit List : " + deposit.get(i));

            //take each value separately
            Iterator j = deposit.iterator();
            while (j.hasNext()) {
                JSONObject jsonObject1 = (JSONObject) j.next();
                System.out.println(" deposit List with " + "customer " + jsonObject1.get(" customer") + " and customerNumber : " + jsonObject1.get("id") + " and initialBalance" + jsonObject1.get(" initialBalance") + " and upperBound " + jsonObject1.get("upperBound"));
                list.add(jsonObject1.get(" customer"));
                list.add(jsonObject1.get("id"));
                list.add(jsonObject1.get(" initialBalance"));
                list.add(jsonObject1.get("upperBound"));
            }
            return list;
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
