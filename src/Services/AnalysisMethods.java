package Services;

/**
 * Enum representing different analysis methods.
 * This enum is used to specify the type of analysis to be performed.
 * It includes methods for counting levels, finding common sources, and detecting anomalies.
 */
public enum AnalysisMethods {
    COUNT_LEVELS,FIND_COMMON_SOURCE,DETECT_ANOMALIES;

    /**
     * Parses a string to find the corresponding AnalysisMethods enum value.
     * @param method the string representation of the analysis method
     * @return the corresponding AnalysisMethods enum value
     * @throws IllegalArgumentException if the method is not a valid AnalysisMethods value
     */
    public static AnalysisMethods parseMethod(String method) throws IllegalArgumentException {
        return AnalysisMethods.valueOf(method.toUpperCase());
    }
}
