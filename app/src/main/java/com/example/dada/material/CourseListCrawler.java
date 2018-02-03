package com.example.dada.material;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by DaDa on 2/2/2018.
 */

public class CourseListCrawler implements Runnable {
    private Listener listener;
    private List<Course> courses;
    private OkHttpClient client;
    private String url;


    public CourseListCrawler(Listener listener) {
        this.listener = listener;
        courses = new ArrayList<>();
        client = new OkHttpClient();
        this.url = "http://jinxuliang.com/mainwebsite";
    }

    public void crawl() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        String html = httpGet(url);
        extract(html);

        for (int i = 0; i < courses.size(); i++) {
            Log.i("Course", courses.get(i).toString());
        }

        listener.courseListCrawlDone(courses);
    }

    private void extract(String html) {
        Document doc = Jsoup.parse(html);
        Elements list = doc.select("div.col-md-4");
        for (int i = 0; i < list.size(); i++) {
            Course course = new Course();
            Element element = list.get(i);

            String title = element.select("strong").first().text();
            course.setTitle(title);

            Elements lis = element.select("li");
            for (int j = 0; j < lis.size(); j++) {
                String description = lis.get(j).text();
                course.addDescription(description);
            }

            Element panel = element.select("div.panel-primary").first();
            String s[] = panel.attr("ng-click").split("/");
            String id = s[s.length - 1];
            id = id.substring(0, id.length() - 2);
            course.setId(id);

            courses.add(course);
        }
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

    public interface Listener {
        void courseListCrawlDone(List<Course> courses);
    }
}
