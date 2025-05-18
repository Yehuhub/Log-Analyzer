package Analyzer;

import DataManagement.DataCenter;
import DataManagement.DataStorage;
import Factories.StorageFactory;
import Services.AnalysisMethods;
import Services.LogEntry;

import java.util.ArrayList;

/**
 * A log analyzer for analyzing common sources,
 * have its DataStorage container as a member to avoid repeated calls to the data center
 */
public class CommonSourcesAnalyzer extends LogAnalyzerDecorator{
    private final DataStorage container;

    /**
     * constructor also sets the proper container in the data center
     * @param analyzer gets a log analyzer and stores in decorator
     */
    public CommonSourcesAnalyzer(LogAnalyzer analyzer){
        super(analyzer);

        this.container = DataCenter.getInstance().getOrCreateStorage(AnalysisMethods.FIND_COMMON_SOURCE,
                () -> StorageFactory.getInstance().createDataStorage(AnalysisMethods.FIND_COMMON_SOURCE));
    }

    /**
     * insert the data into the appropriate container and calls other decorators to analyze
     * @param logs - array of all the logs
     */
    @Override
    public void analyze(ArrayList<LogEntry> logs) {
        super.analyze(logs);

        for(LogEntry entry : logs){
            this.container.insertData(entry.source());
        }

    }
}
