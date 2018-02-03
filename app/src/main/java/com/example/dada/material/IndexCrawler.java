package com.example.dada.material;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class IndexCrawler implements Callable<String> {

    @Override
    public String call() throws Exception {
        OkHttpClient client = new OkHttpClient();
        String urlIndex = "http://jinxuliang.com/mainwebsite";
        Request request = new Request.Builder().url(urlIndex).build();
        String html = "";
        try {
            Response response = client.newCall(request).execute();
            html = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(html);

        Element p = doc.body().select(".body-content >" +
                " div.container > div.bg-danger > p").get(5);
        p.html("");

        StringBuilder result = new StringBuilder();
        Elements links = doc.select("link");
        for (Element link : links) {
            result.append(link.outerHtml());
        }
        Elements wells = doc.select("div.well");
        for (Element well : wells) {
            result.append(well.outerHtml());
        }
        return result.toString();
    }
}
