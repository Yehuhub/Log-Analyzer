package Services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * a class for parsing log files according to the format
 */
public class LogParser {
    /**
     * function  gets the filepath and return an array of LogEntry
     * @param filePath - logfile path
     * @return ArrayList<LogEntry> - array of log entries
     */
    public ArrayList<LogEntry> parseLogFile(String filePath){
        ArrayList<LogEntry> parsedFile = new ArrayList<LogEntry>();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while((line = br.readLine()) != null){
                lineNumber++;

                if(!line.startsWith("[") || !line.endsWith("]")){
                    System.out.println("unexpected input " + line );
                    continue;
                }

                String [] parsedLine = line.substring(1, line.length()-1).split("\\] \\[");

                if(parsedLine.length != 4){
                    System.out.println("unexpected input " + line );
                    continue;
                }

                try{
                    String timeStamp = parsedLine[0];
                    String logLevel = parsedLine[1];
                    String source = parsedLine[2];
                    String message = parsedLine[3];

                    parsedFile.add(new LogEntry(timeStamp, logLevel, source, message));
                }catch (IllegalArgumentException e){
                    System.out.println("unexpected input " + line);
                }
            }

            return parsedFile;
        }catch (IOException e){
            System.err.println("error processing file " + filePath);
            throw new RuntimeException(e);
        }
    }
}
