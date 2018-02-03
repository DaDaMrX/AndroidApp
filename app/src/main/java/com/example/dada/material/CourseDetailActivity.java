package com.example.dada.material;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseDetailActivity extends AppCompatActivity
        implements CourseCrawler.CourseCrawlerListener,
        CourseDetailAdapter.GoToDetailListener{

    @BindView(R.id.course_detail_recycler_view)
    RecyclerView courseDetailRecyclerView;

    private List<Section> sections;
    private List<BigSection> bigSections;
    private CourseDetailAdapter adapter;

    private String id;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);

        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        title = bundle.getString("title");
        setTitle(title);
        Log.i("CourseDetailActivity", "Get " + id);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        courseDetailRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.ItemDecoration mDividerItemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        courseDetailRecyclerView.addItemDecoration(mDividerItemDecoration);

        sections = new ArrayList<>();
        bigSections = new ArrayList<>();
        adapter = new CourseDetailAdapter(sections, bigSections, this);
        courseDetailRecyclerView.setAdapter(adapter);

        new CourseCrawler(id, this).crawl();
    }


    @Override
    public void crawlCourseDone(List<Section> sections,
                                List<BigSection> bigSections) {
        this.sections = sections;
        this.bigSections = bigSections;
        adapter.setSections(sections, bigSections);
    }

    @Override
    public void goToDetail(String id) {
        Intent intent = new Intent(this, SectionDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("title", title);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
