package htmlcontentcreator;


import java.io.*;

public class Config {
    String configFile = "ContentConfigJSON.txt";
    String sampleXlsx = "Sample.xlsx";
    FileReader config;

    public Config(String currentWorkingDirectory) {
        try {
            File ConfigFile = new File(currentWorkingDirectory + this.configFile);
            if (ConfigFile.exists() && !ConfigFile.isDirectory()) {
                this.config = new FileReader(currentWorkingDirectory + this.configFile);
            } else {
                FileWriter configFileWriter = new FileWriter(currentWorkingDirectory + this.configFile);
                configFileWriter.write("{");
                configFileWriter.write(System.lineSeparator());
                configFileWriter.write("\t\"files\": [");
                configFileWriter.write(System.lineSeparator());
                configFileWriter.write("\t\t\"" + currentWorkingDirectory + this.sampleXlsx + "\"");
                configFileWriter.write(System.lineSeparator());
                configFileWriter.write("\t]");
                configFileWriter.write(System.lineSeparator());
                configFileWriter.write("}");
                configFileWriter.close();
                this.config = new FileReader(currentWorkingDirectory + this.configFile);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
