package info.androidhive.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import info.androidhive.recyclerview.app.AppConfig;
import info.androidhive.recyclerview.table.ui.SampleActivity;

/**
 * Created by Gopal on 08-11-2017.
 */

public class ToolReport extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String filename = "filenameKey";
    String tittleString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_report);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
        }
        TextView submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(ToolReport.this, HistoricalTimelinePhoto.class);
                startActivity(io);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tool, menu);
        if (tittleString.equalsIgnoreCase("Historical Timeline")
                || tittleString.equalsIgnoreCase("Transect Walk")
                || tittleString.equalsIgnoreCase("Village Resource Mapping (Village, water and soil maps)")
                || tittleString.equalsIgnoreCase("Wealth Ranking")) {
            MenuItem item = menu.findItem(R.id.report);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.report) {
            if (tittleString.equalsIgnoreCase("Gender Analysis")) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(ToolReport.this);
                builderSingle.setTitle("Select Gender:-");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ToolReport.this, android.R.layout.simple_list_item_1);
                arrayAdapter.add("Male");
                arrayAdapter.add("FeMale");

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        if (strName.equalsIgnoreCase("Male")) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(filename, "gender_analysis_male.csv");
                            editor.commit();
                        } else {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(filename, "gender_analysis_female.csv");
                            editor.commit();
                        }
                        Intent io = new Intent(ToolReport.this, SampleActivity.class);
                        startActivity(io);
                    }
                });
                builderSingle.show();
            } else if (tittleString.equalsIgnoreCase("Seasonal Analysis")) {
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(filename, "seasonal_calendar_analysis.csv");
                editor.commit();
                Intent io = new Intent(ToolReport.this, SampleActivity.class);
                startActivity(io);
            } else if (tittleString.equalsIgnoreCase("Livelihood Analysis")) {
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(filename, "livelihood_analysis.csv");
                editor.commit();
                Intent io = new Intent(ToolReport.this, SampleActivity.class);
                startActivity(io);
            } else if (tittleString.equalsIgnoreCase("Watershed Resources Analysis (forest, agriculture and other resources)")) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(ToolReport.this);
                builderSingle.setTitle("Make your choices:-");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ToolReport.this, android.R.layout.simple_list_item_1);
                arrayAdapter.add("Timber");
                arrayAdapter.add("NTFP");
                arrayAdapter.add("Wild life");
                arrayAdapter.add("Agriculture Crops");
                arrayAdapter.add("LiveStock");
                arrayAdapter.add("Crop Ranking");

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        if (strName.equalsIgnoreCase("Timber")) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(filename, "timber.csv");
                            editor.commit();
                        } else if (strName.equalsIgnoreCase("NTFP")) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(filename, "ntfp.csv");
                            editor.commit();
                        } else if (strName.equalsIgnoreCase("Wild life")) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(filename, "wild_life.csv");
                            editor.commit();
                        } else if (strName.equalsIgnoreCase("Agriculture Crops")) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(filename, "agriculture_crops.csv");
                            editor.commit();
                        } else if (strName.equalsIgnoreCase("LiveStock")) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(filename, "livestock.csv");
                            editor.commit();
                        } else if (strName.equalsIgnoreCase("Crop Ranking")) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(filename, "crop_ranking.csv");
                            editor.commit();
                        }
                        Intent io = new Intent(ToolReport.this, SampleActivity.class);
                        startActivity(io);
                    }
                });
                builderSingle.show();
            } else if (tittleString.equalsIgnoreCase("Institutional Analysis")) {
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(filename, "institutional_analysis.csv");
                editor.commit();
                Intent io = new Intent(ToolReport.this, SampleActivity.class);
                startActivity(io);
            } else if (tittleString.equalsIgnoreCase("S.W.O.T. Analysis")) {
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(filename, "swot.csv");
                editor.commit();
                Intent io = new Intent(ToolReport.this, SampleActivity.class);
                startActivity(io);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
