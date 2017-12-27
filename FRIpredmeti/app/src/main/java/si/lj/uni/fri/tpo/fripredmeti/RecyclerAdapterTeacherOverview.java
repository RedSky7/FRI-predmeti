package si.lj.uni.fri.tpo.fripredmeti;

import android.support.v7.widget.RecyclerView;

/**
 * Created by timko on 27. 12. 2017.
 */

/**
 * Created by timko on 26. 12. 2017.
 */


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.github.lzyzsd.circleprogress.CircleProgress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by rajat on 2/8/2015.
 */
public class RecyclerAdapterTeacherOverview extends RecyclerView.Adapter<RecyclerAdapterTeacherOverview.ViewHolder>{


    private ArrayList<String> dataSource;
    private Activity mActivity;


    public RecyclerAdapterTeacherOverview(Activity a, int type){
        mActivity = a;

        dataSource = new ArrayList<>();
        //TODO: pridobi podatke in jih zapiši v dataSource

        fillData(type);

    }

    public void addData(String data){
        dataSource.add(data);
    }

    public void fillData(int type)
    {
        switch (type)
        {
            case 0: //filamo PREDMETI
                //dataSource.add("Računalniška arhitektura");
                //dataSource.add("Organizacija računalnikov");
                break;
            case 1:    //filamo TEHNOLOGIJE
                dataSource.add("webGL");
                dataSource.add("HTML");
                dataSource.add("CSS");
                dataSource.add("JavaScript");
                break;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_overview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(dataSource.get(position));
    }




    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView title;


        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);

        }
    }



}

