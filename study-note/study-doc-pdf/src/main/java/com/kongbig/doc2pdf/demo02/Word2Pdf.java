package com.kongbig.doc2pdf.demo02;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 *
 */
@Slf4j
public class Word2Pdf {

    public static void main(String[] args) {
        word2pdf("D:\\dev\\test-poi.doc", "D:\\dev\\test-poi-doc.pdf");
        word2pdf("D:\\dev\\test-poi.docx", "D:\\dev\\test-poi-docx.pdf");
    }

    public static void word2pdf(String srcPath, String destPath) {
        // 验证License，若不验证则转化出的PDP文档会有水印产生
        if (!getLicense()) {
            return;
        }

        try (
                // 新建一个空白pdf文档
                FileOutputStream os = new FileOutputStream(new File(destPath));
        ) {
            // srcPath是将要被转化的word文档
            Document document = new Document(srcPath);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            document.save(os, SaveFormat.PDF);
            document.deepClone();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private static boolean getLicense() {
        boolean result = false;
        try {
            //  wordlicense.xml应放在..\WebRoot\WEB-INF\classes路径下
            InputStream is = Word2Pdf.class.getClassLoader().getResourceAsStream("aspose-license.xml");
            com.aspose.words.License aposeLic = new com.aspose.words.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

}
