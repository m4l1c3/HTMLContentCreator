package htmlcontentcreator;


public class ContentFormat {
    private String format;
    private String outputType;

    public ContentFormat(String format, String outputType) {
        this.format = format;
        this.outputType = outputType;
    }

    public String getFormat() {
        return this.format;
    }

    public String getOutputType() {
        return this.outputType;
    }
}
