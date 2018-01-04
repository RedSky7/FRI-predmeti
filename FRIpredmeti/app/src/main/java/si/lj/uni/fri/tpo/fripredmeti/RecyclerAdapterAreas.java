package si.lj.uni.fri.tpo.fripredmeti;

/**
 * Created by timko on 26. 12. 2017.
 */


import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;

import java.util.ArrayList;

/**
 * Created by rajat on 2/8/2015.
 */
public class RecyclerAdapterAreas extends RecyclerView.Adapter<RecyclerAdapterAreas.ViewHolder>{


    private ArrayList<String> dataSource;
    private ArrayList<Integer> iconSource;
    private Activity mActivity;


    public RecyclerAdapterAreas(Activity a){
        mActivity = a;
        dataSource = new ArrayList<>();
        iconSource = new ArrayList<>();

        fillData();

        //TODO: pridobi podatke in jih zapiši v dataSource

    }

    public void fillData()
    {

        dataSource.add("Ni za kej");
        iconSource.add(R.drawable.ic_two);

        dataSource.add("Perčič");
        iconSource.add(R.drawable.ic_one);

        dataSource.add("Področje krompirja");
        iconSource.add(R.drawable.ic_three);



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);



        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String all = dataSource.get(position);

        holder.title.setText(all);
        holder.icon.setImageDrawable(mActivity.getDrawable(iconSource.get(position)));
        //holder.number.setText("("+stringComponents[1]+")");
        //holder.percent2.setText(stringComponents[2]+"%");
        //holder.percent.setProgress(Integer.parseInt(stringComponents[2]));






        holder.current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MainActivity.class);
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
        //protected CircleProgress percent;
        //protected TextView percent2;
        //protected TextView number;
        protected TextView title;
        protected ImageView icon;
        protected CardView current;

        //protected FrameLayout first;
        //protected FrameLayout second;

        public ViewHolder(View itemView) {
            super(itemView);
            //number =  (TextView) itemView.findViewById(R.id.number);
            //percent = (CircleProgress) itemView.findViewById(R.id.percent);
            //percent2 = (TextView) itemView.findViewById(R.id.percent2);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            current = (CardView) itemView.findViewById(R.id.card);

            //first = (FrameLayout) itemView.findViewById(R.id.fl);
            //second = (FrameLayout) itemView.findViewById(R.id.fl2);
        }
    }



}
