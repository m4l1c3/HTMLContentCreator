package htmlcontentcreator;

import java.util.Iterator;

public interface IContentFormatPlugin {
    void getLanguages(Iterator languageIterator);

    void getCMSBlocks();

    void processDataRows();

    void processDataCells(Iterator iterator);
}
