package Services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


/**
 * singleton class for managing the configuration for the application.
 */
public class PropertiesManager {
    private static PropertiesManager instance;
    private final Properties prop = new Properties();

    /**
     * constructor, sets the default values for configuration
     */
    private PropertiesManager() {

        //set default values for the properties
        prop.setProperty("log.directory", "logs");
        prop.setProperty("thread.pool.size", "4");
        prop.setProperty("output.file", "output.json");
        prop.setProperty("log.analysis", "COUNT_LEVELS");
        prop.setProperty("log.analysis.anomalies.levels", "ERROR");
        prop.setProperty("log.analysis.anomalies.window", "30");
        prop.setProperty("log.analysis.anomalies.threshold", "2");
    }

    /**
     * function to get the instance of the class
     * @return - PropertiesManager instance
     */
    public static PropertiesManager getInstance() {
        if(instance == null){
            instance = new PropertiesManager();
        }
        return instance;
    }
    /**
     * loads a config file to the properties
     * @param filepath - path to config file
     */
    public void loadProperties(String filepath) {
        try(FileInputStream fis = new FileInputStream(filepath)){
            prop.load(fis);
            System.out.println("config loaded from file: " + filepath);
        }
        catch (IOException e){
            System.err.println("Error loading config file: " + filepath);

        }
    }

    /**
     * function to get the property value
     * @param propertyName - name of the property
     * @return - value of the property
     */
    public String getProperty(String propertyName){
        return prop.getProperty(propertyName);
    }

    /**
     * function to get an integer property value
     * @param propertyName - name of the property
     * @param defaultValue - default value if the property is not found
     * @return - value of the property
     */
    public int getIntProperty(String propertyName, int defaultValue) {
        String value = getProperty(propertyName);

        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.err.println("Warning: Invalid " + propertyName + " value '" + value + "'. Using default: " + defaultValue);
            }
        }
        return defaultValue;
    }

    /**
     * function to get an array of AnalysisMethods from the properties
     * @return - array of AnalysisMethods
     */
    public AnalysisMethods[] getAnalysisMethods(){
        String [] methodsStrings = prop.getProperty("log.analysis").split(",");
        ArrayList<AnalysisMethods> methods =  new ArrayList<>();

        for (String method : methodsStrings){
            try{
            methods.add(AnalysisMethods.parseMethod(method.trim()));
            } catch (IllegalArgumentException e) {
                System.out.println("Analysis method " + method + " not supported: " + e.getMessage());
            }
        }
        
        return methods.toArray(new AnalysisMethods[0]);
    }

}
