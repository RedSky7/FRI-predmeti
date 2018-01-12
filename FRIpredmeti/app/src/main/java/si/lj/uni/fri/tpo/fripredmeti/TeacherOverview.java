package si.lj.uni.fri.tpo.fripredmeti;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.List;
import java.util.concurrent.ExecutionException;

import si.lj.uni.fri.tpo.fripredmeti.Model.Comment;
import si.lj.uni.fri.tpo.fripredmeti.Model.Course;
import si.lj.uni.fri.tpo.fripredmeti.Model.StaticGlobals;
import si.lj.uni.fri.tpo.fripredmeti.Model.Teacher;
import si.lj.uni.fri.tpo.fripredmeti.REST.GetClasses;
import si.lj.uni.fri.tpo.fripredmeti.REST.GetComments;
import si.lj.uni.fri.tpo.fripredmeti.REST.GetTeacher;
import si.lj.uni.fri.tpo.fripredmeti.REST.ImageDownloader;

public class TeacherOverview extends AppCompatActivity {

    private TextView tvPriljubljenost;
    private TextView tvNaziv;
    private TextView tvEmail;
    private TextView tvNumOfComments;
    private TextView tvPrejsnje;
    private TextView tvNumPrejsnje;
    private TextView tvAvgPrejsnje;
    private Spinner  spSort;
    private ImageView imSlika;
    private DonutProgress donutProgress;
    private DonutProgress donutProgress2;

    private int izvajalecID;
    private int sortIndex;

    private double KOLICNIK_ZA_OCENE = 20.0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_overview);

        //setSupportActionBar((Toolbar)findViewById(R.id.toolbar2));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        //poveži elemente
        tvPriljubljenost  = (TextView)findViewById(R.id.textView55);
        tvNaziv           = (TextView)findViewById(R.id.tvNaziv);
        imSlika           = (ImageView)findViewById(R.id.imSlika);
        tvEmail           = (TextView)findViewById(R.id.teacherContact);
        spSort            = (Spinner)findViewById(R.id.spinnerSortBy);
        tvNumOfComments   = (TextView)findViewById(R.id.numberOfComments);
        tvPrejsnje        = (TextView)findViewById(R.id.tvPrejsnje);
        tvAvgPrejsnje     = (TextView)findViewById(R.id.tvPrejsnjeleto);
        tvNumPrejsnje     = (TextView)findViewById(R.id.tvnumPrejsnje);
        donutProgress     = (DonutProgress)findViewById(R.id.donPriljubljenost);
        donutProgress2    = (DonutProgress)findViewById(R.id.donPriljubljenost2);


        //po čem bomo sortirali
        sortIndex = spSort.getSelectedItemPosition();

        //dinamično dodaj seznam tehnologij
        /*RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view_teacher_overview_TEHNOLOGIJE);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        RecyclerAdapterTeacherOverview adapter2 = new RecyclerAdapterTeacherOverview(this, 1);
        recyclerView2.setAdapter(adapter2);*/


        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortIndex = position;
                loadComments(izvajalecID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if(StaticGlobals.StaticEmail != null) {
            //prikaži ikono za komentiraje, na koncu scrollanja
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

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
            fab.animate().translationY(0).setInterpolator(new AccelerateInterpolator()).start();
        }

        //pridobi, ID učitelja, na katerega smo kliknili na prejšnjem activityju
        Intent intent = getIntent();
        izvajalecID = Integer.parseInt(intent.getStringExtra("mainID"));
        StaticGlobals.StaticIzvajalecID = izvajalecID;
        //StaticGlobals.StaticEmail = "blaz";

        //pridobi podatke o predavatelju
        try {
            Teacher t = new GetTeacher().execute(izvajalecID).get();
            loadTeacher(t);
            loadComments(izvajalecID);
            loadCourses(izvajalecID);

            Bitmap image = new ImageDownloader().execute(t.getSlika()).get();

            if(image == null) {
                imSlika.setBackground(getResources().getDrawable(R.drawable.ic_man));
            }
            else
                imSlika.setBackground(new BitmapDrawable(getResources(), image));

        } catch (Exception e) {
            Toast.makeText(TeacherOverview.this, "There was error connecting to server!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void showDialog(View v)
    {
        CommentDialog cd = new CommentDialog(this);
        Dialog d = cd.showDialog(this, false, izvajalecID, 0);
        d.setOnDismissListener(new Dialog.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //refresh comments
                loadComments(izvajalecID);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void loadTeacher(Teacher t){
        //sedaj začni z inicializacijo informacij
        getSupportActionBar().setTitle(t.toString());
        tvPriljubljenost.setText(Double.toString(t.splosnaOcena()));
        tvNaziv.setText(t.getNaziv());
        tvEmail.setText(t.getEmail());
        tvNumOfComments.setText(Integer.toString(t.getNumberOfComments()));

        tvNumPrejsnje.setText(Integer.toString(t.getNumberOfComments()));
        tvPrejsnje.setText("Prejsnje leto");
        tvAvgPrejsnje.setText(Double.toString(t.splosnaOcena()));

        donutProgress.setProgress(izracunajProcent(t.getSplosnaOcena()));
        donutProgress2.setProgress(izracunajProcent(t.getSplosnaOcena()));
        //izracunajProcent()
    }


    //naloži predmete
    private void loadCourses(int izvajalecID){

        List<Course> coursesList = null;
        try {
            coursesList = new GetClasses().execute(izvajalecID).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //dinamično dodaj seznam predmetov
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_teacher_overview_PREDMETI);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterTeacherOverview adapter = new RecyclerAdapterTeacherOverview(this, 0, null); //dodano null by Boštjan

        for(int i = 0; i < coursesList.size(); i++){
            Course c = coursesList.get(i);
            adapter.addData(c.getIme());
        }

        recyclerView.setAdapter(adapter);
    }


    //naloži komentarje
    private void loadComments(int izvajalecID){

        List<Comment> commentList = null;
        try {
            commentList = new GetComments().execute(izvajalecID, sortIndex).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.recycler_view_teacher_overview_COMMENTS);
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

    public float izracunajProcent(double st){
        float a = Math.round(st * KOLICNIK_ZA_OCENE * 100); //da se procenti pravilno izračunajo se mora najprej shranit, ne sprašuj zakaj
        return a / 100;
    }
}
