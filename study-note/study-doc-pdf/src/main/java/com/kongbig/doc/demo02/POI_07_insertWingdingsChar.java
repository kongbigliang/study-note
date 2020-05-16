package com.kongbig.doc.demo02;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSym;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

public class POI_07_insertWingdingsChar {

    /**
     * https://www.iteye.com/blog/53873039oycg-2194564
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        POI_07_insertWingdingsChar t = new POI_07_insertWingdingsChar();
        t.testWingdings();
    }

    public void testWingdings() throws Exception {
        XWPFDocument xdoc = new XWPFDocument();
        XWPFParagraph p = xdoc.createParagraph();
        XWPFRun pRun = getOrAddParagraphFirstRun(p, false, false);
        CTRPr pRpr = getRunCTRPr(p, pRun);
        // 设置字体大小  
        CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger("26"));

        CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr
                .addNewSzCs();
        szCs.setVal(new BigInteger("26"));

        pRun.setText("-------------------------------Wingdings字符---------------------------------------");
        pRun.addBreak();
        pRun = getOrAddParagraphFirstRun(p, true, false);
        pRpr = getRunCTRPr(p, pRun);
        // 设置字体大小  
        sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger("26"));

        szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
        szCs.setVal(new BigInteger("26"));
        // 32-255  
        List<CTSym> symList = pRun.getCTR().getSymList();
        for (int i = 32; i <= 255; i++) {
            symList.add(getCTSym("Wingdings", "F0" + Integer.toHexString(i)));
            pRun.setText("  [字符编码:" + i + "]  ");
        }

        pRun.addBreak(BreakType.PAGE);
        p = xdoc.createParagraph();
        pRun = getOrAddParagraphFirstRun(p, false, false);
        pRpr = getRunCTRPr(p, pRun);
        // 设置字体大小  
        sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger("26"));

        szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
        szCs.setVal(new BigInteger("26"));
        pRun.setText("-------------------------------Wingdings 2字符---------------------------------------");
        pRun.addBreak();
        pRun = getOrAddParagraphFirstRun(p, true, false);
        pRpr = getRunCTRPr(p, pRun);
        // 设置字体大小  
        sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger("26"));

        szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
        szCs.setVal(new BigInteger("26"));
        // 32-255  
        symList = pRun.getCTR().getSymList();
        for (int i = 32; i <= 255; i++) {
            symList.add(getCTSym("Wingdings 2", "F0" + Integer.toHexString(i)));
            pRun.setText("  [字符编码:" + i + "]  ");
        }

        pRun.addBreak(BreakType.PAGE);
        p = xdoc.createParagraph();
        pRun = getOrAddParagraphFirstRun(p, false, false);
        pRpr = getRunCTRPr(p, pRun);
        // 设置字体大小  
        sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger("26"));

        szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
        szCs.setVal(new BigInteger("26"));
        pRun.setText("-------------------------------Wingdings 3字符---------------------------------------");
        pRun.addBreak();
        pRun = getOrAddParagraphFirstRun(p, true, false);
        pRpr = getRunCTRPr(p, pRun);
        // 设置字体大小  
        sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger("26"));

        szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
        szCs.setVal(new BigInteger("26"));
        // 32-255  
        symList = pRun.getCTR().getSymList();
        for (int i = 32; i <= 255; i++) {
            symList.add(getCTSym("Wingdings 3", "F0" + Integer.toHexString(i)));
            pRun.setText("  [字符编码:" + i + "]  ");
        }
        saveDocument(xdoc, "C:/Users/Administrator/Desktop/" + System.currentTimeMillis() + ".docx");
    }

    public CTSym getCTSym(String wingType, String charStr) throws Exception {
        CTSym sym = CTSym.Factory
                .parse("<xml-fragment w:font=\""
                        + wingType
                        + "\" w:char=\""
                        + charStr
                        + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\"> </xml-fragment>");
        return sym;
    }

    /**
     * @Description: 得到XWPFRun的CTRPr
     */
    public CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
        CTRPr pRpr = null;
        if (pRun.getCTR() != null) {
            pRpr = pRun.getCTR().getRPr();
            if (pRpr == null) {
                pRpr = pRun.getCTR().addNewRPr();
            }
        } else {
            pRpr = p.getCTP().addNewR().addNewRPr();
        }
        return pRpr;
    }

    public void saveDocument(XWPFDocument document, String savePath)
            throws Exception {
        FileOutputStream fos = new FileOutputStream(savePath);
        document.write(fos);
        fos.close();
    }

    public XWPFRun getOrAddParagraphFirstRun(XWPFParagraph p, boolean isInsert,
                                             boolean isNewLine) {
        XWPFRun pRun = null;
        if (isInsert) {
            pRun = p.createRun();
        } else {
            List<XWPFRun> runs = p.getRuns();
            if (CollectionUtils.isNotEmpty(runs)) {
                pRun = runs.get(0);
            } else {
                pRun = p.createRun();
            }
        }
        if (isNewLine) {
            pRun.addBreak();
        }
        return pRun;
    }
}  