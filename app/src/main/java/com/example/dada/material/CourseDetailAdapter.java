package com.example.dada.material;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class CourseDetailAdapter
        extends RecyclerView.Adapter<CourseDetailAdapter.ViewHolder> {

    private List<Section> sections;
    private List<BigSection> bigSections;
    private GoToDetailListener listener;

    public CourseDetailAdapter(List<Section> sections,
                               List<BigSection> bigSections,
                               GoToDetailListener listener) {
        this.sections = sections;
        this.bigSections = bigSections;
        this.listener = listener;
    }

    public void setSections(List<Section> sections,
                            List<BigSection> bigSections) {
        this.sections = sections;
        this.bigSections = bigSections;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        public TextView textView;
        private String id;

        private CourseDetailAdapter adapter;
        public List<Section> sections;
        public List<BigSection> bigSections;

        private boolean isExpanded;
        private int bigSectionNum;

        public ViewHolder(View v, int viewType, CourseDetailAdapter adapter) {
            super(v);
            this.adapter = adapter;
            this.isExpanded = true;
            this.bigSectionNum = -1;

            if (viewType == 0) {
                textView = v.findViewById(R.id.tvSectionDetail);
                layout = v.findViewById(R.id.layoutSectionDetail);
                layout.setOnClickListener((view) -> {
                    this.adapter.listener.goToDetail(id);
                });
            } else if (viewType == 1) {
                textView = v.findViewById(R.id.tvSectionTitle);
                layout = v.findViewById(R.id.layoutSectionTitle);
                layout.setOnClickListener((view) -> {
                    if (isExpanded) {
                        int pos = getAdapterPosition() + 1;
                        while (pos < sections.size() &&
                                sections.get(pos).getType() == 0) {
                            sections.remove(pos);
                        }
                        adapter.notifyDataSetChanged();
                        isExpanded = false;
                    } else {
                        List<Section> secs = bigSections.get(bigSectionNum).getSections();
                        int pos = getAdapterPosition();
                        sections.addAll(pos + 1, secs);
                        adapter.notifyDataSetChanged();
                        isExpanded = true;
                    }

                });
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == 0) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.section_detail_item, parent, false);
        } else if (viewType == 1) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.section_title_item, parent, false);
        }
        ViewHolder vh = new ViewHolder(v, viewType, this);
        vh.sections = sections;
        vh.bigSections = bigSections;
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Section section = sections.get(position);
        String title = section.getTitle();
        holder.textView.setText(title);
        holder.id = section.getId();
        holder.bigSectionNum = section.getBigTitleNum();



    }

    @Override
    public int getItemViewType(int position) {
        return sections.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }


    public interface GoToDetailListener {
        void goToDetail(String id);
    }

}
