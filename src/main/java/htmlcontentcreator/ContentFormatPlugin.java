package htmlcontentcreator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class ContentFormatPlugin implements IContentFormatPlugin {

    ContentFormat contentFormat;
    File fileInput;
    FileInputStream fileInputStream;
    XSSFWorkbook workBook;
    XSSFSheet sheet;
    String currentFile;
    String currentWorkingDirectory;
    String currentPageName;
    Iterator<Row> rowIterator;
    ArrayList<CMSLanguage> languages;
    ArrayList<CMSBlock> cmsBlocks;

    public abstract void getLanguages(Iterator<Cell> languageIterator);

    public abstract void getCMSBlocks();

    public abstract void processDataRows(Iterator<Row> iterator);

    public abstract void processDataCells(Iterator<Cell> iterator);
}
