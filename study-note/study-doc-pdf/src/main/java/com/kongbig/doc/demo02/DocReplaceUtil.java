package com.kongbig.doc.demo02;

import com.kongbig.common.dto.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: doc和docx替换文本的工具类
 * @author: lianggangda
 * @date: 2020/4/20 8:42
 */
@Slf4j
public class DocReplaceUtil {

    private static boolean IS_DEBUG = false;

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>(4);
        /*map.put("name", "kongbig");
        map.put("age", "25");
        map.put("sex", "男");
        map.put("checkbox", "☑");
        String srcPath1 = "D:\\dev\\test-poi.doc";
        String destPath1 = "D:\\dev\\test-poi-replace.doc";
        String srcPath2 = "D:\\dev\\test-poi.docx";
        String destPath2 = "D:\\dev\\test-poi-replace.docx";
        DocReplaceUtil.replaceAndGenerateWord(srcPath1, map, destPath1);
        DocReplaceUtil.replaceAndGenerateWord(srcPath2, map, destPath2);
        DocReplaceUtil.replaceAndGenerateWord("D:\\dev\\1.text", map, "D:\\dev\\1-replace.text");*/

        map.clear();
        map.put("bsqf", "张三");
        map.put("idcard", "440661273817293");
        map.put("company", "喜羊羊公司");
        map.put("platform", "京西");
        map.put("shop", "华南第一店");
        map.put("url", "https://www.baidu.com");
        map.put("shopid", String.valueOf(System.currentTimeMillis()));
        map.put("shid", String.valueOf(System.currentTimeMillis()));
        map.put("sale", "xxx销售方");
        map.put("bookY1", "2020");
        map.put("bookM1", "4");
        map.put("bookD1", "20");
        map.put("bookY2", "2020");
        map.put("bookM2", "8");
        map.put("bookD2", "20");
        map.put("signY", "2020");
        map.put("signM", "4");
        map.put("signD", "20");
        map.put("mao", "□");
        map.put("qiye", "□");
        map.put("geren", "☑");
        // String srcPath3 = "C:\\Users\\Administrator\\Desktop\\book\\淘宝销售授权书1-copy.docx";
        String srcPath3 = "C:\\Users\\Administrator\\Desktop\\book\\淘宝销售授权书1 - 副本.docx";
        String destPath3 = "C:\\Users\\Administrator\\Desktop\\book\\淘宝销售授权书1-copy-replace.docx";
        String srcPath4 = "C:\\Users\\Administrator\\Desktop\\book\\拼多多销售授权书 - 副本.doc";
        String destPath4 = "C:\\Users\\Administrator\\Desktop\\book\\拼多多销售授权书-copy-replace.doc";
        DocReplaceUtil.replaceAndGenerateWord(srcPath3, map, destPath3);
        DocReplaceUtil.replaceAndGenerateWord(srcPath4, map, destPath4);
    }

    public static void replaceAndGenerateWord(String srcPath, Map<String, String> contentMap, String destPath) {
        String fileType = srcPath.split("\\.")[1];
        switch (fileType) {
            case Constants.FileType.DOC:
                // 处理doc
                dealDoc(srcPath, contentMap, destPath);
                break;
            case Constants.FileType.DOCX:
                // 处理docx
                dealDocx(srcPath, contentMap, destPath);
                break;
            default:
                log.error("不能处理类型为：[{}]的文件", fileType);
                break;
        }
    }

    /**
     * 处理doc的替换
     * 整个文本进行替换，所以key不能有包含关系
     *
     * @param srcPath
     * @param contentMap
     * @param destPath
     */
    private static void dealDoc(String srcPath, Map<String, String> contentMap, String destPath) {
        try (
                InputStream in = new FileInputStream(new File(srcPath));
                HWPFDocument document = new HWPFDocument(in);
                OutputStream outputStream = new FileOutputStream(destPath);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ) {
            // 读取文本内容
            Range range = document.getRange();
            // 替换内容
            for (Map.Entry<String, String> entry : contentMap.entrySet()) {
                String key = entry.getKey(), val = entry.getValue();
                log.info("dealDoc-将key值为[{}]的文本替换为[{}]", key, val);
                range.replaceText(key, val);
            }
            // 导出到文件
            document.write((OutputStream) byteArrayOutputStream);
            outputStream.write(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 处理docx的替换
     *
     * @param srcPath
     * @param contentMap
     * @param destPath
     */
    private static void dealDocx(String srcPath, Map<String, String> contentMap, String destPath) {
        InputStream in = null;
        XWPFDocument document = null;
        OutputStream outputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            in = new FileInputStream((new File(srcPath)));
            document = new XWPFDocument(in);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            // 替换内容
            paragraphs.forEach(p -> {
                List<XWPFRun> runs = p.getRuns();
                for (XWPFRun run : runs) {
                    String key = run.text().trim();
                    if (StringUtils.isBlank(key)) {
                        continue;
                    }
                    if (log.isDebugEnabled() && IS_DEBUG) {
                        log.debug("dealDocx-key值为[{}]", key);
                    }
                    if (contentMap.containsKey(key)) {
                        String val = contentMap.get(key);
                        log.info("dealDocx-将key值为[{}]的文本替换为[{}]", key, val);
                        run.setText(val, 0);
                    }
                }
            });
            // 导出到文件
            byteArrayOutputStream = new ByteArrayOutputStream();
            document.write((OutputStream) byteArrayOutputStream);
            outputStream = new FileOutputStream(destPath);
            outputStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(byteArrayOutputStream);
            try {
                if (document != null) {
                    document.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            IOUtils.closeQuietly(in);
        }
    }

}
