package com.example.dada.material;

import android.webkit.WebView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


public class Index implements Runnable {
    WebView webView;

    public Index(WebView webView) {
        this.webView = webView;
    }

    @Override
    public void run() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Future<String> result = executor.submit(new IndexCrawler());
        String html = null;
        try {
            html = result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String finalHtml = html;
        webView.post(() -> {
            webView.loadData(finalHtml, "text/html; charset=UTF-8", null);
        });
    }
}


