package htmlcontentcreator;

import java.util.Iterator;

public class Application {

    public static void main(String[] args) {
        String currentWorkingDirectory;
        String configFile = "ContentConfigJSON.txt";

        try {
            currentWorkingDirectory = Application.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "../../../";

            JSONIteratorFactory jsonIteratorFactory;
            jsonIteratorFactory = new JSONIteratorFactory(currentWorkingDirectory, configFile);
            Iterator<String> jsonIterator = jsonIteratorFactory.getJsonIterator();
            process(currentWorkingDirectory, jsonIterator);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            System.out.println("Finished");
        }
    }

    private static void process(String currentWorkingDirectory, Iterator<String> jsonIterator) {
        while (jsonIterator.hasNext()) {
            try {
                String currentIteratorFile = jsonIterator.next();
                XLSXPluginI data = new XLSXPluginI("XLSX", "UTF-8", currentIteratorFile, currentWorkingDirectory);
                ITemplateWriter templateWriter = new ITemplateWriter();
                templateWriter.writeTemplates(data);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
