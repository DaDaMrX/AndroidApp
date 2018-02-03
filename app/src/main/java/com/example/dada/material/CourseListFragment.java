package com.example.dada.material;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CourseListFragment extends Fragment
        implements CourseListCrawler.Listener {

    @BindView(R.id.courses_recycler_view)
    RecyclerView coursesRecyclerView;

    private List<Course> courses;
    private CourseListAdapter mAdapter;

    private Context context;
    private Listener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        getActivity().setTitle("课程");
        coursesRecyclerView = view.findViewById(R.id.courses_recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        courses = new ArrayList<>();
        mAdapter = new CourseListAdapter(courses, this);
        coursesRecyclerView.setAdapter(mAdapter);

        coursesRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        coursesRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.ItemDecoration mDividerItemDecoration =
                new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
        coursesRecyclerView.addItemDecoration(mDividerItemDecoration);

        CourseListCrawler crawler = new CourseListCrawler(this);
        crawler.crawl();
    }

    public void onCourseClicked(String id, String title) {
        if (mListener != null) {
            Log.i("Fragment", "Clicked " + id);
            mListener.onCourseItemClicked(id, title);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void courseListCrawlDone(List<Course> courses) {
        this.courses = courses;
        ((Activity)mListener).runOnUiThread(() -> mAdapter.setmDataset(courses));
    }

    public interface Listener {
        void onCourseItemClicked(String id, String title);
    }
}
