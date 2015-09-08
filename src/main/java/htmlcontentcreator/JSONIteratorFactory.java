package htmlcontentcreator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class JSONIteratorFactory implements IJSONIterator {

    JSONObject jsonObject;
    JSONArray jsonFilesArray;
    JSONParser jsonParser = new JSONParser();

    public JSONIteratorFactory(FileReader configFile, String sectionToParse) {
        try {
            this.jsonObject = (JSONObject) this.jsonParser.parse(configFile);
            if (!sectionToParse.equals("")) {
                this.jsonFilesArray = (JSONArray) jsonObject.get(sectionToParse);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public Iterator<String> getJsonIterator() {
        return this.jsonFilesArray.iterator();
    }
}
