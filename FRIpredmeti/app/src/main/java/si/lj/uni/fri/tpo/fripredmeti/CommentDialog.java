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

import si.lj.uni.fri.tpo.fripredmeti.Model.StaticGlobals;
import si.lj.uni.fri.tpo.fripredmeti.REST.SendComment;
import si.lj.uni.fri.tpo.fripredmeti.REST.SendCommentPredmet;

/**
 * Created by timko on 6. 09. 2017.
 */

public class CommentDialog  implements DialogInterface.OnDismissListener{
    private Activity mActivity;
    private SeekBar splosnaOcena;
    private SeekBar tezavnost;
    private SeekBar zanimivost;
    private EditText mnenje;
    private ImageView more;

    public CommentDialog(Activity a)
    {
        mActivity = a;
    }

    public Dialog showDialog(final Activity activity, final boolean predmeti, final int izvajalecID, final int predmetID){
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
        more         = (ImageView)dialog.findViewById(R.id.view_more);

        splosnaOcena.setProgress(2);

        if(!predmeti){
            more.setVisibility(View.GONE);
        }

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

        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final Drawable one = ContextCompat.getDrawable(mActivity, R.drawable.one);
                final Drawable two = ContextCompat.getDrawable(mActivity, R.drawable.two);
                final Drawable three = ContextCompat.getDrawable(mActivity, R.drawable.three);
                final Drawable four = ContextCompat.getDrawable(mActivity, R.drawable.four);
                final Drawable five = ContextCompat.getDrawable(mActivity, R.drawable.five);

                final Drawable one_dark = ContextCompat.getDrawable(mActivity, R.drawable.one_dark);
                final Drawable two_dark = ContextCompat.getDrawable(mActivity, R.drawable.two_dark);
                final Drawable three_dark = ContextCompat.getDrawable(mActivity, R.drawable.three_dark);
                final Drawable four_dark = ContextCompat.getDrawable(mActivity, R.drawable.four_dark);
                final Drawable five_dark = ContextCompat.getDrawable(mActivity, R.drawable.five_dark);

                switch (progress){
                    case 0: seekBar.setThumb(seekBar.equals(splosnaOcena) ? one : one_dark ); break;
                    case 1: seekBar.setThumb(seekBar.equals(splosnaOcena) ? two : two_dark ); break;
                    case 2: seekBar.setThumb(seekBar.equals(splosnaOcena) ? three : three_dark ); break;
                    case 3: seekBar.setThumb(seekBar.equals(splosnaOcena) ? four : four_dark ); break;
                    case 4: seekBar.setThumb(seekBar.equals(splosnaOcena) ? five : five_dark ); break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        splosnaOcena.setOnSeekBarChangeListener(seekBarChangeListener);

        tezavnost.setOnSeekBarChangeListener(seekBarChangeListener);

        zanimivost.setOnSeekBarChangeListener(seekBarChangeListener);

        //dodajanje komentarja
        Button add = (Button) dialog.findViewById(R.id.btnAdd);
        add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ocena = Integer.toString(splosnaOcena.getProgress()+1);
                String tezavnostOcena = Integer.toString(tezavnost.getProgress()+1);
                String zanimivostOcena = Integer.toString(zanimivost.getProgress()+1);
                try {
                    //TODO: komentar, izvajalecID, splosnaOcena, email
                    String email = StaticGlobals.StaticEmail;
                    if(predmeti){
                        new SendCommentPredmet().execute(
                                mnenje.getText().toString(),
                                Integer.toString(predmetID),
                                ocena,
                                email,
                                tezavnostOcena,
                                zanimivostOcena
                        ).get();
                    }
                    else {
                        new SendComment().execute(mnenje.getText().toString(), Integer.toString(izvajalecID), ocena, email).get();
                    }

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