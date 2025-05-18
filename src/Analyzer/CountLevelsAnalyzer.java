package Analyzer;

import DataManagement.DataCenter;
import DataManagement.DataStorage;
import Factories.StorageFactory;
import Services.AnalysisMethods;
import Services.LogEntry;

import java.util.ArrayList;

/**
 * A log analyzer for counting levels,
 * have its DataStorage container as a member to avoid repeated calls to the data center
 */
public class CountLevelsAnalyzer extends LogAnalyzerDecorator{
    private final DataStorage container;

    /**
     * constructor also sets the proper container in the data center
     * @param analyzer gets a log analyzer and stores in decorator
     */
    public CountLevelsAnalyzer(LogAnalyzer analyzer){
        super(analyzer);

        this.container = DataCenter.getInstance().getOrCreateStorage(AnalysisMethods.COUNT_LEVELS,
                () -> StorageFactory.getInstance().createDataStorage(AnalysisMethods.COUNT_LEVELS));
    }

    /**
     * insert the data into the appropriate container and calls other decorators to analyze
     * @param logs - array of all the logs
     */
    @Override
    public void analyze(ArrayList<LogEntry> logs) {
        super.analyze(logs);

        for(LogEntry entry : logs){
            this.container.insertData(entry.logLevel());
        }

    }
}