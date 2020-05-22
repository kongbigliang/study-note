package com.kongbig.doc.demo02;

import com.kongbig.common.dto.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSym;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
         String srcPath3 = "C:\\Users\\Administrator\\Desktop\\book\\淘宝销售授权书1 - 副本.docx";
         String destPath3 = "C:\\Users\\Administrator\\Desktop\\book\\淘宝销售授权书1-copy-replace.docx";
        // String srcPath4 = "C:\\Users\\Administrator\\Desktop\\book\\拼多多销售授权书 - 副本.doc";
        // String destPath4 = "C:\\Users\\Administrator\\Desktop\\book\\拼多多销售授权书-copy-replace.doc";
        DocReplaceUtil.replaceAndGenerateWord(srcPath3, map, destPath3, null);
        // DocReplaceUtil.replaceAndGenerateWord(srcPath4, map, destPath4, null);
    }

    /**
     * 磅数和字号的关系：
     * 绑    字号
     * 5    八号
     * 5.5  七号
     * 6.5  小六
     * 7.5  六号
     * 9    小五
     * 10.5 五号
     * 12   小四
     * 14   四号
     * 15   小三
     * 16   三号
     * 18   小二
     * 22   二号
     * 24   小一
     * 26   一号
     * 36   小初
     * 42   初号
     *
     * @param srcPath
     * @param contentMap
     * @param destPath
     * @param fontSize   默认字体：小四
     */
    public static void replaceAndGenerateWord(String srcPath, Map<String, String> contentMap, String destPath, Integer fontSize) {
        //  默认字体：小四
        int defaultFontSize = 12;
        String fileType = srcPath.split("\\.")[1];
        switch (fileType) {
            case Constants.FileType.DOC:
                // 处理doc
                dealDoc(srcPath, contentMap, destPath);
                break;
            case Constants.FileType.DOCX:
                // 处理docx
                dealDocx(srcPath, contentMap, destPath, Objects.isNull(fontSize) ? defaultFontSize : fontSize.intValue());
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
                switch (val) {
                    case "☑":
                        // TODO
                        range.replaceText(key, val);
                        break;
                    case "□":
                        // TODO
                        range.replaceText(key, val);
                        break;
                    default:
                        range.replaceText(key, val);
                        break;
                }
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
    private static void dealDocx(String srcPath, Map<String, String> contentMap, String destPath, int fontSize) {
        try (
                InputStream in = new FileInputStream((new File(srcPath)));
                XWPFDocument document = new XWPFDocument(in);
                OutputStream outputStream = new FileOutputStream(destPath);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ) {
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
                        switch (val) {
                            case "☑":
                                run.setText("", 0);
                                run.getCTR().getSymList().add(getCtSym("Wingdings 2", String.format("%s%s", "F0", Integer.toHexString(82))));
                                break;
                            case "□":
                                run.setText("", 0);
                                run.getCTR().getSymList().add(getCtSym("Wingdings", String.format("%s%s", "F0", Integer.toHexString(111))));
                                break;
                            default:
                                run.setText(val, 0);
                                break;
                        }
                        run.setFontSize(fontSize);
                    }
                }
            });
            // 导出到文件
            document.write((OutputStream) byteArrayOutputStream);
            outputStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @param wingType
     * @param charStr
     * @return
     */
    private static CTSym getCtSym(String wingType, String charStr) {
        CTSym sym = null;
        try {
            sym = CTSym.Factory
                    .parse("<xml-fragment w:font=\""
                            + wingType
                            + "\" w:char=\""
                            + charStr
                            + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\"> </xml-fragment>");
        } catch (XmlException e) {
            log.error(e.getMessage(), e);
        }
        return sym;
    }

}
