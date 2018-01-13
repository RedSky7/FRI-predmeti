package si.lj.uni.fri.tpo.fripredmeti;

/**
 * Created by timko on 26. 12. 2017.
 */


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import si.lj.uni.fri.tpo.fripredmeti.Model.*;
import si.lj.uni.fri.tpo.fripredmeti.REST.GetAllPodrocja;

/**
 * Created by rajat on 2/8/2015.
 */
public class RecyclerAdapterAreas extends RecyclerView.Adapter<RecyclerAdapterAreas.ViewHolder>{


    private ArrayList<String> dataSource;
    private ArrayList<Integer> iconSource;
    private Activity mActivity;
    private List<AreasModel> listAreas;


    public RecyclerAdapterAreas(Activity a){
        mActivity = a;
        dataSource = new ArrayList<>();
        iconSource = new ArrayList<>();
        try {
            listAreas = new GetAllPodrocja().execute().get();
            for (int i = 0; i < listAreas.size(); i++) {
                dataSource.add(listAreas.get(i).getImePodrocja());
                iconSource.add(R.drawable.ic_polygonal_chart_of_triangles);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //TODO: pridobi podatke in jih zapiši v dataSource

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_item_3, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //String all = dataSource.get(position);
        AreasModel areasModel = listAreas.get(position);

        holder.title.setText(areasModel.getImePodrocja());
        holder.icon.setImageDrawable(mActivity.getDrawable(iconSource.get(position)));

        holder.hiddenPodrocjeID.setText(areasModel.getPodrocjeID() + "");

        //holder.number.setText("("+stringComponents[1]+")");
        //holder.percent2.setText(stringComponents[2]+"%");
        //holder.percent.setProgress(Integer.parseInt(stringComponents[2]));
        if(areasModel.getImePodrocja().equals("Strojna oprema(HW)"))
            holder.icon.setImageDrawable(mActivity.getDrawable(R.drawable.ic_cpu));
        else if(areasModel.getImePodrocja().equals("Programska oprema(SW)"))
            holder.icon.setImageDrawable(mActivity.getDrawable(R.drawable.ic_cd));
        else if(areasModel.getImePodrocja().equals("Igre in umetna inteligenca"))
            holder.icon.setImageDrawable(mActivity.getDrawable(R.drawable.ic_gamepad));
        else if(areasModel.getImePodrocja().equals("Operacijski sistemi"))
            holder.icon.setImageDrawable(mActivity.getDrawable(R.drawable.ic_operating_system));
        else if(areasModel.getImePodrocja().equals("Spletne tehnologije"))
            holder.icon.setImageDrawable(mActivity.getDrawable(R.drawable.ic_wifi));
        else if(areasModel.getImePodrocja().equals("Informatika"))
            holder.icon.setImageDrawable(mActivity.getDrawable(R.drawable.ic_server));
        else if(areasModel.getImePodrocja().equals("Matematika"))
            holder.icon.setImageDrawable(mActivity.getDrawable(R.drawable.ic_calculator));
        else if(areasModel.getImePodrocja().equals("Ostalo"))
            holder.icon.setImageDrawable(mActivity.getDrawable(R.drawable.ic_computer));

        holder.current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FrameLayout spinner = (FrameLayout) mActivity.findViewById(R.id.progress);
                spinner.setVisibility(View.VISIBLE);

                Intent intent = new Intent(mActivity, MainActivity.class);
                intent.putExtra("title", holder.title.getText());
                intent.putExtra("hiddenPredmetID", holder.hiddenPodrocjeID.getText());
                mActivity.startActivity(intent);

                //spinner.setVisibility(View.GONE);
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
        protected TextView hiddenPodrocjeID;

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
            hiddenPodrocjeID = (TextView) itemView.findViewById(R.id.TV_idPodrocja);

            //first = (FrameLayout) itemView.findViewById(R.id.fl);
            //second = (FrameLayout) itemView.findViewById(R.id.fl2);
        }
    }

    public void fillDataWithQuery(String query) {
        dataSource.clear();
        AreasModel am;
        for (int i = 0; i < listAreas.size(); i++) {
            am = listAreas.get(i);

            if(am.getImePodrocja().toLowerCase().contains(query.toLowerCase())){
                dataSource.add(am.getImePodrocja());
            }
        }

        /*AreasModel am;

        for (int i = 0; i < vsaPodrocja.size(); i++) {
            am = vsaPodrocja.get(i);

            if(am.getImePodrocja().toLowerCase().contains(query.toLowerCase())){
                dataSource.add(am.getImePodrocja() + ":10:100");
            }
        }

        searching = true;

        dataSource.add("This is with query:0:0");
        dataSource.add("Query:0:0");
        dataSource.add("Querrrx:0:0");
        dataSource.add("Querrryyyyyyyyyyyy:0:0");*/
    }

}
