package Exporters;

import Services.PropertiesManager;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Exporter made for exporting data to a file
 */
public class FileExporter implements Exporter{

    /**
     * Exports the data to a file
     * @param data the data to export
     */
    @Override
    public void export(JSONObject data) {
        String fileName = PropertiesManager.getInstance().getProperty("output.file");
        try(BufferedWriter br = new BufferedWriter(new FileWriter(fileName))){
            br.write(data.toString());
        } catch (IOException ie){
            System.out.println("error saving report");
        } catch (Exception e) {
            throw new RuntimeException(e); //just for now
        }
    }
}
