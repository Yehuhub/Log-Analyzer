package Exporters;

import org.json.JSONObject;

/**
 * Exporter interface for exporting data
 * This is the interface for the strategy pattern
 * The concrete classes will implement this interface
 */
public interface Exporter {
    /**
     * Exports the data
     * @param data the data to export
     */
    void export(JSONObject data);
}
