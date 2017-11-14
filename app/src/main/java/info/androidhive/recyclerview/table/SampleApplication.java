package info.androidhive.recyclerview.table;

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }
}
