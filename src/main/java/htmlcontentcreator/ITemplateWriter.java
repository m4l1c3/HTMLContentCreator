package htmlcontentcreator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ITemplateWriter implements ITemplateProcessor {
    String templateSuffix = "Template";
    String templateExtension = ".html";
    Path htmlTemplatePath;

    public ITemplateWriter() {
    }

    public void writeTemplates(ContentFormatPlugin contentFormatplugin) {
        if (contentFormatplugin.cmsBlocks.size() > 0) {
            for (CMSBlock currentCMSBlock : contentFormatplugin.cmsBlocks) {
                this.htmlTemplatePath = Paths.get(contentFormatplugin.currentWorkingDirectory, currentCMSBlock.Name +
                        this.templateSuffix + this.templateExtension);

                try {
                    byte[] encoded = Files.readAllBytes(this.htmlTemplatePath);
                    String htmlTemplate = new String(encoded, contentFormatplugin.contentFormat.getOutputType());

                    for (ContentPieces currentContentPiece : currentCMSBlock.Language.ContentPieces) {
                        htmlTemplate = htmlTemplate.replace("%%" + currentContentPiece.SectionName + "%%",
                                currentContentPiece.SectionContent);
                    }
                    htmlTemplate = htmlTemplate.replace("%%language-code%%",
                            currentCMSBlock.Language.Name);
                    PrintWriter writer =
                            new PrintWriter(contentFormatplugin.currentWorkingDirectory + currentCMSBlock.Language.Name
                                    + "-" + currentCMSBlock.Name + this.templateExtension,
                                    contentFormatplugin.contentFormat.getOutputType());
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
}
