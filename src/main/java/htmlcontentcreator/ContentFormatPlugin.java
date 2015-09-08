package htmlcontentcreator;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ContentFormatPlugin {

    public int currentRow = 0;
    public int currentColumn = 0;
    ContentFormat contentFormat;
    File fileInput;
    FileInputStream fileInputStream;
    String currentFile;
    String currentWorkingDirectory;
    String currentPageName;
    Iterator rowIterator;
    ArrayList<CMSLanguage> languages;
    ArrayList<CMSBlock> cmsBlocks;
}
