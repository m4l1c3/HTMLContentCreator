package htmlcontentcreator;


public class ContentFormatPluginFactory {
    public ContentFormatPlugin getContentFormat(String contentFormat, String currentIteratorFile,
                                                String currentWorkingDirectory) {
        if ("JSON".equals(contentFormat.toUpperCase())) {
            return new JSONPlugin(contentFormat, "UTF-8", currentIteratorFile, currentWorkingDirectory);
        } else if ("XML".equals(contentFormat.toUpperCase())) {
            return new XMLPlugin(contentFormat, "UTF-8", currentIteratorFile, currentWorkingDirectory);
        } else {
            return new XLSXPlugin(contentFormat, "UTF-8", currentIteratorFile, currentWorkingDirectory);
        }
    }
}
