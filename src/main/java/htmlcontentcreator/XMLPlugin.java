package htmlcontentcreator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class XMLPlugin extends ContentFormatPlugin implements IContentFormatPlugin {
    private Document doc;
    private String currentPageSection;

    public XMLPlugin(String contentFormat, String contentType, String fileInput, String currentWorkingDirectory) {
        this.contentFormat = new ContentFormat(contentFormat, contentType);
        this.fileInput = new File(fileInput);
        this.currentFile = fileInput;
        this.languages = new ArrayList<CMSLanguage>();
        this.cmsBlocks = new ArrayList<CMSBlock>();
        this.currentWorkingDirectory = currentWorkingDirectory;
        this.currentPageName = this.currentFile.substring(this.currentFile.lastIndexOf("/") + 1,
                this.currentFile.lastIndexOf("."));
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            this.doc = builder.parse(this.fileInput);
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xpath.evaluate("//Language", this.doc, XPathConstants.NODESET);
            this.getLanguages(nodeListToIterator(nodeList));
            this.rowIterator = nodeListToIterator((NodeList) xpath.evaluate("//section[@id != 'languages']", this.doc, XPathConstants.NODESET));
            this.processDataRows();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Iterator nodeListToIterator(NodeList nodeList) {
        ArrayList iteratorItems = new ArrayList();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String nodeTextValue = node.getTextContent().trim();
            if (!nodeTextValue.equals("\n") && !nodeTextValue.equals("")) {
                iteratorItems.add(node);
            }
        }

        return iteratorItems.iterator();
    }

    public void getCMSBlocks() {
        if (this.languages.size() > 0) {
            for (CMSLanguage currentLanguage : this.languages) {
                CMSBlock cmsBlock = new CMSBlock(this.currentPageName, currentLanguage);
                this.cmsBlocks.add(cmsBlock);
            }
        }
    }

    public void getLanguages(Iterator iterator) {
        while (iterator.hasNext()) {
            Node node = (Node) iterator.next();
            this.languages.add(new CMSLanguage(node.getTextContent()));
        }
    }

    public void processDataRows() {
        try {
            while (this.rowIterator.hasNext()) {
                Node node = (Node) this.rowIterator.next();
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Iterator columnIterator = nodeListToIterator(node.getChildNodes());
                    this.currentPageSection = node.getAttributes().getNamedItem("id").getTextContent();
                    this.processDataCells(columnIterator);
                }
                this.currentRow++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.getCMSBlocks();
    }

    public void processDataCells(Iterator iterator) {
        String sectionName = this.currentPageSection;
        try {
            int i = 0;
            while (iterator.hasNext()) {
                Node section = (Node) iterator.next();
                CMSLanguage currentLanguage = this.languages.get(i);
                String sectionContent = section.getTextContent().replaceAll("\n", "").trim();
                currentLanguage.ContentPieces.add(new ContentPieces(sectionName, sectionContent));
                i++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
