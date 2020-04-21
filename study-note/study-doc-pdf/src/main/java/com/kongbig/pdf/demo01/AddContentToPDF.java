package com.kongbig.pdf.demo01;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class AddContentToPDF {

    public static void main(String[] args) throws IOException, DocumentException {
        // 创建一个pdf读入流
        PdfReader reader = new PdfReader("D:\\dev\\test-itextpdf.pdf");
        // 根据一个pdfreader创建一个pdfStamper.用来生成新的pdf.
        PdfStamper stamper = new PdfStamper(reader,
                new FileOutputStream("D:\\dev\\test-itextpdf-replace.pdf"));

        // 这个字体是itext-asian.jar中自带的 所以不用考虑操作系统环境问题.
        BaseFont bf = BaseFont.createFont("STSong-Light",
                "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED); // set font
        // baseFont不支持字体样式设定.但是font字体要求操作系统支持此字体会带来移植问题.
        Font font = new Font(bf, 10);
        font.setStyle(Font.BOLD);
        font.getBaseFont();
        // 页数是从1开始的
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            // 获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上.
            PdfContentByte over = stamper.getOverContent(i);
            // 用pdfreader获得当前页字典对象.包含了该页的一些数据.比如该页的坐标轴信息.
            PdfDictionary p = reader.getPageN(i);
            // 拿到mediaBox 里面放着该页pdf的大小信息.
            PdfObject po = p.get(new PdfName("MediaBox"));
            System.out.println(po.isArray());
            // po是一个数组对象.里面包含了该页pdf的坐标轴范围.
            PdfArray pa = (PdfArray) po;
            System.out.println(pa.size());
            // 看看y轴的最大值.
            System.out.println(pa.getAsNumber(pa.size() - 1));
            // 开始写入文本
            over.beginText();
            // 设置字体和大小
            over.setFontAndSize(font.getBaseFont(), 10);
            // 设置字体颜色
            over.setColorFill(BaseColor.RED);
            // 设置字体的输出位置
            over.setTextMatrix(200, 750);
            // 要输出的text
            over.showText("kongbig " + i);
            over.endText();
            // 创建一个image对象.
            Image image = Image.getInstance("D:\\dev\\1.jpg");
            // 设置image对象的输出位置pa.getAsNumber(pa.size()-1).floatValue() 是该页pdf坐标轴的y轴的最大值
            image.setAbsolutePosition(0, pa.getAsNumber(pa.size() - 1).floatValue() - 100);//0, 0, 841.92, 595.32
            over.addImage(image);

            // 画一个圈.
            over.setRGBColorStroke(0xFF, 0x00, 0x00);
            over.setLineWidth(5f);
            over.ellipse(250, 450, 350, 550);
            over.stroke();
        }

        stamper.close();
    }
}