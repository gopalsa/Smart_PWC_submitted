package smart.cst.pwc;

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

import smart.cst.pwc.db.DbReport;

/**
 * Created by Gopal on 08-11-2017.
 */

public class AdditionalInformation extends AppCompatActivity {

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
        setContentView(R.layout.additional_info);
        dbReport = new DbReport(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
        }
        getSupportActionBar().setTitle(tittleString);
        TextView submit = (TextView) findViewById(R.id.submit);
        final EditText additionalinfo = (EditText) findViewById(R.id.additionalinfo);
        try {
            String additional = dbReport.getData(vrpString, tittleString, getResources().getString(R.string.additionalInfo));
            if (additional != null)
                additionalinfo.setText(additional);
        } catch (Exception e) {
            Log.d("AdditionalInformation ", e.toString());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (additionalinfo.getText().toString().length() > 0) {
                    int success = dbReport.updatedata(vrpString, tittleString,
                            getResources().getString(R.string.additionalInfo), additionalinfo.getText().toString());
                    if (success == 0)
                        dbReport.addData(vrpString, tittleString,
                                getResources().getString(R.string.additionalInfo), additionalinfo.getText().toString());
                    Intent io = new Intent(AdditionalInformation.this, Observation.class);
                    startActivity(io);
                } else {
                    additionalinfo.setError("Enter Additional Information");
                }
            }
        });
    }
}
