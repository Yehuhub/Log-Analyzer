package DataManagement;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * CounterStorage is a class that implements the DataStorage interface.
 * It is used to store data in a map where the key is a string and the value is an integer.
 * The integer value represents the number of times the string has been inserted into the map.
 */
public class CounterStorage implements DataStorage{
    protected final Map<String, Integer> counterMap = new HashMap<>();

    /**
     * insertData is a method that takes a string as input and increments the value associated with that string in the map.
     * @param data the string to be inserted into the map
     */
    @Override
    synchronized public void insertData(String data) {
        counterMap.put(data, counterMap.getOrDefault(data, 0) + 1);
    }

    /**
     * formattedData is a method that formats the data in the map into a JSON object.
     * @return a JSON object containing the formatted data
     */
    synchronized public JSONObject formattedData (){

        JSONObject result = new JSONObject(counterMap);

        JSONObject wrapper = new JSONObject();
        wrapper.put("data", result);
        return wrapper;
    }
}