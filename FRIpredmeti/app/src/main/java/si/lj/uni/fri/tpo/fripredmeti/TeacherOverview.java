package si.lj.uni.fri.tpo.fripredmeti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }
}
