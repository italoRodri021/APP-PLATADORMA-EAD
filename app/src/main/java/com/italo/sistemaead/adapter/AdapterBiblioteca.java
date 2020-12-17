package com.italo.sistemaead.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.italo.sistemaead.R;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.model.Livros;

import java.util.List;

public class AdapterBiblioteca extends RecyclerView.Adapter<AdapterBiblioteca.MyViewHolder> {

    private Context context;
    private List<Livros> livrosLista;

    public AdapterBiblioteca(Context context, List<Livros> livrosLista){

        this.context = context;
        this.livrosLista = livrosLista;

    }

    @Override
    public AdapterBiblioteca.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_livros,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterBiblioteca.MyViewHolder holder, int position) {

        Livros livros = livrosLista.get(position);

        holder.titulo.setText(livros.getTituloLivro());

        String id = livros.getIdLivro();
        StorageReference imagemRef = ConfigFirebase.getFirebaseStorage()
                .child("Biblioteca")
                .child("Livros")
                .child(id + ".JPEG");

        long MAXIMOBYTES = 1080 * 1080;

        imagemRef.getBytes(MAXIMOBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap b = BitmapFactory.decodeByteArray(bytes, 0,bytes.length);
                holder.imagemCapaLivro.setImageBitmap(b);

            }
        });

    }

    @Override
    public int getItemCount() {
        return livrosLista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        ImageView imagemCapaLivro;

        public MyViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textViewTituloLivroAdapter);
            imagemCapaLivro = itemView.findViewById(R.id.imageViewCapaLivroAdapter);

        }


    }


}
