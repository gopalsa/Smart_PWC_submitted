package info.androidhive.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import info.androidhive.recyclerview.app.AppConfig;

/**
 * Created by Gopal on 08-11-2017.
 */

public class AttendancePage extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    String tittleString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_page);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
        }
        TextView submit = (TextView) findViewById(R.id.submit);
        TextView tittletxt = (TextView) findViewById(R.id.tittletxt);
        TextView subtittletxt = (TextView) findViewById(R.id.subtittletxt);
        TextView processadopted = (TextView) findViewById(R.id.processadopted);
        tittletxt.setText(tittleString);
        Log.d("xxxxxxxxxxxx",tittleString);
        subtittletxt.setText("Group Picture & Video of " + tittleString + " participants");
        processadopted.setText(getResources().getString(AppConfig.getProcess(tittleString)));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(AttendancePage.this, ToolReport.class);
                startActivity(io);
            }
        });
    }

}
