package com.example.dada.material;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by DaDa on 2/2/2018.
 */

public class CourseCrawler implements Runnable {
    private String courseId;
    private CourseCrawlerListener listener;

    private OkHttpClient client;
    private String contentUrl;
    private String courseUrl;
    private List<Section> sections;
    private List<BigSection> bigSections;
    private String baseUrl;
    private String moduleUrl;

    public CourseCrawler(String courseId, CourseCrawlerListener listener) {
        this.courseId = courseId;
        this.listener = listener;

        client = new OkHttpClient();
        baseUrl = "http://jinxuliang.com";
        contentUrl = baseUrl + "/openservice/api/courseservice/course/";
        courseUrl = baseUrl + "/course2/api/pptService/";
        moduleUrl = baseUrl + "/openservice/api/courseservice/module/";

        sections = new ArrayList<>();
        bigSections = new ArrayList<>();
    }

    public void crawl() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        String jsonString = getCourseContent(contentUrl + courseId);
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = (JSONObject) jsonArray.get(i);
                Section section = getBigTitle(obj);
                section.setBigTitleNum(i);
                sections.add(section);

                BigSection bigSection = new BigSection();
                bigSection.setId(section.getId());
                bigSection.setBigTitle(section.getTitle());

                String category = obj.getString("resourceCategory");
                if (category.equals("CourseModule")) {
                    int pos = sections.size();
                    recursive(obj.getString("_id"));
                    for (; pos < sections.size(); pos++) {
                        sections.get(pos).setBigTitleNum(i);
                        bigSection.addSection(sections.get(pos));
                    }

                } else if (category.equals("PPTResource")) {
                    section = getCourse(obj.getString("_id"));
                    section.setBigTitleNum(i);
                    sections.add(section);

                    bigSection.addSection(section);
                }

                bigSections.add(bigSection);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((Activity)listener).runOnUiThread(() -> {
            listener.crawlCourseDone(sections, bigSections);
        });
    }

    private String getCourseContent(String url) {
        Request request = new Request.Builder().url(url).build();
        String jsonString = "";
        try {
            Response response = client.newCall(request).execute();
            jsonString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    private Section getBigTitle(JSONObject obj) {
        Section section = new Section(1);
        try {
            String title = obj.getString("resourceName");

            String prefix = "《Android技术基础》";
            if (courseId.equals("5642fa1ac89f08ac304482d0") &&
                    title.startsWith(prefix)) {
                title = title.substring(prefix.length());
                if (title.charAt(0) == '/') {
                    title = title.substring(1);
                }
                if (title.charAt(0) == '《' &&
                        title.charAt(title.length() - 1) == '》') {
                    title = title.substring(1, title.length() - 1);
                }
            }
            section.setTitle(title);
            section.setId(obj.getString("_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return section;
    }

    private void recursive(String id) {
        String jsonString = httpGet(moduleUrl + id);
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                String category = obj.getString("resourceCategory");
                if (category.equals("CourseModule")) {
                    recursive(obj.getString("_id"));
                } else if (category.equals("PPTResource")) {
                    Section section = getCourse(obj.getString("_id"));
                    sections.add(section);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Section getCourse(String id) {
        String jsonString = httpGet(courseUrl + id);
        Section section = new Section(0);
        try {
            JSONObject obj = new JSONObject(jsonString);
            section.setId(obj.getString("_id"));
            section.setTitle(obj.getString("name"));
            String pptUrl = baseUrl + obj.getString("originalFileDownloadURL");
            section.setPptUrl(pptUrl);
            String codeUrl = baseUrl + obj.getString("sourceCodeDownloadURL");
            section.setCodeUrl(codeUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return section;
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

    public interface CourseCrawlerListener {
        void crawlCourseDone(List<Section> sections,
                             List<BigSection> bigSections);
    }
}










