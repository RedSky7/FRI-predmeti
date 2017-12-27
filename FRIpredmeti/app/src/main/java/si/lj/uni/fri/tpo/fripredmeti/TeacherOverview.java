package si.lj.uni.fri.tpo.fripredmeti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class TeacherOverview extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_overview);

        //getSupportActionBar().setTitle("Janez Primer");

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar2));
        getSupportActionBar().setTitle("Janez Primer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_teacher_overview_PREDMETI);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterTeacherOverview adapter = new RecyclerAdapterTeacherOverview(this, 0);
        recyclerView.setAdapter(adapter);


        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view_teacher_overview_TEHNOLOGIJE);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        RecyclerAdapterTeacherOverview adapter2 = new RecyclerAdapterTeacherOverview(this, 1);
        recyclerView2.setAdapter(adapter2);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
