package com.kongbig.doc.demo01;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @Description: jacob将word文档转pdf
 * @author: lianggangda
 * @date: 2020/4/20 11:35
 */
@Slf4j
public class JacobToPdf {

    public static void main(String[] args) {
        String wordPath = "C:\\Users\\Administrator\\Desktop\\book\\淘宝销售授权书1-copy-replace.docx";
        String pdfPath = "C:\\Users\\Administrator\\Desktop\\book\\淘宝销售授权书1-copy-replace.pdf";
        JacobToPdf.wordToPdf(wordPath, pdfPath);
    }

    /**
     * @param wordPath word 的路径
     * @param pdfPath  pdf 的路径
     */
    public static void wordToPdf(String wordPath, String pdfPath) {
        ActiveXComponent app = null;
        try {
            // 打开word
            app = new ActiveXComponent("Word.Application");
            // 获得word中所有打开的文档
            Dispatch documents = app.getProperty("Documents").toDispatch();
            System.out.println("打开文件: " + wordPath);
            // 打开文档
            Dispatch document = Dispatch.call(documents, "Open", wordPath, false, true).toDispatch();
            // 如果文件存在的话，不会覆盖，会直接报错，所以我们需要判断文件是否存在
            File target = new File(pdfPath);
            if (target.exists()) {
                target.delete();
            }
            System.out.println("另存为: " + pdfPath);
            Dispatch.call(document, "SaveAs", pdfPath, 17);
            // 关闭文档
            Dispatch.call(document, "Close", false);
        } catch (Exception e) {
            log.error("转换失: {}", e.getMessage(), e);
        } finally {
            // 关闭office
            app.invoke("Quit", 0);
        }
    }

}
