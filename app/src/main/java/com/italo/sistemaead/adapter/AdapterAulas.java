package com.italo.sistemaead.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.italo.sistemaead.R;
import com.italo.sistemaead.model.Aulas;

import java.util.List;

public class AdapterAulas extends RecyclerView.Adapter<AdapterAulas.MyViewHolder> {

    private Context context;
    private List<Aulas> aulasLista;

    public AdapterAulas(Context context, List<Aulas> aulasLista) {

        this.context = context;
        this.aulasLista = aulasLista;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_aulas, parent, false);

        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Aulas aula = aulasLista.get(position);

        holder.titulo.setText(aula.getTituloAula());
        holder.url.setText(aula.getUrlAula());




    }


    @Override
    public int getItemCount() {

        return aulasLista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, url;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textViewTituloAulaAdapter);
            url = itemView.findViewById(R.id.textViewUrlAulaAdapter);
            progressBar = itemView.findViewById(R.id.progressBarAulas);
        }
    }
}
