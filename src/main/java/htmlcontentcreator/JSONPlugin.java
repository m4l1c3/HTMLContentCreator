package htmlcontentcreator;


import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONPlugin extends ContentFormatPlugin implements IContentFormatPlugin {
    private FileReader fileReader;
    private JSONIteratorFactory jsonIteratorFactory;
    private JSONObject currentJSONObject;

    public JSONPlugin(String contentFormat, String contentType, String fileInput, String currentWorkingDirectory) {
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
            this.fileReader = new FileReader(this.fileInput);
            this.jsonIteratorFactory = new JSONIteratorFactory(this.fileReader, "");
            this.setRowIterator();
            this.processDataRows();
            this.getCMSBlocks();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public void setRowIterator() {
        try {
            this.rowIterator = this.jsonIteratorFactory.jsonObject.keySet().iterator();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void getLanguages(Iterator languageIterator) {
        while (languageIterator.hasNext()) {
            String language = (String) languageIterator.next();
            this.languages.add(new CMSLanguage(language));
        }
        this.setRowIterator();
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
            this.getLanguages(this.rowIterator);
            while (this.rowIterator.hasNext()) {
                String language = (String) this.rowIterator.next();
                this.currentJSONObject = (JSONObject) this.jsonIteratorFactory.jsonObject.get(language);
                this.processDataCells(currentJSONObject.keySet().iterator());
                this.currentRow++;
            }
            this.getCMSBlocks();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void processDataCells(Iterator iterator) {
        try {
            while (iterator.hasNext()) {
                String sectionName = (String) iterator.next();
                String sectionContent = (String) this.currentJSONObject.get(sectionName);
                CMSLanguage currentLanguage = this.languages.get(this.currentRow);
                currentLanguage.ContentPieces.add(new ContentPieces(sectionName, sectionContent));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
