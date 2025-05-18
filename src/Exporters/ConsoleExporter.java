package Exporters;

import Services.PropertiesManager;
import org.json.JSONObject;

/**
 * Exporter made for testing both about strategy pattern and for debugging
 */
public class ConsoleExporter implements Exporter{

    /**
     * Exports the data to the console
     * @param data the data to export
     */
    @Override
    public void export(JSONObject data) {
        String fileName = PropertiesManager.getInstance().getProperty("output.file");
        System.out.println(data.toString());
    }
}
