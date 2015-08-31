package htmlcontentcreator;

import java.util.ArrayList;

public class CMSLanguage {
    public String Name;
    public ArrayList<ContentPieces> ContentPieces;

    public CMSLanguage(String name) {
        this.Name = name;
        this.ContentPieces = new ArrayList<ContentPieces>();
    }
}
