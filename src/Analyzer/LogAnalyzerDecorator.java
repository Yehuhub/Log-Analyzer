package Analyzer;

import Services.LogEntry;

import java.util.ArrayList;

/**
 * decorator class for all log analyzers to extend
 */
public abstract class LogAnalyzerDecorator implements LogAnalyzer{
    protected LogAnalyzer wrappedAnalyzer;

    /**
     * constructor, gets another analyzer and saves it as member to later be used to analyze
     * @param analyzer - log analyzer
     */
    public LogAnalyzerDecorator(LogAnalyzer analyzer){
        this.wrappedAnalyzer = analyzer;
    }

    /**
     * analyze the logs using the saved analyzer
     * @param logs - array of logs
     */
    @Override
    public void analyze(ArrayList<LogEntry> logs) {
        wrappedAnalyzer.analyze(logs);
    }

    /**
     * returns the file name the thread is working on
     * @return String
     */
    @Override
    public String getFileName(){return wrappedAnalyzer.getFileName();}
}
