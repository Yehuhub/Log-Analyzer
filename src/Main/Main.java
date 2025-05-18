package Main;

import DataManagement.DataCenter;
import Exporters.FileExporter;
import Services.*;
import Threading.AnalysisManager;


public class Main {
    public static void main(String[] args) {

        try{
            DataCenter.getInstance().setExporter(new FileExporter());
            PropertiesManager.getInstance().loadProperties("config.properties");
            AnalysisManager manager = new AnalysisManager();
            manager.analyze();
            DataCenter.getInstance().exportData();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

}
