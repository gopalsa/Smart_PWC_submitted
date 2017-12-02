package info.androidhive.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import info.androidhive.recyclerview.db.DbReport;

/**
 * Created by Gopal on 08-11-2017.
 */

public class Observation extends AppCompatActivity {
    DbReport dbReport;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observation);
        dbReport = new DbReport(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
        }

        getSupportActionBar().setTitle(tittleString);
        TextView submit = (TextView) findViewById(R.id.submit);
        final EditText observation = (EditText) findViewById(R.id.observation);
        try {
            String obs = dbReport.getData(vrpString, tittleString, getResources().getString(R.string.observation));
            if (obs != null)
                observation.setText(obs);
        } catch (Exception e) {
            Log.d("Observation ", e.toString());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (observation.getText().toString().length() > 0) {
                    int success = dbReport.updatedata(vrpString, tittleString,
                            getResources().getString(R.string.observation), observation.getText().toString());
                    if (success == 0)
                        dbReport.addData(vrpString, tittleString,
                                getResources().getString(R.string.observation), observation.getText().toString());
                    Intent io = new Intent(Observation.this, MainActivity.class);
                    startActivity(io);
                } else {
                    observation.setError("Enter Observation");
                }
            }
        });
    }
}
