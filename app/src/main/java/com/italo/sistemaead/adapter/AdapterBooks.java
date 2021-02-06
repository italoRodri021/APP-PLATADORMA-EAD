package com.italo.sistemaead.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.italo.sistemaead.R;
import com.italo.sistemaead.model.Books;

import java.util.List;

public class AdapterBooks extends RecyclerView.Adapter<AdapterBooks.MyViewHolder> {

    private Context context;
    private List<Books> listBooks;

    public AdapterBooks(Context context, List<Books> listBooks){

        this.context = context;
        this.listBooks = listBooks;

    }

    @Override
    public AdapterBooks.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_books,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterBooks.MyViewHolder holder, int position) {

        Books livros = listBooks.get(position);

        holder.textTitle.setText(livros.getTituloLivro());



    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        ImageView textPhoto;

        public MyViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textViewTituloLivroAdapter);
            textPhoto = itemView.findViewById(R.id.imageViewCapaLivroAdapter);

        }


    }


}
