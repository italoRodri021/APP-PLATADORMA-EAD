package com.italo.sistemaead.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.italo.sistemaead.R;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.IdUser;
import com.italo.sistemaead.config.Permissao;
import com.italo.sistemaead.model.Usuario;

import java.io.ByteArrayOutputStream;

public class EditarPerfilActivity extends AppCompatActivity {

    private static final int SELECAO_CAMERA = 200;
    private static final int SELECAO_GALERIA = 100;
    private ImageView imagemFotoAluno;
    private EditText campoNome, campoMatricula, campoEmail;
    private Button botaoCamera, botaoGaleria, botaoSalvar;
    private DatabaseReference databaseRef;
    private StorageReference storageRef;
    private FirebaseAuth autenticacao;
    private ProgressBar progressBar;
    private String idUsuario;
    private Toolbar toolbar;
    private final String[] permissoesNecessarias = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        iniComponentes();
        setSupportActionBar(toolbar);
        configInterface();
        getImagem();

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() { // -> Recuperando dados
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                    campoNome.setText(usuario.getNome()); // Configurando texto nos campos
                    campoMatricula.setText(usuario.getMatricula());
                    campoEmail.setText(usuario.getEmail());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", databaseError.getMessage());
            }
        });

    }

    public void configInterface() {

        botaoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent abrirCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // -> Chamando intent do android

                if (abrirCamera.resolveActivity(getPackageManager()) != null) { // -> Verificando se o dispositivo possui uma camera
                    startActivityForResult(abrirCamera, SELECAO_CAMERA);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }

            }
        });

        botaoGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent abrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // -> Chamando intent do android

                if (abrirGaleria.resolveActivity(getPackageManager()) != null) { // -> Verificando se o dispositivo possui um software de galeria
                    startActivityForResult(abrirGaleria, SELECAO_GALERIA);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Bitmap imagem = null;


            try {

                switch (requestCode) {
                    case SELECAO_CAMERA:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        imagemFotoAluno.setImageBitmap(imagem);
                        break;
                }


                if (imagem != null) {

                    imagemFotoAluno.setImageBitmap(imagem);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imagemBytes = baos.toByteArray();

                    salvandoDadosUploadImagem(imagemBytes);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void salvandoDadosUploadImagem(final byte[] imagemBytes) {

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                StorageReference imagemRef = storageRef.child(idUsuario + ".JPEG");
                UploadTask uploadImagem = imagemRef.putBytes(imagemBytes);

                uploadImagem.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        toast("Dados atualizados com sucesso!");
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        progressBar.setVisibility(View.GONE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        toast("Ops! Erro inesperado. Verifique a conexão com a internet.");
                        progressBar.setVisibility(View.GONE);
                        Log.d("ERRO UPLOAD IMAGEM", e.getMessage());
                    }
                });

            }
        });


    }

    public void getImagem() {

        StorageReference imagemRef = storageRef
                .child(idUsuario + ".JPEG");

        long MAXIMOBYTES = 1080 * 1080;

        imagemRef.getBytes(MAXIMOBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imagemFotoAluno.setImageBitmap(b);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("LOG ERRO", e.getMessage());

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoUsuario : grantResults) { // -> Verificando se a permissao foi negada

            if (permissaoUsuario == PackageManager.PERMISSION_DENIED) {

                toast("Aceite as permissões do app primeiro!");
                finish();

            } else {
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemMenuSair:

                autenticacao.signOut();
                Intent i = new Intent(getApplicationContext(), SplashActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.popenter, R.anim.popexit);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void toast(String mensagem) {

        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

    }

    public void iniComponentes() {

        toolbar = findViewById(R.id.toobarEditarPerfil);
        imagemFotoAluno = findViewById(R.id.imageViewEditarPerfil);
        botaoSalvar = findViewById(R.id.btnSalvarEditarPerfil);
        botaoCamera = findViewById(R.id.btnCameraEditarPerfil);
        botaoGaleria = findViewById(R.id.btnGaleriaEditarPerfil);
        campoNome = findViewById(R.id.editTextNomeEditarPerfil);
        campoMatricula = findViewById(R.id.editTextMatriculaEditarPerfil);
        campoEmail = findViewById(R.id.editTextCampoEmailEditarPerfil);
        progressBar = findViewById(R.id.progressBarEditarPerfil);

        Permissao.validarPermissoes(permissoesNecessarias, EditarPerfilActivity.this, 1);

        idUsuario = IdUser.getIdUser();
        autenticacao = ConfigFirebase.getFirebaseAuth();
        databaseRef = ConfigFirebase.getFirebaseDatabase().child("Usuarios").child("Perfil").child(idUsuario);
        storageRef = ConfigFirebase.getFirebaseStorage().child("Usuarios").child("Perfil");


    }

}

