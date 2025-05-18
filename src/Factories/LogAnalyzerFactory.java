package Factories;

import Analyzer.*;
import Services.AnalysisMethods;
import Services.PropertiesManager;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton factory class for creating analyzers, chaining the required analyzers together
 */
public class LogAnalyzerFactory {
    private static LogAnalyzerFactory instance;
    private static final Map<AnalysisMethods, LogAnalyzerCreator> creatorMap = new HashMap<>();

    //register methods
    static {
        creatorMap.put(AnalysisMethods.COUNT_LEVELS, CountLevelsAnalyzer::new);
        creatorMap.put(AnalysisMethods.DETECT_ANOMALIES, DetectAnomaliesAnalyzer::new);
        creatorMap.put(AnalysisMethods.FIND_COMMON_SOURCE, CommonSourcesAnalyzer::new);
    }

    /**
     * private constructor for singleton
     */
    private LogAnalyzerFactory(){}

    /**
     * get instance of the factory
     * @return - instance of the factory
     */
    public static LogAnalyzerFactory getInstance(){
        if(instance == null){
            instance = new LogAnalyzerFactory();
        }
        return instance;
    }

    /**
     * main analyzer building method
     * @param fileName - which file called this function
     * @return - chained analysis methods analyzer
     */
    public LogAnalyzer createLogAnalyzer(String fileName){
        LogAnalyzer analyzer = new BaseLogAnalyzer(fileName);
        AnalysisMethods[] methods = PropertiesManager.getInstance().getAnalysisMethods();

        for (AnalysisMethods method : methods){
            analyzer = creatorMap.get(method).create(analyzer);
            if (analyzer == null){
                throw new IllegalArgumentException("Analysis method not supported: " + method);
            }
        }

        return analyzer;
    }

    /**
     * interface for creating methods required for factory
     */
    private interface LogAnalyzerCreator{
        LogAnalyzer create(LogAnalyzer analyzer);
    }

}
