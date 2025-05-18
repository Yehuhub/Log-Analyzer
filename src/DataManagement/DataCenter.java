package DataManagement;

import Services.AnalysisMethods;
import Exporters.Exporter;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Singleton class that manages data storage and export
 */
public class DataCenter{
    private static DataCenter instance;
    private final Map<AnalysisMethods, DataStorage> mainData = new ConcurrentHashMap<>();
    private Exporter exporter;

    /**
     * private constructor to prevent instantiation
     */
    private DataCenter(){}

    /**
     * function that creates instance of DataCenter
     * @return instance of DataCenter
     */
    public static DataCenter getInstance(){
        if(instance == null){
            instance = new DataCenter();
        }
        return instance;
    }

    /**
     * function takes use of concurrent hashmap to to create datastorage and get it
     * used only to synchronize creation
     * @param method method to be used
     * @param creator function that creates DataStorage
     * @return DataStorage object
     */
    public DataStorage getOrCreateStorage(AnalysisMethods method, Supplier<DataStorage> creator){
        return mainData.computeIfAbsent(method, key -> creator.get());
    }


    /**
     * function organizes data and sends it out so it can be exported
     *
     * @return JSONObject with correctly parsed data
     */
    public JSONObject getData(){
        JSONObject dataAsJSON = new JSONObject();

        for(Map.Entry<AnalysisMethods, DataStorage> entry : mainData.entrySet()){
            dataAsJSON.put(entry.getKey().name(), entry.getValue().formattedData().get("data"));
        }

        return dataAsJSON;
    }

    /**
     * function sets the exporter
     * @param ex exporter
     */
    public void setExporter(Exporter ex){
        exporter = ex;
    }

    /**
     * function to export data
     */
    public void exportData() throws IllegalStateException{
        if(exporter == null){
            throw new IllegalStateException("No export strategy has been set");
        }
        exporter.export(getData());
    }

    /**
     * function to export data with exporter provided
     * @param ex exporter
     */
    public void exportDataWith(Exporter ex){
        ex.export(getData());
    }
}
