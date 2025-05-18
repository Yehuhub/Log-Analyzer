package Factories;

import Analyzer.CommonSourcesAnalyzer;
import Analyzer.CountLevelsAnalyzer;
import Analyzer.DetectAnomaliesAnalyzer;
import DataManagement.AnomalyStorage;
import DataManagement.CommonSourcesCounterStorage;
import DataManagement.CounterStorage;
import DataManagement.DataStorage;
import Services.AnalysisMethods;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory class for creating different storage types
 */
public class StorageFactory {
    private static StorageFactory instance;
    private static final Map<AnalysisMethods, DataStorageCreator> creatorMap = new HashMap<>();

    //register methods
    static{
        creatorMap.put(AnalysisMethods.COUNT_LEVELS, CounterStorage::new);
        creatorMap.put(AnalysisMethods.DETECT_ANOMALIES, AnomalyStorage::new);
        creatorMap.put(AnalysisMethods.FIND_COMMON_SOURCE, CommonSourcesCounterStorage::new);
    }

    /**
     * private ctor
     */
    private StorageFactory(){}

    /**
     * get the only instance of the class
     * @return StorageFactory instance
     */
    public static StorageFactory getInstance(){
        if (instance == null){
            instance = new StorageFactory();
        }
        return instance;
    }

    /**
     * main Data Storage creation function
     * @param method - which method's storage is needed
     * @return - DataStorage container
     */
    public DataStorage createDataStorage(AnalysisMethods method){
        DataStorage container = creatorMap.get(method).create();
        if(container == null){
            throw new IllegalArgumentException("Analysis method not supported: " + method);
        }
        return container;
    }

    /**
     * interface for creating methods required for factory
     */
    private interface DataStorageCreator{
        DataStorage create();
    }

}
