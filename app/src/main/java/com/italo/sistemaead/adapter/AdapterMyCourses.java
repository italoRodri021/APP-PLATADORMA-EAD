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

public class AdapterMyCourses extends RecyclerView.Adapter<AdapterMyCourses.MyviewHolder> {

    private Context context;
    private List<Course> listCourse;

    public AdapterMyCourses(Context context, List<Course> listCourse) {

        this.context = context;
        this.listCourse = listCourse;

    }

    @Override
    public AdapterMyCourses.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_courses, parent, false);

        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterMyCourses.MyviewHolder holder, int position) {

        Course c = listCourse.get(position);

        holder.textTitle.setText(c.getTitle());
        Glide.with(context).load(c.getUrlPhoto()).into(holder.imagePhoto);


    }

    @Override
    public int getItemCount() {

        return listCourse.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        ImageView imagePhoto;
        TextView textTitle;

        public MyviewHolder(View itemView) {
            super(itemView);

            imagePhoto = itemView.findViewById(R.id.imageViewIconeCursoMeusCursos);
            textTitle = itemView.findViewById(R.id.textViewTituloCursoMeusCursos);

        }
    }
}

