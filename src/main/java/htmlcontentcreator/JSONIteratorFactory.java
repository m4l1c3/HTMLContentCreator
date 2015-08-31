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

    private JSONObject jsonObject;
    private JSONArray jsonFilesArray;
    private FileReader fileReader;
    private JSONParser jsonParser = new JSONParser();

    public JSONIteratorFactory(String currentWorkingDirectory, String jsonConfigFile) {
        try {
            this.fileReader = new FileReader(currentWorkingDirectory + jsonConfigFile);
            this.jsonObject = (JSONObject) this.jsonParser.parse(this.fileReader);
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
