package DataManagement;

import org.json.JSONObject;

/**
 * Interface for DataStorage
 * This interface defines the methods that any data storage implementation must provide.
 */
public interface DataStorage {
    void insertData(String data);
    JSONObject formattedData();
}

