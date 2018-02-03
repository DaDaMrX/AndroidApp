package com.example.dada.material;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DaDa on 1/31/2018.
 */

class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {
    private List<Course> mDataset;
    CourseListFragment fragment;

    public CourseListAdapter(List<Course> myDataset,
                             CourseListFragment fragment) {
        this.mDataset = myDataset;
        this.fragment = fragment;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tvCourseTitle;
        public TextView tvCourseDescription;
        public ConstraintLayout course_item;
        public String id;
        CourseListFragment fragment;

        public ViewHolder(View v, CourseListFragment fragment) {
            super(v);
            this.fragment = fragment;
            imageView = (ImageView) v.findViewById(R.id.imageView);
            tvCourseTitle = (TextView) v.findViewById(R.id.tvCourseTitle);
            tvCourseDescription = (TextView) v.findViewById(R.id.tvCourseDescription);
            course_item = (ConstraintLayout) v.findViewById(R.id.course_item);
            course_item.setOnClickListener((view) -> {
                Log.i("ViewHolder", "Clicked " + id);
                String title = tvCourseTitle.getText().toString();
                fragment.onCourseClicked(id, title);
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        ViewHolder vh = new ViewHolder(v, fragment);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = mDataset.get(position);
        holder.id = course.getId();
        holder.tvCourseTitle.setText(course.getTitle());
        List<String> descriptions = course.getDescriptions();
        StringBuffer description = new StringBuffer();
        for (String desc : descriptions) {
            description.append("- " + desc + '\n');
        }
        description.deleteCharAt(description.length() - 1);
        setImage(holder.imageView, course.getId());
        holder.tvCourseDescription.setText(description.toString());

        if (holder.id.equals("5443ab09137e4302288eed66")) {
            holder.tvCourseTitle.setText("面向对象软件开发实践");
        }
    }

    private void setImage(ImageView imageView, String id) {
        switch (id) {
            case "54004d84137e45731c99035b":
                imageView.setImageResource(R.mipmap.java);
                break;
            case "5642fa1ac89f08ac304482d0":
                imageView.setImageResource(R.mipmap.android);
                break;
            case "543b979c137e481e6cbdb267":
                imageView.setImageResource(R.mipmap.csharp);
                break;
            case "5687703ec89f0500f01f240a":
                imageView.setImageResource(R.mipmap.computer);
                break;
            case "5443ab09137e4302288eed66":
                imageView.setImageResource(R.mipmap.oop);
                break;
            case "56d2ae2dc89f052bd4386224":
                imageView.setImageResource(R.mipmap.web);
                break;
        }
    }

    public void setmDataset(List<Course> courses) {
        this.mDataset = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
