package si.lj.uni.fri.tpo.fripredmeti;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import si.lj.uni.fri.tpo.fripredmeti.Fragments.AreasFragment;
import si.lj.uni.fri.tpo.fripredmeti.Fragments.SearchFragment;

public class Areas extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, AreasFragment.OnFragmentInteractionListener, SearchView.OnQueryTextListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar2));
        getSupportActionBar().setTitle("Pregled področij");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        if(getIntent().getBooleanExtra("login", false))
            Snackbar.make(findViewById(R.id.activity_main), "Pozdravljen " + getIntent().getStringExtra("user"), Snackbar.LENGTH_SHORT).show();



        AreasFragment newFragment = new AreasFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment, "AREAS");
        //transaction.addToBackStack(null);
        transaction.commit();

    }

    /*
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        FrameLayout spinner = (FrameLayout) findViewById(R.id.progress);
        spinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }


    public void startMainActivity(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri){}

    public boolean onQueryTextChange(String newText)
    {
        if (newText.length() > 0)
        {
            SearchFragment newFragment = new SearchFragment();
            Bundle args = new Bundle();
            args.putString("query", newText);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment, "SEARCH");
            //transaction.addToBackStack(null);
            transaction.commit();

        }
        else
        {
            AreasFragment newFragment = new AreasFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment, "AREAS");
            transaction.commit();
        }
        return true;
    }



    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
