package si.lj.uni.fri.tpo.fripredmeti;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import si.lj.uni.fri.tpo.fripredmeti.Model.StaticGlobals;
import si.lj.uni.fri.tpo.fripredmeti.REST.SendRegistration;

/**
 * Created by Blaz on 07-Jan-18.
 */

public class RegistrationDialog  implements DialogInterface.OnDismissListener{

    private Activity mActivity;
    private Button cancel;
    private Button registration;
    private TextView username;

    public RegistrationDialog(Activity a)
    {
        mActivity = a;
    }

    public Dialog showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        mActivity = activity;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.registration_dialog);

        cancel       = (Button)dialog.findViewById(R.id.btnCancel);
        registration = (Button)dialog.findViewById(R.id.btnRegistration);
        username     = (TextView)dialog.findViewById(R.id.tvUserName);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                try {
                    String verificationCode = new SendRegistration().execute(
                            StaticGlobals.StaticEmail,
                            StaticGlobals.StaticPassword,
                            username.getText().toString()).get();
                    if(verificationCode.equals("fail")){
                        Toast.makeText(mActivity, "Registracija je neuspešna", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(mActivity, "Registracija je uspešna", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }
}
