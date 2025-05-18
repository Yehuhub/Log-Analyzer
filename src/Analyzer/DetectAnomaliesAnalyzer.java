package Analyzer;

import DataManagement.DataCenter;
import DataManagement.DataStorage;
import Factories.StorageFactory;
import Services.AnalysisMethods;
import Services.LogEntry;
import Services.PropertiesManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A log analyzer for detecting anomalies in a log file,
 * have its DataStorage container as a member to avoid repeated calls to the data center
 * have its default values set as members and changed if necessary
 */
public class DetectAnomaliesAnalyzer extends LogAnalyzerDecorator{
    private int threshold = 2;
    private int window = 30;
    private String[] levels = {"ERROR"};
    private final DataStorage container;

    /**
     * constructor also sets the proper container in the data center
     * @param analyzer gets a log analyzer and stores in decorator
     */
    public DetectAnomaliesAnalyzer(LogAnalyzer analyzer){
        super(analyzer);

        this.container = DataCenter.getInstance().getOrCreateStorage(AnalysisMethods.DETECT_ANOMALIES,
                () -> StorageFactory.getInstance().createDataStorage(AnalysisMethods.DETECT_ANOMALIES));
    }

    /**
     * filters the array for the required anomaly level,
     * then look for anomalies and insert to the data container
     * @param logs - array of all the logs
     */
    @Override
    public void analyze(ArrayList<LogEntry> logs) {
        super.analyze(logs);
        threshold = PropertiesManager.getInstance().getIntProperty("log.analysis.anomalies.threshold", threshold);
        window = PropertiesManager.getInstance().getIntProperty("log.analysis.anomalies.window", window);

        String[] levelsStrings = PropertiesManager.getInstance().getProperty("log.analysis.anomalies.levels").split(",");
        if(levelsStrings.length > 0 ){
            levels = levelsStrings;
        }

        List<LogEntry> filteredLogs = logs.stream()
                .filter(this::isMatchedLevels)
                .toList();

        ArrayList<LogEntry> anomalies = detectAnomalies(filteredLogs);

        for(LogEntry anomaly : anomalies ){
            this.container.insertData(getFileName() + "," + anomaly.timeStamp());
        }

    }

    /**
     * function for filtering according to log level
     * @param log - LogEntry to check
     * @return boolean - true if the log level is in one of the requested levels
     */
    private boolean isMatchedLevels(LogEntry log){
        for(String level : levels){
            if(log.logLevel().equalsIgnoreCase(level)){
                return true;
            }
        }
        return false;
    }

    /**
     * actual log anomaly detection using sliding window
     * @param logList - list of the logs
     * @return - array of all the anomalies
     */
    private ArrayList<LogEntry> detectAnomalies(List<LogEntry> logList){
        ArrayList<LogEntry> anomalies = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //if not enough anomalies to cross threshold
        if(logList.size() < threshold){
            return anomalies;
        }

        Map<LocalDateTime, LogEntry> logRecord = new HashMap<>();
        ArrayList<LocalDateTime> slidingWindow = new ArrayList<>();
        boolean inAnomaly = false;

        for(LogEntry log : logList){
            //get the time of current log
            LocalDateTime currentTime = LocalDateTime.parse(log.timeStamp(), formatter);

            //insert all the logs to the map
            logRecord.put(currentTime, log);

            //remove all the logs that are older than the window
            slidingWindow.removeIf(time -> Duration.between(time, currentTime).toSeconds() > window);

            // add the current log to the sliding window
            slidingWindow.add(currentTime);

            // check if the sliding window has enough logs to cross the threshold
            if(slidingWindow.size() >= threshold && !inAnomaly){
                // get the first log in that window
                LocalDateTime anomalyStart = slidingWindow.getFirst();
                //get that matching log from the map
                LogEntry toAdd = logRecord.get(anomalyStart);
                
                anomalies.add(toAdd);
                inAnomaly = true;
            } else if (slidingWindow.size() < threshold && inAnomaly) {
                inAnomaly = false;
            }
        }

        return anomalies;

    }
}

