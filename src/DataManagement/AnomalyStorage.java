package DataManagement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * AnomalyStorage is a class that implements the DataStorage interface.
 */
public class AnomalyStorage implements DataStorage{
    protected final Map<String, ArrayList<String>> anomalyMap = new HashMap<>();

    /**
     * insertData is a method that takes a string as input and splits it into two parts.
     * The first part is used as a key in a map, and the second part is added to a list associated with that key.
     * @param data the string to be inserted into the map
     */
    @Override
    synchronized public void insertData(String data){
        String [] values = data.split(",");
        if(values.length != 2){
            System.out.println("unexpected data");
            return;
        }

        ArrayList<String> list = anomalyMap.getOrDefault(values[0], new ArrayList<>());

        if(!list.contains(values[1])){
            list.add(values[1]);
            anomalyMap.put(values[0], list);
        }
    }

    /**
     * formattedData is a method that formats the data in the map into a JSON object.
     * @return a JSON object containing the formatted data
     */
    @Override
    synchronized public JSONObject formattedData(){
        JSONArray result = new JSONArray();
        JSONObject inner = new JSONObject();

        for(Map.Entry<String, ArrayList<String>> entry : anomalyMap.entrySet()){
            JSONObject file = new JSONObject();
            JSONArray anomalies = new JSONArray(entry.getValue());
            file.put("anomalies", anomalies);
            file.put("anomalies_count", entry.getValue().size());
            inner.put(entry.getKey(), file);
        }

        result.put(inner);
        JSONObject wrapper = new JSONObject();
        wrapper.put("data", result);
        return wrapper;
    }

}
