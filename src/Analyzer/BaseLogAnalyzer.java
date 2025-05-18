package Analyzer;

import Services.LogEntry;

import java.util.ArrayList;

/**
 * base Log analyzer used for storing the filename in the analyzer
 */
public class BaseLogAnalyzer implements LogAnalyzer{
    private final String fileName;

    public BaseLogAnalyzer(String file){this.fileName = file;}

    /**
     *
     * @return the current file name
     */
    @Override
    public String getFileName() {
        return fileName;
    }

    /**
     *
     * @param logs array of all logs
     */
    @Override
    public void analyze(ArrayList<LogEntry> logs) {}
}
