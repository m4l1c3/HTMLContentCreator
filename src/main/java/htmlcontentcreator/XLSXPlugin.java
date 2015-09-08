package htmlcontentcreator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class XLSXPlugin extends ContentFormatPlugin implements IContentFormatPlugin {
    private XSSFWorkbook workBook;
    private XSSFSheet sheet;

    public XLSXPlugin(String contentFormat, String contentType, String fileInput, String currentWorkingDirectory) {
        this.contentFormat = new ContentFormat(contentFormat, contentType);
        this.fileInput = new File(fileInput);
        this.currentFile = fileInput;
        this.languages = new ArrayList<CMSLanguage>();
        this.cmsBlocks = new ArrayList<CMSBlock>();
        this.currentWorkingDirectory = currentWorkingDirectory;
        this.currentPageName = this.currentFile.substring(this.currentFile.lastIndexOf("/") + 1,
                this.currentFile.lastIndexOf("."));

        try {
            this.fileInputStream = new FileInputStream(this.fileInput);
            this.workBook = new XSSFWorkbook(this.fileInputStream);
            this.sheet = this.workBook.getSheetAt(0);
            this.rowIterator = sheet.iterator();
            this.processDataRows();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void getLanguages(Iterator iterator) {
        while (iterator.hasNext()) {
            Cell cell = (Cell) iterator.next();
            if (this.currentColumn != 0) {
                this.languages.add(new CMSLanguage(cell.toString()));
            }
            this.currentColumn++;
        }
    }

    public void getCMSBlocks() {
        if (this.languages.size() > 0) {
            for (CMSLanguage currentLanguage : this.languages) {
                CMSBlock cmsBlock = new CMSBlock(this.currentPageName, currentLanguage);
                this.cmsBlocks.add(cmsBlock);
            }
        }
    }

    public void processDataRows() {
        try {
            while (this.rowIterator.hasNext()) {
                Row row = (Row) this.rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                this.processDataCells(cellIterator);
                this.currentRow++;
            }
            this.getCMSBlocks();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void processDataCells(Iterator iterator) {
        try {
            if (this.currentRow == 0) {
                this.getLanguages(iterator);
                this.currentColumn = 0;
            } else {
                String sectionName = "";

                while (iterator.hasNext()) {
                    Cell cell = (Cell) iterator.next();

                    if (this.currentColumn != 0) {
                        CMSLanguage language = this.languages.get(this.currentColumn - 1);
                        ContentPieces contentPieces = new ContentPieces(sectionName, cell.toString());
                        language.ContentPieces.add(contentPieces);
                    } else {
                        sectionName = cell.toString();
                    }
                    this.currentColumn++;
                }
                this.currentColumn = 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
