package si.lj.uni.fri.tpo.fripredmeti;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import si.lj.uni.fri.tpo.fripredmeti.REST.SendCommentVote;

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
                //map.put(0, new String[]{"Stankica", "Profesor je odličen :D", "11", "5", "0"});
                //map.put(1, new String[]{"Sabina", "Pezde me je fuknu :(", "12", "1", "0"});
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
        holder.hiddenID.setText(data[4]);
        holder.date.setText(data[5]);

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
        protected TextView hiddenID;
        protected TextView date;
        protected ImageButton upVote;
        protected ImageButton downVote;

        public ViewHolder(final View itemView) {
            super(itemView);
            commentAuthor = (TextView) itemView.findViewById(R.id.textView16);
            comment       = (TextView) itemView.findViewById(R.id.textView17);
            commentMark   = (TextView) itemView.findViewById(R.id.textView19);
            teacherMark   = (TextView) itemView.findViewById(R.id.textView18);
            hiddenID      = (TextView) itemView.findViewById(R.id.hiddenID);
            date          = (TextView) itemView.findViewById(R.id.tvDate);
            upVote        = (ImageButton) itemView.findViewById(R.id.upVote);
            downVote      = (ImageButton) itemView.findViewById(R.id.downVote);

            upVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String hiddenID = ((TextView) itemView.findViewById(R.id.hiddenID)).getText().toString();
                    try {
                        String result = new SendCommentVote().execute("blaz", hiddenID, "true").get();
                        if(result != "fail")
                            commentMark.setText(result);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });

            downVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String hiddenID = ((TextView) itemView.findViewById(R.id.hiddenID)).getText().toString();
                    try {
                        String result = new SendCommentVote().execute("blaz", hiddenID, "false").get();
                        if(result != "fail")
                            commentMark.setText(result);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}



