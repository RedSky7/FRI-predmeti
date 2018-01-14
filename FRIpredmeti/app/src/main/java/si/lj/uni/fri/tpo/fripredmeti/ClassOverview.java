package si.lj.uni.fri.tpo.fripredmeti;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import android.widget.AdapterView;
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
import java.util.concurrent.ExecutionException;

import si.lj.uni.fri.tpo.fripredmeti.Model.Comment;
import si.lj.uni.fri.tpo.fripredmeti.Model.Course;
import si.lj.uni.fri.tpo.fripredmeti.Model.StaticGlobals;
import si.lj.uni.fri.tpo.fripredmeti.REST.GetClassDetails;
import si.lj.uni.fri.tpo.fripredmeti.REST.GetComments;
import si.lj.uni.fri.tpo.fripredmeti.REST.GetKomentarPredmet;

public class ClassOverview extends AppCompatActivity {

    private Spinner spSort;

    private int predmetID;
    private int sortID;
    private Course celPredmet = null;

    private TextView tvNumPriljubljenost;
    private TextView tvNumTezavnost;
    private TextView tvNumZanimivost;
    private TextView tvAvgPriljubljenost;
    private TextView tvAvgTezavnost;
    private TextView tvAvgZanimivost;


    private double KOLICNIK_ZA_OCENE = 20.0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_overview);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar2));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getIntent().getStringExtra("title"));

        tvNumPriljubljenost  = (TextView)findViewById(R.id.tvNumPriljubljenost);
        tvNumTezavnost       = (TextView)findViewById(R.id.tvNumTezavnost);
        tvNumZanimivost      = (TextView)findViewById(R.id.tvNumZanimivost);
        tvAvgPriljubljenost  = (TextView)findViewById(R.id.tvAvgPriljubljenost);
        tvAvgTezavnost       = (TextView)findViewById(R.id.tvAvgTezavnost);
        tvAvgZanimivost      = (TextView)findViewById(R.id.tvAvgZanimivost);

        if(StaticGlobals.StaticEmail != null) {
            ScrollView sv = (ScrollView) findViewById(R.id.sw);
            sv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Rect scrollBounds = new Rect();
                    v.getHitRect(scrollBounds);

                    Spinner tv15 = (Spinner) findViewById(R.id.spinnerSortBy);
                    //FrameLayout tv15 = (FrameLayout) findViewById(R.id.fl);
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

            //če je scroll premajhen se gumb ne more prikazati, tako da ga prikažemo tudi tukaj
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
            fab.animate().translationY(0).setInterpolator(new AccelerateInterpolator()).start();
        }

        spSort = (Spinner)findViewById(R.id.spinnerSortBy);

        sortID = spSort.getSelectedItemPosition();

        Intent intent = getIntent();
        predmetID = Integer.parseInt(intent.getStringExtra("mainID"));

        loadComments(predmetID);

        try {
            celPredmet = new GetClassDetails().execute(predmetID).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(celPredmet != null){
            ArrayList<String> data = new ArrayList<>();
            data.add(izracunajProcent(celPredmet.getZanimivostOcena()) + "");
            data.add(izracunajProcent(celPredmet.getZanimivostOcena()) / 20 + "");
            data.add(Integer.toString(celPredmet.getStKomentarjev()));

            fillCircle(data, (DonutProgress) findViewById(R.id.zanimivost_progress),
                    (TextView) findViewById(R.id.tvAvgZanimivost),
                    (TextView) findViewById(R.id.tvNumZanimivost), true);

            data.clear();
            data.add(izracunajProcent(celPredmet.getSplosnaOcena()) + ""); //***to nej bi bla priljubljenost
            data.add(izracunajProcent(celPredmet.getSplosnaOcena()) / 20 + "");
            data.add(Integer.toString(celPredmet.getStKomentarjev()));

            fillCircle(data, (DonutProgress) findViewById(R.id.priljubljenost_progress),
                    (TextView) findViewById(R.id.tvAvgPriljubljenost),
                    (TextView) findViewById(R.id.tvNumPriljubljenost), true);

            data.clear();
            data.add(izracunajProcent(celPredmet.getTezavnostOcena()) + "");
            data.add(izracunajProcent(celPredmet.getTezavnostOcena()) / 20  + "");
            data.add(Integer.toString(celPredmet.getStKomentarjev()));

            fillCircle(data, (DonutProgress) findViewById(R.id.tezavnost_progress),
                    (TextView) findViewById(R.id.tvAvgTezavnost),
                    (TextView) findViewById(R.id.tvNumTezavnost), true);
        }
        /*else {

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
        }*/

        //TODO: Uncomment create_KOMENTARJI();
        create_PREDPOGOJI();
        create_IZVAJALCI();
        create_TEHNOLOGIJE();
        create_OPIS();
        //create_KOMENTARJI();

        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortID = position;
                loadComments(predmetID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public double izracunajProcent(double st){
        double a = Math.round(st * KOLICNIK_ZA_OCENE * 100); //da se procenti pravilno izračunajo se mora najprej shranit, ne sprašuj zakaj
        return a / 100;
    }

    public void create_PREDPOGOJI()
    {
        //TODO: Fill with right data use .fillData()
        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_class_overview_PREDPOGOJI);
        RecyclerAdapterTeacherOverview adapter1 = new RecyclerAdapterTeacherOverview(this, 3, celPredmet.getPredpogoji());
        recyclerView1.setAdapter(adapter1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
    }

    public void create_IZVAJALCI()
    {
        //TODO: Fill with right data use .fillData()
        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_class_overview_IZVAJALCI);
        RecyclerAdapterTeacherOverview adapter1 = new RecyclerAdapterTeacherOverview(this, 3, celPredmet.getIzvajalciSeznam());
        recyclerView1.setAdapter(adapter1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
    }

    public void create_TEHNOLOGIJE()
    {
        //TODO: Fill with right data use .fillData()
            RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_class_overview_TEHNOLOGIJE);
            RecyclerAdapterTeacherOverview adapter1 = new RecyclerAdapterTeacherOverview(this, 3, celPredmet.getOznake());
            recyclerView1.setAdapter(adapter1);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView1.setLayoutManager(layoutManager);
    }

    public void create_KOMENTARJI()
    {
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
        desc.setText(celPredmet != null ? celPredmet.getOcena() : "To je opis");
    }

    public void fillCircle(ArrayList<String> data, DonutProgress progress, TextView procenti, TextView ljudi, boolean override)
    {
        progress.setProgress(Float.parseFloat(data.get(0)));
        if(!override)
            procenti.setText(data.get(1).substring(0, 2) + "%");
        else
            procenti.setText(data.get(1).substring(0, 3).replace(".", ","));
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
        Dialog d = cd.showDialog(this, true, 0, predmetID);
        d.setOnDismissListener(new Dialog.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //refresh comments
                loadComments(predmetID);
            }
        });
    }

    public void showPrimer(View v)
    {
        //Intent intent = new Intent(this, TeacherOverview.class);
        //startActivity(intent);

        Intent myIntent = new Intent(this, TeacherOverview.class);
        myIntent.putExtra("teacherID", 1);
        startActivity(myIntent);
    }

    private void loadComments(int predmetID){

        List<Comment> commentList = null;
        try {
            commentList = new GetKomentarPredmet().execute(predmetID, sortID).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.recycler_view_class_overview_KOMENTARJI);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        recyclerView3.setLayoutManager(layoutManager3);
        RecyclerAdapterCommentOverview adapter3 = new RecyclerAdapterCommentOverview(this, 0);

        for(int i = 0; i < commentList.size(); i++){
            //map.put(0, new String[]{"Stankica", "Profesor je odličen :D", "11", "5", "ID"});
            Comment c = commentList.get(i);
            adapter3.addData(i, new String[]{
                    c.getUsername(),
                    c.getKomentar(),
                    Integer.toString(c.getOcenaKomentar()),
                    Integer.toString(c.getSplosnaOcena()),
                    Integer.toString(c.getKomentarID()),
                    c.getDatum()});
        }
        recyclerView3.setAdapter(adapter3);
    }

}

