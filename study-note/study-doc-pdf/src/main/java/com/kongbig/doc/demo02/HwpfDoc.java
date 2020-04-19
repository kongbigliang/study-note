package com.kongbig.doc.demo02;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 处理doc
 * @author: lianggangda
 * @date: 2020/4/18 20:57
 */
@Slf4j
public class HwpfDoc {

    public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<>(3);
        map.put("name", "kongbig");
        map.put("age", "25");
        map.put("sex", "男");
        map.put("checkbox", "☑");
        String tmpFile = "D:\\dev\\test-poi.doc";
        String exportFile = "D:\\dev\\test-poi-replace.doc";
        createWordByModel(tmpFile, map, exportFile);
    }

    private static void createWordByModel(String tmpFile, Map<String, String> contentMap, String exportFile) throws Exception {
        InputStream in = null;
        in = new FileInputStream(new File(tmpFile));

        HWPFDocument document = new HWPFDocument(in);
        // 读取文本内容
        Range bodyRange = document.getRange();
        System.out.println(bodyRange.toString());
        System.out.println(bodyRange.text());
        // 替换内容
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            bodyRange.replaceText(entry.getKey(), entry.getValue());
        }

        // 导出到文件
        OutputStream outputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.write((OutputStream) byteArrayOutputStream);
            outputStream = new FileOutputStream(exportFile);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(document);
        }
    }

}
