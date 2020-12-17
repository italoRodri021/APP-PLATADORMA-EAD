package com.italo.sistemaead.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.italo.sistemaead.R;
import com.italo.sistemaead.fragments.AlunoFragment;
import com.italo.sistemaead.fragments.BibliotecaFragment;
import com.italo.sistemaead.fragments.CursosFragment;
import com.italo.sistemaead.fragments.PesquisaFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smartTabLayout = findViewById(R.id.smartTabLayout);
        viewPager = findViewById(R.id.viewpager);

        toolbar = findViewById(R.id.toobarMenu);

        setSupportActionBar(toolbar);

        FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Destaques", CursosFragment.class)
                .add("Pesquisar", PesquisaFragment.class)
                .add("Biblioteca", BibliotecaFragment.class)
                .add("Aluno", AlunoFragment.class)
                .create()
        );

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

    }


    /**
     * Configurando Toobar
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemAcaoMeusCursos: // -> Item abre MeusCursosActivity

                Intent i = new Intent(getApplicationContext(), MeusCursosActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.popenter, R.anim.popexit);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {

    }

}