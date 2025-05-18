package Threading;

import Services.PropertiesManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


/**
 * AnalysisManager in charge of getting file names, creating a threadpoop and executing all the threads
 */
public class AnalysisManager {
    private final int poolSize;
    private final String[] filePaths ;

    /**
     * constructor, sets the default values for configuration
     */
    public AnalysisManager(){
        poolSize = PropertiesManager.getInstance().getIntProperty("thread.pool.size", 5);
        filePaths = getFilePaths();
    }

    /**
     * function to start the analysis
     * @throws IllegalStateException - if no log files are found
     */
    public void analyze()throws IllegalStateException{

        if(filePaths.length == 0){
            throw new IllegalStateException("No log files found");
        }

        try(ExecutorService threadPool = Executors.newFixedThreadPool(poolSize)){
            for (String file : filePaths){
                threadPool.submit(new LogAnalyzingThread(file));
            }
            threadPool.shutdown();
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * function to get the file paths from the log directory
     * @return - array of file paths
     */
    private String[] getFilePaths(){
        String path = PropertiesManager.getInstance().getProperty("log.directory");
        Path folder = Paths.get(path);

        try(Stream<Path> pathStream = Files.list(folder)){
            return pathStream.filter(Files::isRegularFile)
                    .filter(p -> p.toString().toLowerCase().endsWith(".log"))
                    .map(Path::toString)
                    .toArray(String[]::new);
        }catch(IOException ie){
            System.out.println("Invalid log path!");
            System.out.println(path);
            System.out.println(folder.toString());
            System.exit(0); // not graceful but required in the exercise
        }
        return new String[0]; // we have to return something
    }


}
