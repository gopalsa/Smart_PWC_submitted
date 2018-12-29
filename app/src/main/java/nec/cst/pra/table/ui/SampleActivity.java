package nec.cst.pra.table.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import java.io.File;

import nec.cst.pra.R;

public class SampleActivity extends AppCompatActivity implements
        CsvPickerFragment.OnCsvFileSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, CsvPickerFragment.newInstance(), CsvPickerFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onCsvFileSelected(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            File file = new File(fileName);
            if (file.exists() && fileName.endsWith(".csv")) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, TableLayoutFragment.newInstance(fileName), CsvPickerFragment.class.getSimpleName())
                        .addToBackStack(CsvPickerFragment.class.getSimpleName())
                        .commit();
            } else {
                Toast.makeText(this, R.string.not_csv_file_error, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.no_such_file_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            this.finish();
        } else {
            this.finish();
        }
    }
}
