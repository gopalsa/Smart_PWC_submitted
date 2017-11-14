package info.androidhive.recyclerview.app;

import android.app.Application;
import android.text.TextUtils;

import com.ahmedjazzar.rosetta.LanguageSwitcher;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.appevents.AppEventsLogger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    private Locale firstLaunchLocale;
    private HashSet<Locale> supportedLocales;
    public static LanguageSwitcher languageSwitcher;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppEventsLogger.activateApp(this);
        init();
    }


    private void init() {
        AutomatedSupportedLocales();
        manualSupportedLocales();

        languageSwitcher = new LanguageSwitcher(this, firstLaunchLocale);
        languageSwitcher.setSupportedLocales(supportedLocales);
    }

    private void AutomatedSupportedLocales() {

    }

    private void manualSupportedLocales() {
        // This is the locale that you wanna your app to launch with.
        firstLaunchLocale = new Locale("en");

        // You can use a HashSet<String> instead and call 'setSupportedStringLocales()' :)
        supportedLocales = new HashSet<>();
        supportedLocales.add(new Locale("en"));
        supportedLocales.add(new Locale("ta"));
        supportedLocales.add(new Locale("ml"));
        supportedLocales.add(new Locale("kn"));
        supportedLocales.add(new Locale("te"));
        supportedLocales.add(new Locale("mr"));
        supportedLocales.add(new Locale("or"));
        supportedLocales.add(new Locale("bn"));
        supportedLocales.add(new Locale("as"));
        supportedLocales.add(new Locale("my"));
        supportedLocales.add(new Locale("th"));
        supportedLocales.add(new Locale("ms"));
        supportedLocales.add(new Locale("km"));
        supportedLocales.add(new Locale("vi"));
        supportedLocales.add(new Locale("zh"));
        supportedLocales.add(new Locale("pt"));
        supportedLocales.add(new Locale("fr"));
        supportedLocales.add(new Locale("es"));
        supportedLocales.add(new Locale("sw"));
    }

    Map<String ,String> lang=new HashMap<>();

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}