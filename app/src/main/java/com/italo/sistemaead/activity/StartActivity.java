package com.italo.sistemaead.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

import com.italo.sistemaead.R;
import com.italo.sistemaead.fragment.LoginFragment;
import com.italo.sistemaead.fragment.RegisterFragment;

public class StartActivity extends AppCompatActivity {

    private LoginFragment loginFrag;
    private RegisterFragment registerFrag;
    private Button btnContinue, btnRegister;
    private FrameLayout frameContentStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnContinue = findViewById(R.id.btnContinuarIniciar);
        btnRegister = findViewById(R.id.btnFrazerCadastro);
        frameContentStart = findViewById(R.id.frameConteudoInicio);

        configInterface();
    }

    public void configInterface(){

        final ViewGroup root = findViewById(R.id.frameConteudoInicio);

        btnContinue.setOnClickListener((v) -> {

            TransitionManager.beginDelayedTransition(root, new Slide());

            if (frameContentStart.getVisibility() == View.INVISIBLE){
                frameContentStart.setVisibility(View.VISIBLE);
                btnContinue.setText("VOLTAR");
            }else {
                frameContentStart.setVisibility(View.INVISIBLE);
                btnContinue.setText("CONTINUAR");
            }

            loginFrag = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.popenter, R.anim.popexit)
                    .add(R.id.frameConteudoInicio, loginFrag)
                    .addToBackStack(null);
            transaction.commit();

        });

        btnRegister.setOnClickListener((v) -> {

            TransitionManager.beginDelayedTransition(root, new Slide());

            if (frameContentStart.getVisibility() == View.INVISIBLE){
                frameContentStart.setVisibility(View.VISIBLE);
                btnContinue.setText("VOLTAR");

            }else {
                frameContentStart.setVisibility(View.INVISIBLE);
                btnContinue.setText("CONTINUAR");

            }

            registerFrag = new RegisterFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.popenter, R.anim.popexit)
                    .add(R.id.frameConteudoInicio, registerFrag)
                    .addToBackStack(null);
            transaction.commit();

        });

    }

    @Override
    public void onBackPressed() {

    }
}