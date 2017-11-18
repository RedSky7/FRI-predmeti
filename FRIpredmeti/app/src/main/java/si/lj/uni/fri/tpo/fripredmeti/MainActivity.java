package si.lj.uni.fri.tpo.fripredmeti;

import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Računalniška grafika");

        ScrollView sv = (ScrollView) findViewById(R.id.sw);
        sv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Rect scrollBounds = new Rect();
                v.getHitRect(scrollBounds);
                FrameLayout tv15 = (FrameLayout) findViewById(R.id.fl2);
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
                if (tv15.getLocalVisibleRect(scrollBounds)) {
                    fab.animate().translationY(0).setInterpolator(new LinearInterpolator()).start();
                } else {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab.getLayoutParams();
                    int fab_bottomMargin = layoutParams.bottomMargin;
                    fab.animate().translationY(fab.getHeight() + fab_bottomMargin).setInterpolator(new LinearInterpolator()).start();
                }
            }
        });

    }


    public void showDialog(View v)
    {
        CommentDialog cd = new CommentDialog(this);
        cd.showDialog(this);
    }
}
