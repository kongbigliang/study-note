package com.kongbig.doc.demo02;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 处理docx
 * @author: lianggangda
 * @date: 2020/4/18 21:42
 */
@Slf4j
public class XwpfDocx {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>(3);
        map.put("name", "kongbig");
        map.put("age", "25");
        map.put("sex", "男");
        map.put("checkbox", "☑");
        String tmpFile = "D:\\dev\\test-poi.docx";
        String exportFile = "D:\\dev\\test-poi-replace.docx";
        createWordByModel(tmpFile, map, exportFile);
    }

    private static void createWordByModel(String tmpFile, Map<String, String> contentMap, String exportFile) {
        FileInputStream in = null;
        XWPFDocument document = null;
        OutputStream outputStream = null;
        try {
            in = new FileInputStream(new File(tmpFile));
            document = new XWPFDocument(in);

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            List<IBodyElement> bodyElements = document.getBodyElements();

            paragraphs.forEach(p -> {
                List<XWPFRun> runs = p.getRuns();
                for (XWPFRun run : runs) {
                    String key = run.text();
                    if (contentMap.containsKey(key)) {
                        run.setText(contentMap.get(key), 0);
                    }
                }
            });

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.write((OutputStream) byteArrayOutputStream);
            outputStream = new FileOutputStream(exportFile);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            IOUtils.closeQuietly(in);
        }

    }

}
