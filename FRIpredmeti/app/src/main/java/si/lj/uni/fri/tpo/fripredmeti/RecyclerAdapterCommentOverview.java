package si.lj.uni.fri.tpo.fripredmeti;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Blaz on 28-Dec-17.
 */

public class RecyclerAdapterCommentOverview extends RecyclerView.Adapter<RecyclerAdapterCommentOverview.ViewHolder>{


    //private ArrayList<String> dataSource;
    Map<Integer, String[]> map;

    private Activity mActivity;


    public RecyclerAdapterCommentOverview(Activity a, int type){
        mActivity = a;

        //dataSource = new ArrayList<>();
        map = new HashMap<Integer, String[]>();
        //TODO: pridobi podatke in jih zapiši v dataSource

        fillData(type);

    }

    public void addData(int key, String[] data){
        //dataSource.add(data);
        map.put(key, data);
    }

    public void fillData(int type)
    {
        switch (type)
        {
            case 0: //filamo PREDMETI
                map.put(0, new String[]{"Stankica", "Profesor je odličen :D", "11", "5"});
                map.put(1, new String[]{"Sabina", "Pezde me je fuknu :(", "12", "1"});
                //dataSource.add("Avtor komentrja");
                //dataSource.add("LAIDU LAIDU LAIDU LAIDU LALIDU");
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String[] data = map.get(position);

        holder.commentAuthor.setText(data[0]);
        holder.comment.setText(data[1]);
        holder.commentMark.setText(data[2]);
        holder.teacherMark.setText(data[3]);

    }




    @Override
    public int getItemCount() {
        return map.size();
        //return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        //protected TextView title;
        protected TextView commentAuthor;
        protected TextView comment;
        protected TextView commentMark;
        protected TextView teacherMark;

        public ViewHolder(View itemView) {
            super(itemView);
            commentAuthor = (TextView) itemView.findViewById(R.id.textView16);
            comment       = (TextView) itemView.findViewById(R.id.textView17);
            commentMark   = (TextView) itemView.findViewById(R.id.textView19);
            teacherMark   = (TextView) itemView.findViewById(R.id.textView18);
        }
    }
}



