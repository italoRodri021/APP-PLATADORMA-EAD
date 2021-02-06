package com.italo.sistemaead.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.italo.sistemaead.R;
import com.italo.sistemaead.fragment.CourseFragment;
import com.italo.sistemaead.fragment.SearchFragment;
import com.italo.sistemaead.fragment.StudentFragment;
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
                .add("Destaques", CourseFragment.class)
                .add("Pesquisar", SearchFragment.class)
                //.add("Biblioteca", BooksFragment.class)
                .add("Aluno", StudentFragment.class)
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

                Intent i = new Intent(getApplicationContext(), MyCoursesActivity.class);
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