package com.example.dada.material;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by DaDa on 2/2/2018.
 */

public class SectionDetailCrawler implements Runnable {

    private String id;
    private SectionDetailCrawlerListener listener;

    private OkHttpClient client;
    private String baseUrl;
    private String downloadUrl;
    private String detailUrl;
    private String detailUrlSufix;

    private String title;
    private String detail;
    private String pptUrl;
    private String codeUrl;

    public SectionDetailCrawler(String id, SectionDetailCrawlerListener listener) {
        this.id = id;
        this.listener = listener;

        this.client = new OkHttpClient();
        this.baseUrl = "http://jinxuliang.com";
        this.downloadUrl = this.baseUrl + "/course2/api/pptService/";
        this.detailUrl = this.baseUrl + "/openservice/api/courseservice/resource/";
        this.detailUrlSufix = "/description?needTxt=true";

        this.title = "";
        this.detail = "";
        this.pptUrl = "";
        this.codeUrl = "";
    }

    public void crawl() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        String jsonString = httpGet(downloadUrl + id);
        try {
            JSONObject obj = new JSONObject(jsonString);
            title = obj.getString("name");
            pptUrl = baseUrl + obj.getString("originalFileDownloadURL");
            codeUrl = baseUrl + obj.getString("sourceCodeDownloadURL");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        detail = httpGet(detailUrl + id + detailUrlSufix);

        Log.i("SectionDetailCrawler", title);
        Log.i("SectionDetailCrawler", detail);
        Log.i("SectionDetailCrawler", pptUrl);
        Log.i("SectionDetailCrawler", codeUrl);

        ((Activity)listener).runOnUiThread(() -> {
            listener.sectionDetailCrawlDone(title, detail, pptUrl, codeUrl);
        });
    }

    private String httpGet(String url) {
        Request request = new Request.Builder().url(url).build();
        String result = "";
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public interface SectionDetailCrawlerListener {
        void sectionDetailCrawlDone(String title, String detail,
                                    String pptUrl, String codeUrl);
    }
}
