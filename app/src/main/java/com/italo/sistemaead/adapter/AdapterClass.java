package com.italo.sistemaead.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.italo.sistemaead.R;
import com.italo.sistemaead.model.Class;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {

    private Context context;
    private List<Class> listClass;

    public AdapterClass(Context context, List<Class> listClass) {

        this.context = context;
        this.listClass = listClass;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_class, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Class c = listClass.get(position);
        holder.textTitle.setText(c.getTitle());

    }


    @Override
    public int getItemCount() {

        return listClass.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textViewTituloAulaAdapter);
            progressBar = itemView.findViewById(R.id.progressBarAulas);
        }
    }
}
