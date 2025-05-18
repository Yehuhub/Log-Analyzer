package DataManagement;

import org.json.JSONObject;

import java.util.*;

/**
 * Class that extends CounterStorage to provide a specific implementation for
 * formattedData method.
 * This class is used to count the occurrences of different sources
 * and provide a formatted JSON object with the results.
 */
public class CommonSourcesCounterStorage extends CounterStorage{

    /**
     * formattedData is a method that formats the data in the map into a JSON object.
     * @return a JSON object containing the formatted data
     */
    @Override
    synchronized public JSONObject formattedData(){
        JSONObject result = new JSONObject();

        if (counterMap.isEmpty()) {
            result.put("sources", new ArrayList<>());
            result.put("source_counts", new ArrayList<>());
            result.put("most_common_source", JSONObject.NULL);
            result.put("most_common_source_count", 0);
            result.put("least_common_source", JSONObject.NULL);
            result.put("least_common_source_count", 0);

            JSONObject wrapper = new JSONObject();
            wrapper.put("data", result);
            return wrapper;
        }

        List<String> sources = new ArrayList<>();
        List<Integer> sourceCounts = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : counterMap.entrySet()) {
            sources.add(entry.getKey());
            sourceCounts.add(entry.getValue());
        }


        result.put("sources", sources.toString());
        result.put("source_counts", sourceCounts.toString());

        Map.Entry<String, Integer> maxEntry = Collections.max(counterMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue));
        Map.Entry<String, Integer> minEntry = Collections.min(counterMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue));
        result.put("most_common_source_count", maxEntry.getValue());
        result.put("most_common_source", maxEntry.getKey());
        result.put("least_common_source_count", minEntry.getValue());
        result.put("least_common_source", minEntry.getKey());


        JSONObject wrapper = new JSONObject();
        wrapper.put("data", result);
        return wrapper;

    }
}
