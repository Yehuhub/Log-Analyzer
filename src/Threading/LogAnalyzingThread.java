package Threading;

import Analyzer.LogAnalyzer;
import Factories.LogAnalyzerFactory;
import Services.LogParser;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * LogAnalyzingThread is a thread that analyzes a log file
 */
public class LogAnalyzingThread implements Runnable{
    private final String filePath;

    /**
     * constructor
     * @param path - path to the log file
     */
    public LogAnalyzingThread(String path){
        this.filePath = path;
    }

    /**
     * function to run the thread
     */
    @Override
    public void run(){
        LogParser parser = new LogParser(); // new parser
        var logs = parser.parseLogFile(this.filePath); // parse the file

        // get file name from path
        Path file = Paths.get(this.filePath);
        String fileName = file.getFileName().toString();

        LogAnalyzer analyzer = LogAnalyzerFactory.getInstance().createLogAnalyzer(fileName);//create the analyzer
        analyzer.analyze(logs); // analyze the logs
    }
}
