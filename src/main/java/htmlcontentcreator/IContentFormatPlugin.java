package htmlcontentcreator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;

public interface IContentFormatPlugin {
    void getLanguages(Iterator<Cell> languageIterator);

    void getCMSBlocks();

    void processDataRows(Iterator<Row> iterator);

    void processDataCells(Iterator<Cell> iterator);
}
