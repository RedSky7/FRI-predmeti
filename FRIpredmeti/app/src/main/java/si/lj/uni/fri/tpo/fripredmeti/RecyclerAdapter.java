package si.lj.uni.fri.tpo.fripredmeti;

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
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{


    private ArrayList<String> dataSource;
    private Activity mActivity;
    private boolean isPredmeti;


    public RecyclerAdapter(Activity a, Boolean predmeti){
        mActivity = a;
        isPredmeti = predmeti;
        dataSource = new ArrayList<>();

        fillData(predmeti, 0);

        //TODO: pridobi podatke in jih zapiši v dataSource

    }

    public void fillData(Boolean predmeti, int order)
    {
        dataSource.clear();
        if(predmeti) {
            if(order == 0) {
                dataSource.add("Računalniška arhitektura:201:97");
                dataSource.add("Organizacija računalnikov:201:59");
                dataSource.add("Digitalna vezja:26:51");
            }
            else
            {
                dataSource.add("Digitalna vezja:26:51");
                dataSource.add("Organizacija računalnikov:201:59");
                dataSource.add("Računalniška arhitektura:201:97");
            }
        }
        else {
            dataSource.add("Luka Kadunc:201:69");
            dataSource.add("Plagiat Kadunc:107:69");
            dataSource.add("Tina Kadunc:23:69");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_item2, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);



        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String all = dataSource.get(position);
        String[] stringComponents = all.split(":");

        holder.title.setText(stringComponents[0]);
        holder.number.setText("("+stringComponents[1]+")");
        holder.percent2.setText(stringComponents[2]+"%");
        holder.percent.setProgress(Integer.parseInt(stringComponents[2]));



       // Animation a = AnimationUtils.loadAnimation(mActivity, R.layout.animation_up);
       // a.reset();
        //a.setRepeatMode(Animation.INFINITE);
       // holder.percent2.clearAnimation();
        //holder.number.clearAnimation();
       // holder.percent2.startAnimation(a);
       // holder.number.startAnimation(a);


        Animation fadeIn = new ScaleAnimation(0, 1, 0, 1, 220, 160);
        fadeIn.setInterpolator(new AccelerateInterpolator()); //and this
        fadeIn.setStartOffset(5000);
        fadeIn.setDuration(700);
        fadeIn.setRepeatCount(Animation.INFINITE);
        fadeIn.setRepeatMode(Animation.REVERSE);
        holder.first.setAnimation(fadeIn);


//Animation fadeOut = new AlphaAnimation(0, 1);
        Animation fadeOut = new ScaleAnimation(1, 0, 1, 0, 220, 160);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(5000);
        fadeOut.setDuration(700);
        fadeOut.setRepeatCount(Animation.INFINITE);
        fadeOut.setRepeatMode(Animation.REVERSE);
        //fadeOut.setStartOffset(2000);
        holder.second.setAnimation(fadeOut);




        holder.current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = isPredmeti ? new Intent(mActivity, ClassOverview.class) : new Intent(mActivity, TeacherOverview.class);
                intent.putExtra("title", holder.title.getText());
                mActivity.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected CircleProgress percent;
        protected TextView percent2;
        protected TextView number;
        protected TextView title;
        protected CardView current;

        protected FrameLayout first;
        protected FrameLayout second;

        public ViewHolder(View itemView) {
            super(itemView);
            number =  (TextView) itemView.findViewById(R.id.number);
            percent = (CircleProgress) itemView.findViewById(R.id.percent);
            percent2 = (TextView) itemView.findViewById(R.id.percent2);
            title = (TextView) itemView.findViewById(R.id.title);
            current = (CardView) itemView.findViewById(R.id.card);

            first = (FrameLayout) itemView.findViewById(R.id.fl);
            second = (FrameLayout) itemView.findViewById(R.id.fl2);
        }
    }



}
