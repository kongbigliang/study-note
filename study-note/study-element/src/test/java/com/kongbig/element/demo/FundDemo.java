package com.kongbig.element.demo;

import cn.hutool.core.lang.Tuple;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @description:
 * @author: lianggangda
 * @date: 2020/8/18 9:06
 */
public class FundDemo {

    public static void main(String[] args) throws Exception {
        Map<String, Integer> top = new HashMap<>();
        List<String> arr = new ArrayList<>(Arrays.asList("161005", "163402", "163406", "110011", "166002", "007120", "005827", "519066"));
        List<String> arr2 = new ArrayList<>(Arrays.asList("008860", "008655", "008145", "004890", "009086", "519674", "001605"));
//        arr.addAll(arr2);
        for (int i = 0, len = arr.size(); i < len; i++) {
            String fundCode = arr.get(i);
            Tuple tuple = getOne(fundCode);
            Set<String> set = (Set<String>) tuple.getMembers()[0];
            for (String s : set) {
                if (top.containsKey(s)) {
                    top.put(s, top.get(s) + 1);
                } else {
                    top.put(s, 1);
                }
            }
            String result = tuple.getMembers()[1].toString();
            System.out.println(result);
        }

        System.out.println("========================================================================================");
        int i = 1;
        for (Map.Entry<String, Integer> entry : top.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.print(entry.getKey() + "=" + entry.getValue() + "个\t");
                ++i;
                if (i == 11) {
                    System.out.println();
                }
            }
        }
    }

    private static Tuple getOne(String fundCode) throws Exception {
        Set<String> set = new HashSet<>();

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
        Element fundNameA = doc.getElementsByTag("h4").first().getElementsByTag("a").first();
        String fundName = fundNameA.text();
        Element firstTbody = doc.getElementsByTag("table").first().getElementsByTag("tbody").first();
        Elements trs = firstTbody.getElementsByTag("tr");
        Iterator<Element> iterator = trs.iterator();
        while (iterator.hasNext()) {
            Element next = iterator.next();
            Element stockName = next.getElementsByClass("tol").first().getElementsByTag("a").first();
            Element ratio = next.getElementsByClass("tor").get(2);
            sb.append(stockName.text() + "(" + ratio.text() + ")" + "\t");
            set.add(stockName.text());
        }

        inputStream.close();
        baos.close();

        return new Tuple(set, fundName + "\t\t" + sb.toString());
    }

}
