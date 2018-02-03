package com.example.dada.material;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SectionDetailActivity extends AppCompatActivity
        implements SectionDetailCrawler.SectionDetailCrawlerListener {

    @BindView(R.id.tvSectionTitle)
    TextView tvSectionTitle;

    @BindView(R.id.tvSectionDetail)
    TextView tvSectionDetail;

    private String pptUrl = "";
    private String codeUrl = "";
    private String id;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_detail);
        ButterKnife.bind(this);

        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        title = bundle.getString("title");
        setTitle(title);
        Log.i("Section", id);

        SectionDetailCrawler crawler = new SectionDetailCrawler(id, this);
        crawler.crawl();
    }

    @OnClick(R.id.btnPPT)
    public void downloadPPT() {
        Uri uri = Uri.parse(pptUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.btnCode)
    public void downloadCode() {
        Uri uri = Uri.parse(codeUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void sectionDetailCrawlDone(String title, String detail,
                                       String pptUrl, String codeUrl) {
        tvSectionTitle.setText(title);
        tvSectionDetail.setText(detail);
        this.pptUrl = pptUrl;
        this.codeUrl = codeUrl;
    }
}
