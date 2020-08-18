package com.kongbig.element.demo;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * @description:
 * @author: lianggangda
 * @date: 2020/8/18 9:06
 */
public class FundDemo {

    @Test
    public void test1() throws Exception {
        String[] arr = new String[]{"003096", "003494", "004237", "163406", "163402", "110011", "001605", "519697"};
        for (int i = 0, len = arr.length; i < len; i++) {
            String fundCode = arr[i];
            String jsonStr = HttpUtil.get(String.format("http://fundgz.1234567.com.cn/js/%s.js?rt=1463558676006", fundCode));
            String json = jsonStr.replace("jsonpgz(", "").replace(");", "");
            System.out.println(json);

            Map map = JSONObject.parseObject(json, Map.class);
            String name = map.get("name").toString();

            String destPath = String.format("D:\\fund\\%s-%s.jpg", fundCode, name);
            URL url = new URL(String.format("http://j6.dfcfw.com/charts/StockPos/%s.png?rt=NaN", fundCode));
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpUrlConnection.getInputStream();
            new File(destPath);
            FileOutputStream fos = new FileOutputStream(destPath);

            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                fos.write(b, 0, length);
            }
            fos.close();
        }
    }

    public static void main(String[] args) throws Exception {
        String[] arr = new String[]{"003096", "003494", "004237", "163406", "163402", "110011", "001605", "519697"};
        for (int i = 0, len = arr.length; i < len; i++) {
            String fundCode = arr[i];
            System.out.println(getOne(fundCode));
        }
    }

    public static String getOne(String fundCode) throws Exception {
        StringBuilder sb = new StringBuilder();

        URL url = new URL(String.format("http://fundf10.eastmoney.com/FundArchivesDatas.aspx?type=jjcc&code=%s&topline=10&year=&month=&rt=0.03546340779834911", fundCode));
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpUrlConnection.getInputStream();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }

        String content = baos.toString().replace("var apidata={ content:\"", "");
        String htmlStr = content.substring(0, content.indexOf("\""));

        Document doc = Jsoup.parse(htmlStr);
        Element firstTbody = doc.getElementsByTag("table").first().getElementsByTag("tbody").first();
        Elements trs = firstTbody.getElementsByTag("tr");
        Iterator<Element> iterator = trs.iterator();
        while (iterator.hasNext()) {
            Element next = iterator.next();
            Element fundName = next.getElementsByClass("tol").first().getElementsByTag("a").first();
            Element ratio = next.getElementsByClass("tor").get(2);
            sb.append(fundName.text() + "(" + ratio.text() + ")" + "\t");
        }

        inputStream.close();
        baos.close();

        return sb.toString();
    }

}
