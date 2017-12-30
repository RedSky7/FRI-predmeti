package si.lj.uni.fri.tpo.fripredmeti;

/**
 * Created by timko on 18. 11. 2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;


import android.support.v7.widget.CardView;


import java.util.concurrent.ExecutionException;

import si.lj.uni.fri.tpo.fripredmeti.REST.SendComment;

/**
 * Created by timko on 6. 09. 2017.
 */

public class CommentDialog  implements DialogInterface.OnDismissListener{
    private Activity mActivity;
    private SeekBar splosnaOcena;
    private SeekBar tezavnost;
    private SeekBar zanimivost;
    private EditText mnenje;

    public CommentDialog(Activity a)
    {
        mActivity = a;
    }

    public Dialog showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        mActivity = activity;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.comment_dialog);

        splosnaOcena = (SeekBar)dialog.findViewById(R.id.seekBar2);
        tezavnost    = (SeekBar)dialog.findViewById(R.id.seekBar22);
        zanimivost   = (SeekBar)dialog.findViewById(R.id.seekBar23);
        mnenje       = (EditText)dialog.findViewById(R.id.comment);

        splosnaOcena.setProgress(2);

        CardView cv = (CardView) dialog.findViewById(R.id.card_more);
        cv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ImageView tv = (ImageView) v.findViewById(R.id.view_more);
                LinearLayout ll = (LinearLayout) v.getParent();
                RelativeLayout rl = (RelativeLayout) ll.getChildAt(0);
                if(rl.getVisibility() == View.GONE)
                {
                    rl.setVisibility(View.VISIBLE);
                    tv.setBackgroundResource(R.drawable.ic_expand_less_black_24dp);
                }
                else
                {
                    rl.setVisibility(View.GONE);
                    tv.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });

        splosnaOcena.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final Drawable one = ContextCompat.getDrawable(mActivity, R.drawable.one);
                final Drawable two = ContextCompat.getDrawable(mActivity, R.drawable.two);
                final Drawable three = ContextCompat.getDrawable(mActivity, R.drawable.three);
                final Drawable four = ContextCompat.getDrawable(mActivity, R.drawable.four);
                final Drawable five = ContextCompat.getDrawable(mActivity, R.drawable.five);

                switch (progress){
                    case 0: splosnaOcena.setThumb(one); break;
                    case 1: splosnaOcena.setThumb(two); break;
                    case 2: splosnaOcena.setThumb(three); break;
                    case 3: splosnaOcena.setThumb(four); break;
                    case 4: splosnaOcena.setThumb(five); break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //dodajanje komentarja
        Button add = (Button) dialog.findViewById(R.id.button2);
        add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ocena = Integer.toString(splosnaOcena.getProgress()+1);
                try {
                    //TODO: komentar, izvajalecID, splosnaOcena, email
                    new SendComment().execute(mnenje.getText().toString(), "1", ocena, "blaz").get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();

            }
        });


        //preklic dodajanje komentarja
        Button cancel = (Button) dialog.findViewById(R.id.button3);
        cancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {}
}