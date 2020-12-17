package com.italo.sistemaead.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.italo.sistemaead.R;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.model.Cursos;

import java.util.List;

public class AdapterPesquisa extends RecyclerView.Adapter<AdapterPesquisa.MyViewHolder> {

    private Context context;
    private List<Cursos> cursosLista;

    public AdapterPesquisa(Context context, List<Cursos> cursosLista) {

        this.context = context;
        this.cursosLista = cursosLista;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pesquisa, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Cursos curso = cursosLista.get(position);

        holder.titulo.setText(curso.getTituloCurso());

        String id = curso.getIdCurso(); // -> Recuperando id do curso

        StorageReference imagemRef = ConfigFirebase.getFirebaseStorage()
                .child("Imagens")
                .child("CapaCursos")
                .child(id + ".JPEG");

        long MAXIMOBYTES = 1080 * 1080;

        imagemRef.getBytes(MAXIMOBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length); // -> Convertendo imagem para Bitmap
                holder.imagemCapaCurso.setImageBitmap(b);

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("LOG ERRO IMAGEM", e.getMessage());

            }

        });

    }


    @Override
    public int getItemCount() {
        return cursosLista.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imagemCapaCurso;
        TextView titulo;

        public MyViewHolder(View itemView) {
            super(itemView);

            imagemCapaCurso = itemView.findViewById(R.id.imageViewPesquisaAdapter);
            titulo = itemView.findViewById(R.id.textViewTituloCursoAdapterPesquisa);

        }
    }
}



