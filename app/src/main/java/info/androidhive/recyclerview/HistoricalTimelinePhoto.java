package info.androidhive.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Gopal on 08-11-2017.
 */

public class HistoricalTimelinePhoto extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_timeline_photo);
        TextView submit=(TextView)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(HistoricalTimelinePhoto.this, AdditionalInformation.class);
                startActivity(io);
            }
        });
    }
}
