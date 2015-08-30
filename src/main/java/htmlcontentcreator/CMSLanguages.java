package htmlcontentcreator;

import java.util.ArrayList;

public class CMSLanguages {
    public String Name;
    public ArrayList<ContentPieces> ContentPieces;

    public CMSLanguages(String name) {
        this.Name = name;
        this.ContentPieces = new ArrayList<ContentPieces>();
    }
}
