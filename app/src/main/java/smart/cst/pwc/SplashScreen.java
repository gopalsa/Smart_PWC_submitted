package smart.cst.pwc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashpra);


        //thread for splash screen running
        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    Log.d("Exception", "Exception" + e);
                } finally {
                    startActivity(new Intent(SplashScreen.this, FingerprintActivity.class));
                }
                finish();
            }
        };
        logoTimer.start();
    }


    public void writeFileOnInternalStorage(Context mcoContext,
                                           String sFileName,
                                           String sBody) {
        File file = new File(mcoContext.getFilesDir(), "mydir");
        if (!file.exists()) {
            file.mkdir();
        }

        try {
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
