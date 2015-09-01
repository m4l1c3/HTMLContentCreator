package htmlcontentcreator;


public class ContentFormatPluginFactory {
    public ContentFormatPlugin getContentFormat(String contentFormat, String currentIteratorFile,
                                                String currentWorkingDirectory) {

        if ("JSON".equals(contentFormat.toUpperCase())) {
            return new JSONPlugin();
        } else {
            return new XLSXPlugin(contentFormat, "UTF-8", currentIteratorFile, currentWorkingDirectory);
        }
    }
}
