package si.lj.uni.fri.tpo.fripredmeti;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import si.lj.uni.fri.tpo.fripredmeti.Model.Teacher;
import si.lj.uni.fri.tpo.fripredmeti.REST.GetTeacherData;
import si.lj.uni.fri.tpo.fripredmeti.REST.ImageDownloader;

public class TeacherOverview extends AppCompatActivity {

    private TextView tvPriljubljenost;
    //private TextView tvSlika;
    private TextView tvNaziv;
    private ImageView imSlika;

    private Teacher t;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_overview);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar2));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        //poveži elemente
        tvPriljubljenost  = (TextView)findViewById(R.id.tvPriljbuljenost);
        tvNaziv           = (TextView)findViewById(R.id.tvNaziv);
        imSlika           = (ImageView)findViewById(R.id.imSlika);

        //dinamično dodaj seznam predmetov
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_teacher_overview_PREDMETI);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterTeacherOverview adapter = new RecyclerAdapterTeacherOverview(this, 0);

        adapter.addData("Stanko");

        recyclerView.setAdapter(adapter);


        //dinamično dodaj seznam tehnologij
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view_teacher_overview_TEHNOLOGIJE);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        RecyclerAdapterTeacherOverview adapter2 = new RecyclerAdapterTeacherOverview(this, 1);
        recyclerView2.setAdapter(adapter2);


        ScrollView sv = (ScrollView) findViewById(R.id.sw);
        sv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Rect scrollBounds = new Rect();
                v.getHitRect(scrollBounds);
                FrameLayout tv15 = (FrameLayout) findViewById(R.id.fl2);
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
                if (tv15.getLocalVisibleRect(scrollBounds)) {
                    fab.animate().translationY(0).setInterpolator(new AccelerateInterpolator()).start();
                } else {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();
                    int fab_bottomMargin = layoutParams.bottomMargin;
                    fab.animate().translationY(fab.getHeight() + fab_bottomMargin).setInterpolator(new AccelerateInterpolator()).start();
                }
            }
        });


        //pridobi, ID učitelja, na katerega smo kliknili na prejšnjem activityju
        Intent intent = getIntent();
        int izvajalecID = intent.getIntExtra("teacherID", 0);


        //pridobi podatke o predavatelju
        try {
            t = new GetTeacherData().execute(izvajalecID).get();

            Bitmap image = new ImageDownloader().execute(t.getSlika()).get();
            imSlika.setBackground(new BitmapDrawable(getResources(), image));

            Toast.makeText(TeacherOverview.this, t.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //sedaj začni z inicializacijo informacij
        getSupportActionBar().setTitle(t.toString());
        tvPriljubljenost.setText(Double.toString(t.splosnaOcena()));
        tvNaziv.setText(t.getNaziv());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void showDialog(View v)
    {
        CommentDialog cd = new CommentDialog(this);
        cd.showDialog(this);
    }




}
