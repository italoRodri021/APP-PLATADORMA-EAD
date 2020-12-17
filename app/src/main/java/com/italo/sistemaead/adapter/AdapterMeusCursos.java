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

public class AdapterMeusCursos extends RecyclerView.Adapter<AdapterMeusCursos.MyviewHolder> {

    private Context context;
    private List<Cursos> meusCursosLista;

    public AdapterMeusCursos(Context context, List<Cursos> meusCursosLista) {

        this.context = context;
        this.meusCursosLista = meusCursosLista;

    }


    @Override
    public AdapterMeusCursos.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_meus_cursos, parent, false);

        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterMeusCursos.MyviewHolder holder, int position) {

        Cursos curso = meusCursosLista.get(position);

        holder.titulo.setText(curso.getTituloCurso());
        holder.idCurso.setText(curso.getIdCurso());

        String id = curso.getIdCurso(); // -> Recuperando icone do curso

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

        return meusCursosLista.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        ImageView imagemCapaCurso;
        TextView titulo, idCurso;

        public MyviewHolder(View itemView) {
            super(itemView);

            imagemCapaCurso = itemView.findViewById(R.id.imageViewIconeCursoMeusCursos);
            titulo = itemView.findViewById(R.id.textViewTituloCursoMeusCursos);
            idCurso = itemView.findViewById(R.id.textViewIdCursoMeusCursos);

        }
    }
}

