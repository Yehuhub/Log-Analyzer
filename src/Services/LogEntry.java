package Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * record to hold a log entry
 * @param timeStamp - time and date
 * @param logLevel - log level
 * @param source - src
 * @param message - log message
 */
public record LogEntry(String timeStamp, String logLevel, String source, String message) {

    /**
     * compact constructor used for validation (assuming message could be empty)
     * @param timeStamp - time and date
     * @param logLevel - log level
     * @param source - src
     * @param message - log message
     */
    public LogEntry {
        if(!isValidTimeStamp(timeStamp)){
            throw new IllegalArgumentException("invalid timestamp format");
        }
        if(logLevel == null || source == null){
            throw new IllegalArgumentException("invalid format");
        }
    }

    /**
     * function to validate timestamp
     * @param timeStamp - time and date
     * @return - boolean
     */
    private static boolean isValidTimeStamp(String timeStamp){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime.parse(timeStamp, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    /**
     * returns original log representation
     * @return - String
     */
    @Override
    public String toString(){
        return "[" + timeStamp + "] [" + logLevel + "] [" + source + "] " + message;
    }
}