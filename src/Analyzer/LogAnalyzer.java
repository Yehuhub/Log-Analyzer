package Analyzer;

import Services.LogEntry;

import java.util.ArrayList;

/**
 * base interface for all log analyzers to implement
 */
public interface LogAnalyzer {
    void analyze(ArrayList<LogEntry> logs);
    String getFileName();
}
