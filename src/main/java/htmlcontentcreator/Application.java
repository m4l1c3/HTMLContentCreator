package htmlcontentcreator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class Application {

    public static void main(String[] args) {
        ArrayList<CMSBlock> cmsBlocks = new ArrayList<CMSBlock>();
        JSONParser jsonParser = new JSONParser();
        String currentWorkingDirectory;

        try {
            currentWorkingDirectory = Application.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "../../../";
            FileReader reader = new FileReader(currentWorkingDirectory + "ContentConfigJSON.txt");
            Object objJSON = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) objJSON;
            JSONArray jsonFilesArray = (JSONArray) jsonObject.get("files");
            Iterator jsonIterator = jsonFilesArray.iterator();
            processJSONArray(cmsBlocks, currentWorkingDirectory, jsonIterator);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (ParseException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            System.out.println("Finished");
        }
    }

    private static void processJSONArray(ArrayList<CMSBlock> cmsBlocks, String currentWorkingDirectory, Iterator<String> jsonIterator) {
        ArrayList<CMSLanguages> languages;
        while (jsonIterator.hasNext()) {
            languages = new ArrayList<CMSLanguages>();
            try {
                String currentIteratorFile = jsonIterator.next();
                File excelFile = new File(currentIteratorFile);
                FileInputStream inputStream = new FileInputStream(excelFile);
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = workbook.getSheetAt(0);

                processCMSData(cmsBlocks, languages, currentWorkingDirectory, currentIteratorFile, sheet);

            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private static void processCMSData(ArrayList<CMSBlock> cmsBlocks, ArrayList<CMSLanguages> languages, String currentWorkingDirectory, String currentIteratorFile, XSSFSheet sheet) {
        int currentRow = 0;
        int currentColumn = 0;

        Iterator<Row> itr = sheet.iterator();
        processRowIterator(languages, currentRow, currentColumn, itr);
        createCMSBlocks(cmsBlocks, languages, currentWorkingDirectory, currentIteratorFile);
    }

    private static void createCMSBlocks(ArrayList<CMSBlock> cmsBlocks, ArrayList<CMSLanguages> languages, String currentWorkingDirectory, String currentIteratorFile) {
        String currentPageName;
        if (languages.size() > 0) {
            for (CMSLanguages currentLanguage : languages) {
                currentPageName = currentIteratorFile.substring(
                        currentIteratorFile.lastIndexOf("/") + 1, currentIteratorFile.lastIndexOf("."));
                CMSBlock cmsBlock = new CMSBlock(currentPageName, currentLanguage);
                cmsBlocks.add(cmsBlock);
            }

            writeCMSTemplates(cmsBlocks, currentWorkingDirectory);
        }
    }

    private static void writeCMSTemplates(ArrayList<CMSBlock> cmsBlocks, String currentWorkingDirectory) {
        if (cmsBlocks.size() > 0) {
            Iterator<CMSBlock> cmsBlockIterator = cmsBlocks.iterator();
            Path htmlTemplatePath;

            while (cmsBlockIterator.hasNext()) {
                CMSBlock currentCMSBlock = cmsBlockIterator.next();
                htmlTemplatePath = Paths.get(currentWorkingDirectory, currentCMSBlock.Name + "Template.html");

                try {
                    byte[] encoded = Files.readAllBytes(htmlTemplatePath);
                    String htmlTemplate = new String(encoded, "UTF-8");

                    for (ContentPieces currentContentPiece : currentCMSBlock.Language.ContentPieces) {
                        htmlTemplate = htmlTemplate.replace("%%" + currentContentPiece.SectionName + "%%",
                                currentContentPiece.SectionContent);
                    }
                    htmlTemplate = htmlTemplate.replace("%%language-code%%",
                            currentCMSBlock.Language.Name);
                    PrintWriter writer =
                            new PrintWriter(currentWorkingDirectory + currentCMSBlock.Language.Name
                                    + "-" + currentCMSBlock.Name + ".html",
                                    "UTF-8");
                    writer.write(htmlTemplate);
                    writer.close();
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private static void processRowIterator(ArrayList<CMSLanguages> languages, int currentRow, int currentColumn, Iterator<Row> itr) {
        while (itr.hasNext()) {
            Row row = itr.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (currentRow == 0) {
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (currentColumn != 0) {
                        languages.add(new CMSLanguages(cell.toString()));
                    }
                    currentColumn++;
                }
                currentColumn = 0;
            } else {
                String sectionName = "";

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (currentColumn != 0) {
                        CMSLanguages language = languages.get(currentColumn - 1);
                        ContentPieces contentPieces = new ContentPieces(sectionName, cell.toString());
                        language.ContentPieces.add(contentPieces);
                    } else {
                        sectionName = cell.toString();
                    }
                    currentColumn++;
                }
                currentColumn = 0;
            }
            currentRow++;
        }
    }
}
