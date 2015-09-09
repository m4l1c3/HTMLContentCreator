package htmlcontentcreator;

import java.io.File;
import java.util.Iterator;

public class Application {

    public static void main(String[] args) {
        String currentWorkingDirectory;

        try {
            File f = new File("");
            currentWorkingDirectory = f.getAbsolutePath() + "/";
            Config config = new Config(currentWorkingDirectory);
            JSONIteratorFactory jsonIteratorFactory;
            jsonIteratorFactory = new JSONIteratorFactory(config.config, "files");
            Iterator<String> jsonIterator = jsonIteratorFactory.getIterator();
            process(currentWorkingDirectory, jsonIterator);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            System.out.println("Finished");
        }
    }

    private static void process(String currentWorkingDirectory, Iterator jsonIterator) {
        try {
            while (jsonIterator.hasNext()) {
                String currentIteratorFile = (String) jsonIterator.next();
                ContentFormatPluginFactory contentFormatPluginFactory = new ContentFormatPluginFactory();
                ContentFormatPlugin data = contentFormatPluginFactory.getContentFormat(currentIteratorFile.substring(
                        currentIteratorFile.lastIndexOf(".") + 1).toUpperCase(), currentIteratorFile, currentWorkingDirectory);
                ITemplateWriter templateWriter = new ITemplateWriter();
                templateWriter.writeTemplates(data);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
