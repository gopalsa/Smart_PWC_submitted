package smart.cst.pwc;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;


import smart.cst.pwc.db.DbVrp;

/**
 * Created by whit3hawks on 11/16/16.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String vrpid = "vrpidKey";
    private DbVrp dbVrp;

    private Context context;

    // Constructor
    public FingerprintHandler(Context mContext) {

        context = mContext;
        sharedpreferences = context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        dbVrp = new DbVrp(context);
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        }
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
   //     ((Activity) context).finish();
//        List<ArrayList<String>> vrpList = dbVrp.getAllData();
//        if (vrpList.size() > 0) {
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putString(vrpid, vrpList.get(0).get(0));
//            editor.commit();
//            Intent io = new Intent(context, MainActivity.class);
//            context.startActivity(io);
//            ((Activity) context).finish();
//        } else {
//            Toast.makeText(context, "Add atlease one vrp", Toast.LENGTH_SHORT).show();
//        }
    }

    private void update(String e) {
        TextView textView = (TextView) ((Activity) context).findViewById(R.id.errorText);
        textView.setText(e);
    }

}
