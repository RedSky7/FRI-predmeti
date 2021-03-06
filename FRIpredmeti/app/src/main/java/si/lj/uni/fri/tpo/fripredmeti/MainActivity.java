package si.lj.uni.fri.tpo.fripredmeti;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import si.lj.uni.fri.tpo.fripredmeti.Fragments.ClassessFragment;
import si.lj.uni.fri.tpo.fripredmeti.Fragments.TeachersFragment;

public class MainActivity extends AppCompatActivity implements  ClassessFragment.OnFragmentInteractionListener, TeachersFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);



        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        //imageView.animate().rotationY(180).setInterpolator(new LinearInterpolator()).start();
        //imageView.animate().rotationYBy(180).setInterpolator(new LinearInterpolator()).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FrameLayout spinner = (FrameLayout) findViewById(R.id.progress);
        spinner.setVisibility(View.GONE);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ClassessFragment(getIntent().getStringExtra("hiddenPredmetID"), getIntent().getStringExtra("title")), "predmeti");
        adapter.addFragment(new TeachersFragment(getIntent().getStringExtra("hiddenPredmetID")), "izvajalci");

        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);
    }


    public void startClassActivity(View v)
    {
        Intent intent = new Intent(this, ClassOverview.class);
        startActivity(intent);
    }

    public void startMainActivity(View v)
    {
        Intent intent = new Intent(this, ClassOverview.class);
        startActivity(intent);
    }

    public void startTeacherOverview(View v)
    {
        //Intent intent = new Intent(this, TeacherOverview.class);
        //startActivity(intent);
        Intent myIntent = new Intent(this, TeacherOverview.class);
        myIntent.putExtra("teacherID", 1);
        startActivity(myIntent);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }





    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }


        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }



        @Override
        public int getItemPosition(Object obj)
        {
            return POSITION_NONE;
        }



        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
            /*
            if(container != null) {
                for(int i = 0; i < container.getChildCount(); i++)
                {
                    View view = container.getChildAt(i);

                    RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view);

                    boolean notEmpty = rv.getAdapter().getItemCount() == 0;

                    if (notEmpty) {
                        ImageView iw = (ImageView) view.findViewById(R.id.fs_emptyIcon);
                        TextView tw = (TextView) view.findViewById(R.id.fs_emptyText);
                        if (iw != null && tw != null) {
                            Log.e("HIDING2", notEmpty + "");
                            iw.setVisibility((notEmpty) ? View.VISIBLE : View.GONE);
                            tw.setVisibility((notEmpty) ? View.VISIBLE : View.GONE);
                        }
                    }


                }
            }*/
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri){}
}
