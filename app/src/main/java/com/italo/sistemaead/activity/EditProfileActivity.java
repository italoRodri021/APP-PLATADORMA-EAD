package com.italo.sistemaead.activity;

import android.content.Intent;
import android.graphics.Bitmap;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.italo.sistemaead.R;
import com.italo.sistemaead.config.ConfigFirebase;
import com.italo.sistemaead.config.Permissions;
import com.italo.sistemaead.config.UserFirebase;
import com.italo.sistemaead.model.User;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends AppCompatActivity {

    private static final int CAMERA = 200;
    private static final int GALERY = 100;
    private ImageView imagePhoto;
    private EditText editName, editRegistration, editEmail;
    private Button btnCamera, btnGalery, btnSave;
    private DatabaseReference database;
    private StorageReference storage;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private String idCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        iniComponents();
        getDataUser();
        configInterface();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Bitmap imageBitmap = null;

            try {

                switch (requestCode) {
                    case CAMERA:
                        imageBitmap = (Bitmap) data.getExtras().get("data");
                        break;
                    case GALERY:
                        Uri URL = data.getData();
                        imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), URL);
                        imagePhoto.setImageBitmap(imageBitmap);
                        break;
                }


                if (imageBitmap != null) {

                    imagePhoto.setImageBitmap(imageBitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                    byte[] imagemBytes = baos.toByteArray();

                    uploadImage(imagemBytes);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getDataUser() {

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    User u = snapshot.getValue(User.class);

                    editName.setText(u.getName()); // Configurando texto nos campos
                    editRegistration.setText(u.getRegistration());
                    editEmail.setText(u.getEmail());

                    if (u.getUrlPhoto() != null) {
                        Glide.with(getApplicationContext()).load(u.getUrlPhoto()).into(imagePhoto);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ERROR", error.getMessage());
            }
        });

    }

    public void configInterface() {

        btnCamera.setOnClickListener(view -> {

            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(i, CAMERA);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }

        });

        btnGalery.setOnClickListener(view -> {

            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(i, GALERY);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }

        });


    }

    public void uploadImage(final byte[] imagemBytes) {

        btnSave.setOnClickListener(view -> {

            progressBar.setVisibility(View.VISIBLE);
            StorageReference imageRef = storage.child(idCurrentUser + ".JPEG");
            UploadTask upload = imageRef.putBytes(imagemBytes);

            upload.addOnSuccessListener((taskSnapshot) -> {

                imageRef.getDownloadUrl().addOnCompleteListener((task) -> {

                    String url = task.getResult().toString();

                    User u = new User();
                    u.setIdUser(idCurrentUser);
                    u.setUrlPhoto(url);
                    u.updatePhotoUser();

                });
            });
        });
    }

    public void iniComponents() {

        Toolbar toolbar = findViewById(R.id.toobarEditarPerfil);
        imagePhoto = findViewById(R.id.imageViewEditarPerfil);
        btnSave = findViewById(R.id.btnSalvarEditarPerfil);
        btnCamera = findViewById(R.id.btnCameraEditarPerfil);
        btnGalery = findViewById(R.id.btnGaleriaEditarPerfil);
        editName = findViewById(R.id.editTextNomeEditarPerfil);
        editRegistration = findViewById(R.id.editTextMatriculaEditarPerfil);
        editEmail = findViewById(R.id.editTextCampoEmailEditarPerfil);
        progressBar = findViewById(R.id.progressBarEditarPerfil);
        setSupportActionBar(toolbar);

        Permissions.validatePermissions(Permissions.getPermissionsUser, EditProfileActivity.this, 1);

        idCurrentUser = UserFirebase.getIdUser();
        auth = ConfigFirebase.getFirebaseAuth();
        database = ConfigFirebase.getFirebaseDatabase().child("User").child("Profile").child(idCurrentUser);
        storage = ConfigFirebase.getFirebaseStorage().child("User").child("Profile");

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

                auth.signOut();
                Intent i = new Intent(getApplicationContext(), SplashActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.popenter, R.anim.popexit);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}

