package si.lj.uni.fri.tpo.fripredmeti;

import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ClassOverview extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_overview);




        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar2));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getIntent().getStringExtra("title"));

        ScrollView sv = (ScrollView) findViewById(R.id.sw);
        sv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Rect scrollBounds = new Rect();
                v.getHitRect(scrollBounds);
                Spinner tv15 = (Spinner) findViewById(R.id.spinnerSortBy);
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

        //TODO: Fill botom data with right data
        ArrayList<String> data = new ArrayList<>();
        data.add("70");
        data.add("70%");
        data.add("54");

        fillCircle(data, (DonutProgress) findViewById(R.id.zanimivost_progress),
                (TextView) findViewById(R.id.zanimivost_procenti),
                (TextView) findViewById(R.id.zanimivost_ljudi));

        //TODO: Fill botom data with right data
        data.clear();
        data.add("49");
        data.add("49%");
        data.add("61");
        fillCircle(data, (DonutProgress) findViewById(R.id.priljubljenost_progress),
                (TextView) findViewById(R.id.priljubljenost_procenti),
                (TextView) findViewById(R.id.priljubljenost_ljudi));

        //TODO: Fill botom data with right data
        data.clear();
        data.add("80");
        data.add("4/5");
        data.add("30");
        fillCircle(data, (DonutProgress) findViewById(R.id.tezavnost_progress),
                (TextView) findViewById(R.id.tezavnost_skala),
                (TextView) findViewById(R.id.tezavnost_ljudi));

        //TODO: Uncomment create_KOMENTARJI();
        create_PREDPOGOJI();
        create_IZVAJALCI();
        create_TEHNOLOGIJE();
        create_OPIS();
        //create_KOMENTARJI();
    }


    public void create_PREDPOGOJI()
    {
        //TODO: Fill with right data use .fillData()
        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_class_overview_PREDPOGOJI);
        RecyclerAdapterTeacherOverview adapter1 = new RecyclerAdapterTeacherOverview(this, 1);
        recyclerView1.setAdapter(adapter1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
    }

    public void create_IZVAJALCI()
    {
        //TODO: Fill with right data use .fillData()
        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_class_overview_IZVAJALCI);
        RecyclerAdapterTeacherOverview adapter1 = new RecyclerAdapterTeacherOverview(this, 1);
        recyclerView1.setAdapter(adapter1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
    }

    public void create_TEHNOLOGIJE()
    {
        //TODO: Fill with right data use .fillData()
        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_class_overview_TEHNOLOGIJE);
        RecyclerAdapterTeacherOverview adapter1 = new RecyclerAdapterTeacherOverview(this, 1);
        recyclerView1.setAdapter(adapter1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
    }

    public void create_KOMENTARJI()
    {
        //TODO: Fill with right data use .fillData()
        RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.recycler_view_class_overview_KOMENTARJI);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        recyclerView3.setLayoutManager(layoutManager3);
        RecyclerAdapterCommentOverview adapter3 = new RecyclerAdapterCommentOverview(this, 0);
        recyclerView3.setAdapter(adapter3);
    }

    public void create_OPIS()
    {
        TextView desc = (TextView) findViewById(R.id.textView_DESCRIPTION);
        //TODO: Get opis from server and correct bottom
        desc.setText("To je opis");
    }

    public void fillCircle(ArrayList<String> data, DonutProgress progress, TextView procenti, TextView ljudi)
    {
        progress.setProgress(Integer.parseInt(data.get(0)));
        procenti.setText(data.get(1));
        ljudi.setText("(" + data.get(2) + ")");
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


    public void showPrimer(View v)
    {
        //Intent intent = new Intent(this, TeacherOverview.class);
        //startActivity(intent);

        Intent myIntent = new Intent(this, TeacherOverview.class);
        myIntent.putExtra("teacherID", 1);
        startActivity(myIntent);
    }



}
