package htmlcontentcreator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class JSONIteratorFactory implements IJSONIterator {

    JSONObject jsonObject;
    JSONArray jsonFilesArray;
    JSONParser jsonParser = new JSONParser();

    public JSONIteratorFactory(Config config) {
        try {
            this.jsonObject = (JSONObject) this.jsonParser.parse(config.config);
            this.jsonFilesArray = (JSONArray) jsonObject.get("files");
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
