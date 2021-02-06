package com.italo.sistemaead.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.italo.sistemaead.R;
import com.italo.sistemaead.model.Course;

import java.util.List;

public class AdapterCourses extends RecyclerView.Adapter<AdapterCourses.MyViewHolder> {

    private Context context;
    private List<Course> listCourse;

    public AdapterCourses(Context context, List<Course> listCourse) {

        this.context = context;
        this.listCourse = listCourse;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_courses, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Course c = listCourse.get(position);

        holder.textTitle.setText(c.getTitle());
        holder.textDescription.setText(c.getDescription());
        Glide.with(context).load(c.getUrlPhoto()).into(holder.imagePhoto);

    }

    @Override
    public int getItemCount() {

        return listCourse.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imagePhoto;
        TextView textTitle, textDescription;

        public MyViewHolder(View itemView) {
            super(itemView);

            imagePhoto = itemView.findViewById(R.id.imageViewCapaCursoAdapter);
            textTitle = itemView.findViewById(R.id.textViewTituloCursoAdapter);
            textDescription = itemView.findViewById(R.id.textViewDescricaoCursoAdapter);

        }


    }


}
