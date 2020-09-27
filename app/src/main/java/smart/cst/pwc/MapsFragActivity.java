package smart.cst.pwc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bkhezry.extramaputils.builder.ExtraMarkerBuilder;
import com.github.bkhezry.extramaputils.builder.ExtraPolygonBuilder;
import com.github.bkhezry.extramaputils.builder.ViewOptionBuilder;
import com.github.bkhezry.extramaputils.model.ExtraMarker;
import com.github.bkhezry.extramaputils.model.ViewOption;
import com.github.bkhezry.extramaputils.utils.MapUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import smart.cst.pwc.db.DbPlot;
import smart.cst.pwc.db.DbVrp;
import smart.cst.pwc.model.DataModel;
import smart.cst.pwc.model.DrawingOption;
import smart.cst.pwc.ui.BaseActivity;
import smart.cst.pwc.utils.CalUtils;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

//

public class MapsFragActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    public final static int REQUEST_CHECK_SETTINGS = 0;
    private Location currentLocation;
    private List<LatLng> points = new ArrayList<>();
    private List<Marker> markerList = new ArrayList<>();
    private Polygon polygon;
    private Polyline polyline;
    private ReactiveLocationProvider locationProvider;
    private Observable<Location> lastKnownLocationObservable;
    private Observable<Location> locationUpdatesObservable;
    private Subscription lastKnownLocationSubscription;
    private Subscription updatableLocationSubscription;
    private Marker currentMarker;
    private CompositeSubscription compositeSubscription;
    private final static String TAG = "MapsFragActivity";
    private boolean isGPSOn = false;
    private DrawingOption drawingOption;
    private View calLayout;
    private TextView areaTextView;
    private TextView lengthTextView;


    private CollapsingToolbarLayout collapsingToolbar;


    GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;

    private MultiSnapRecyclerView mRecyclerView;
    private PlotNewAdapter mRecyclerAdapter;
    ArrayList<PlotDetail> myList = new ArrayList<>();
    private DrawingOption.DrawingType currentDrawingType;
    String mapurl = "";
    String areaData = "";
    public ViewOption viewOption;
    String crntPlotName = "";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    public static final String plotid = "plotidKey";
    String vrpId = "";
    private ProgressDialog pDialog;
    private String tittleString;
    private Vrp vrp;
    DbVrp dbVrp;

    DbPlot dbPlot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mapfrg);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        dbVrp = new DbVrp(this);
        dbPlot = new DbPlot(this);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            tittleString = null;
        } else {
            tittleString = extras.getString("tittle");
        }

        getSupportActionBar().setTitle(tittleString);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(vrpid)) {
            vrpId = sharedpreferences.getString(vrpid, "").trim();
        }
        vrp = new Gson().fromJson(ConvertUtils.sample(dbVrp.getDataByvrpid(vrpId).get(1)), Vrp.class);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        currentDrawingType = DrawingOption.DrawingType.POLYGON;
        drawingOption = new DrawingOption(Double.parseDouble(vrp.getGeotag().split(",")[0]), Double.parseDouble(vrp.getGeotag().split(",")[1]), 9, Color.argb(10, 0, 0, 255),
                Color.argb(100, 255, 0, 0), 3, true, true, true, currentDrawingType);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUpFABs();
        setUpCalculateLayout();
        initRequestingLocation();
        if (drawingOption.getRequestGPSEnabling()) {
            requestActivatingGPS();
        }

        mRecyclerView = (MultiSnapRecyclerView) findViewById(R.id.first_recycler_view);
        mRecyclerAdapter = new PlotNewAdapter(MapsFragActivity.this, myList);
        final LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(firstManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putString(plotid, myList.get(position).getPlotid());
//                editor.commit();
//                Intent io = new Intent(MapsFragActivity.this, MainActivity.class);
//                startActivity(io);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getItems();
    }

    private void setUpFABs() {
        final FloatingActionButton btnSatellite = (FloatingActionButton) findViewById(R.id.btnSatellite);
        btnSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(mMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE ? GoogleMap.MAP_TYPE_NORMAL : GoogleMap.MAP_TYPE_SATELLITE);
                btnSatellite.setImageResource(mMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE ? R.drawable.ic_satellite_off : R.drawable.ic_satellite_on);
            }
        });

        btnSatellite.setVisibility(drawingOption.getEnableSatelliteView() ? View.VISIBLE : View.GONE);
        FloatingActionButton btnUndo = (FloatingActionButton) findViewById(R.id.btnUndo);
        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (points.size() > 0) {
                    Marker marker = markerList.get(markerList.size() - 1);
                    marker.remove();
                    markerList.remove(marker);
                    points.remove(points.size() - 1);
                    if (points.size() > 0) {
                        if (drawingOption.getDrawingType() == DrawingOption.DrawingType.POLYGON) {
                            drawPolygon(points);
                        } else if (drawingOption.getDrawingType() == DrawingOption.DrawingType.POLYLINE) {
                            drawPolyline(points);
                        }
                    }
                }
            }
        });
        final FloatingActionButton btnDone = (FloatingActionButton) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag().equals("plus")) {
                    btnDone.setImageResource(R.drawable.ic_done_black_24dp);
                    btnDone.setTag("done");
                } else {
                    btnDone.setImageResource(R.drawable.ic_add_black_24dp);
                    btnDone.setTag("plus");
                }
                returnCurrentPosition();
            }
        });
        FloatingActionButton btnGPS = (FloatingActionButton) findViewById(R.id.btnGPS);
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGPSOn) {
                    requestActivatingGPS();
                } else {
                    if (compositeSubscription != null && locationUpdatesObservable != null) {
                        getLastKnowLocation();
                    }
                }
            }
        });
    }

    private void setUpCalculateLayout() {
        calLayout = findViewById(R.id.calculate_layout);
        calLayout.setVisibility(drawingOption.getEnableCalculateLayout() ? View.VISIBLE : View.GONE);
        areaTextView = (TextView) findViewById(R.id.areaTextView);
        lengthTextView = (TextView) findViewById(R.id.lengthTextView);
    }


    private void initRequestingLocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
        compositeSubscription = new CompositeSubscription();
        locationProvider = new ReactiveLocationProvider(getApplicationContext());
        lastKnownLocationObservable = locationProvider.getLastKnownLocation();
    }


    private void requestActivatingGPS() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(5)
                .setInterval(100);
        locationUpdatesObservable = locationProvider.getUpdatedLocation(locationRequest);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        getLastKnowLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MapsFragActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e(TAG, "Error happen during show Dialog for Turn of GPS");
                        break;
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng center = new LatLng(drawingOption.getLocationLatitude(), drawingOption.getLocationLongitude());
        myLocationMarker(center);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, drawingOption.getZoom()));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                @IdRes int icon = R.drawable.ic_add_location_light_green_500_36dp;
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(getBitmapFromDrawable(MapsFragActivity.this, icon));
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(bitmap).draggable(true));
                marker.setTag(latLng);
                markerList.add(marker);
                points.add(latLng);
                if (drawingOption.getDrawingType() == DrawingOption.DrawingType.POLYGON) {
                    drawPolygon(points);
                    setAreaLength(points);
                } else if (drawingOption.getDrawingType() == DrawingOption.DrawingType.POLYLINE) {
                    drawPolyline(points);
                    setLength(points);
                }


            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                updateMarkerLocation(marker, false);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                updateMarkerLocation(marker, true);
            }
        });


        prepareData();

    }

    private void updateMarkerLocation(Marker marker, boolean calculate) {
        LatLng latLng = (LatLng) marker.getTag();
        int position = points.indexOf(latLng);
        points.set(position, marker.getPosition());
        marker.setTag(marker.getPosition());
        if (drawingOption.getDrawingType() == DrawingOption.DrawingType.POLYGON) {
            drawPolygon(points);
            if (calculate)
                setAreaLength(points);
        } else if (drawingOption.getDrawingType() == DrawingOption.DrawingType.POLYLINE) {
            drawPolyline(points);
            if (calculate)
                setLength(points);
        }
    }


    private void drawPolyline(List<LatLng> latLngList) {
        if (polyline != null) {
            polyline.remove();
        }
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(drawingOption.getStrokeColor());
        polylineOptions.width(drawingOption.getStrokeWidth());
        polylineOptions.addAll(latLngList);
        polyline = mMap.addPolyline(polylineOptions);
    }


    private void drawPolygon(List<LatLng> latLngList) {
        if (polygon != null) {
            polygon.remove();
        }
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.fillColor(drawingOption.getFillColor());
        polygonOptions.strokeColor(drawingOption.getStrokeColor());
        polygonOptions.strokeWidth(drawingOption.getStrokeWidth());
        polygonOptions.addAll(latLngList);
        polygon = mMap.addPolygon(polygonOptions);
    }

    @Override
    protected void onLocationPermissionGranted() {
        getLastKnowLocation();
        updateLocation();
    }

    private void updateLocation() {
        if (locationUpdatesObservable != null && compositeSubscription != null) {
            updatableLocationSubscription = locationUpdatesObservable
                    .subscribe(new Action1<Location>() {
                        @Override
                        public void call(Location location) {
                            if (currentLocation == null)
                                moveMapToCenter(location);

                            currentLocation = location;
                            moveMarkerCurrentPosition(location);
                        }
                    }, new ErrorHandler());
            compositeSubscription.add(updatableLocationSubscription);
        }
    }

    private void getLastKnowLocation() {
        if (lastKnownLocationObservable != null && compositeSubscription != null) {
            lastKnownLocationSubscription =
                    lastKnownLocationObservable
                            .subscribe(new Action1<Location>() {
                                @Override
                                public void call(Location location) {
                                    currentLocation = location;
                                    moveMapToCenter(location);
                                }
                            }, new ErrorHandler());
            compositeSubscription.add(lastKnownLocationSubscription);
        }
    }


    private class ErrorHandler implements Action1<Throwable> {
        @Override
        public void call(Throwable throwable) {
            Toast.makeText(MapsFragActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Error occurred", throwable);
        }
    }

    public void moveMapToCenter(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (mMap != null) {
//            myLocationMarker(latLng);
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    public void moveMarkerCurrentPosition(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (mMap != null) {
            // myLocationMarker(latLng);
        }
    }

    private void myLocationMarker(LatLng latLng) {
        if (currentMarker != null) {
            currentMarker.setPosition(latLng);
        } else {
            @IdRes int icon = R.drawable.ic_home_black_24dp;
            BitmapDescriptor bitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            currentMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(vrp.getName())
                    .icon(bitmap)
                    .draggable(false));
            currentMarker.showInfoWindow();
            if (mMap != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (lastKnownLocationSubscription != null && updatableLocationSubscription != null && compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscription.clear();
            updatableLocationSubscription.unsubscribe();
            lastKnownLocationSubscription.unsubscribe();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        // All required changes were successfully made
                        Log.d(TAG, "User enabled location");
                        getLastKnowLocation();
                        updateLocation();
                        isGPSOn = true;
                        break;
                    case RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Log.d(TAG, "User Cancelled enabling location");
                        isGPSOn = false;
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void returnCurrentPosition() {
        if (points.size() > 0) {
            //Intent returnIntent = new Intent();
            LatLng[] latLngs = new LatLng[points.size()];
            points.toArray(latLngs);
            DataModel dataModel = new DataModel();
            dataModel.setCount(points.size());
            dataModel.setPoints(latLngs);
            //   returnIntent.putExtra(POINTS, dataModel);
            // setResult(RESULT_OK, returnIntent);
            resultFunction(dataModel);
        } else {
            setResult(RESULT_CANCELED);
        }
        // finish();
    }

    private static Bitmap getBitmapFromDrawable(Context context, int icon) {
        Drawable drawable = ContextCompat.getDrawable(context, icon);
        Bitmap obm = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(obm);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return obm;
    }

    private void setAreaLength(List<LatLng> points) {
        areaTextView.setText(getString(R.string.area_label) + String.format(Locale.ENGLISH, "%.2f", CalUtils.getArea(points)) + getString(R.string.mm_label));
        lengthTextView.setText(getString(R.string.length_label) + String.format(Locale.ENGLISH, "%.2f", CalUtils.getLength(points)) + getString(R.string.m_label));
    }

    private void setLength(List<LatLng> points) {
        lengthTextView.setText(getString(R.string.length_label) + String.format(Locale.ENGLISH, "%.2f", CalUtils.getLength(points)) + getString(R.string.m_label));
    }


    private void resultFunction(final DataModel dataModel) {

        if (currentDrawingType == DrawingOption.DrawingType.POLYGON) {
            if (dataModel.getPoints().length >= 3) {
                ViewOption viewOption = new ViewOptionBuilder()
                        .withIsListView(false)
                        .withPolygons(
                                new ExtraPolygonBuilder()
                                        .setFillColor(Color.argb(10, 0, 0, 255))
                                        .setPoints(dataModel.getPoints())
                                        .setStrokeColor(Color.argb(100, 255, 0, 0))
                                        .setStrokeWidth(5)
                                        .setzIndex(9)
                                        .build())
                        .withMarkers(getMarkers(dataModel.getPoints()))
                        .withMapsZoom(12)
                        .build();
                StringBuilder pointsurl = new StringBuilder();
                for (int k = 0; k < viewOption.getPolygons().get(0).getPoints().length; k++) {
                    pointsurl.append("|");
                    pointsurl.append(String.valueOf(viewOption.getPolygons().get(0).getPoints()[k].latitude));
                    pointsurl.append(",");
                    pointsurl.append(String.valueOf(viewOption.getPolygons().get(0).getPoints()[k].longitude));
                }
                mapurl = "http://maps.googleapis.com/maps/api/staticmap?" +
                        "zoom=17&size=600x600&maptype=satellite&sensor=false&path=color%3ared|weight:1|fillcolor%3awhite" +
                        pointsurl.toString();

                ConvertUtils.urltoDatamodel(mapurl);
                areaData = String.valueOf(Math.round(viewOption.getPolygons().get(0).getArea() * 0.000247105 * 100.0) / 100.0) + " Acre";
                this.viewOption = viewOption;
                if (mMap != null) {
                    MapUtils.showElements(viewOption, mMap, this);
                }
                JSONObject fPlotDetail = new JSONObject();
                try {
                    fPlotDetail.put("plotname", "Plot " + String.valueOf(myList.size() + 1));
                    fPlotDetail.put("plotarea", areaData);
                    fPlotDetail.put("ploturl", mapurl);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

                Plot plot = new Plot(null, "Plot " + String.valueOf(myList.size() + 1),
                        mapurl, areaData, null);

                dbPlot.addData(vrpId, tittleString, plot.plotname, new Gson().toJson(plot));

                final PlotDetail mLog = new PlotDetail();
                mLog.basicPlotDetail("plot", fPlotDetail.toString());
                myList.add(mLog);
                mRecyclerAdapter.notifyData(myList);
                mRecyclerView.setVisibility(View.VISIBLE);
                points = new ArrayList<>();
                for (int i = 0; i < markerList.size(); i++) {
                    markerList.get(i).remove();
                }
                markerList = new ArrayList<>();

            }
        } else {
            Toast.makeText(getApplicationContext(), "Please select atleast 3 points", Toast.LENGTH_SHORT).show();
        }

    }

    private void getItems() {
        List<String> history = new ArrayList<>();
        if (tittleString.equals("villgeprofile")) {
            history = dbPlot.getAllData(vrpId);
        } else {
            history = dbPlot.getAllData(vrpId, tittleString);
        }
        for (int i = 0; i < history.size(); i++) {
            Plot plot = new Gson().fromJson(history.get(i), Plot.class);
            JSONObject fPlotDetail = new JSONObject();
            try {
                fPlotDetail.put("plotname", plot.plotname);
                fPlotDetail.put("plotarea", plot.plotarea);
                fPlotDetail.put("ploturl", plot.plotimage);
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
            final PlotDetail mLog = new PlotDetail();
            mLog.basicPlotDetail("plot", fPlotDetail.toString());
            myList.add(mLog);
        }
        if (history.size() > 0) {
            mRecyclerAdapter.notifyData(myList);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    private List<ExtraMarker> getMarkers(LatLng[] points) {
        List<ExtraMarker> extraMarkers = new ArrayList<>();
        @IdRes int icon = R.drawable.ic_beenhere_blue_grey_500_24dp;
        for (LatLng latLng : points) {
            ExtraMarker extraMarker =
                    new ExtraMarkerBuilder()
                            .setCenter(latLng)
                            .setIcon(icon)
                            .build();
            extraMarkers.add(extraMarker);
        }
        ExtraMarker extraMarker =
                new ExtraMarkerBuilder()
                        .setCenter(computeCentroid(points))
                        .setIcon(icon)
                        .build();
        extraMarkers.add(extraMarker);


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(computeCentroid(points));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        markerOptions.title("Plot " + String.valueOf(myList.size() + 1));
        mMap.addMarker(markerOptions).showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                computeCentroid(points), 9));

        return extraMarkers;
    }


    private LatLng computeCentroid(LatLng[] points) {
        double latitude = 0;
        double longitude = 0;
        int n = points.length;

        for (LatLng point : points) {
            latitude += point.latitude;
            longitude += point.longitude;
        }

        return new LatLng(latitude / n, longitude / n);
    }


    public void prepareData() {
//        if (checkInternetConnection()) {
//            getAllPlot(farmerId);
//        } else {
//            currentDrawingType = DrawingOption.DrawingType.POLYGON;
//            myList = new ArrayList<>();
//            mRecyclerView.setVisibility(View.VISIBLE);
////            List<ArrayList<String>> plotList = dbPlot.getAllPlotData();
////            showAllPlot(plotList);
//        }

    }

    //    private void showAllPlot(List<ArrayList<String>> plotList) {
//        for (int i = 0; i < plotList.size(); i++) {
//            ArrayList<String> resultPlot = plotList.get(i);
//            PlotDetail plot = new PlotDetail();
//            plot.basicPlotDetail(resultPlot.get(0), resultPlot.get(1));
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = new JSONObject(resultPlot.get(1).toString());
//                ViewOption viewOption = new ViewOptionBuilder()
//                        .withIsListView(false)
//                        .withPolygons(
//                                new ExtraPolygonBuilder()
//                                        .setFillColor(Color.argb(10, 0, 0, 255))
//                                        .setPoints(ConvertUtils.urltoDatamodel(jsonObject.getString("ploturl")).getPoints())
//                                        .setStrokeColor(Color.argb(100, 255, 0, 0))
//                                        .setStrokeWidth(5)
//                                        .setzIndex(9)
//                                        .build())
//                        .withMarkers(getMarkers(ConvertUtils.urltoDatamodel(jsonObject.getString("ploturl")).getPoints()))
//                        .withMapsZoom(12)
//                        .build();
//                if (mMap != null) {
//                    MapUtils.showElements(viewOption, mMap, this);
//                }
//            } catch (JSONException e) {
//                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//            myList.add(plot);
//        }
//        mRecyclerAdapter.notifyData(myList);
//    }
//
//
//    private void getAllPlot(final String mfarmerid) {
//        // Tag used to cancel the request
//        String tag_string_req = "req_all";
//
//        pDialog.setMessage("Fetching ...");
//        showDialog();
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                AppConfig.URL_PLOT_ALL, new Response.Listener<String>() {
//
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Fetch Response: " + response.toString());
//                hideDialog();
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//                    if (!error) {
//                        dbPlot.deleteAll();
//                        JSONArray plots = jObj.getJSONArray("plots");
//                        for (int i = 0; i < plots.length(); i++) {
//                            JSONObject plot = plots.getJSONObject(i);
//                            dbPlot.addPlot(plot.getString("plotid"), plot.getString("fplotdetail")
//                                    , plot.getString("splotdetail")
//                                    , plot.getString("soildetail"), plot.getString("waterdetail")
//                                    , plot.getString("cropdetail")
//                            );
//                        }
//                        showAllPlot(dbPlot.getAllPlotData());
//                    } else {
//                        String errorMsg = jObj.getString("message");
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Registration Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting params to register url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("farmerid", mfarmerid);
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
//    }
//
//
//    private void registerPlot(final String mfarmerid, final String mplotid,
//                              final String mfplotdetail, final boolean mUpdate) {
//        // Tag used to cancel the request
//        String tag_string_req = "req_fplotdetail";
//
//        pDialog.setMessage("Registering ...");
//        showDialog();
//        String url;
//        if (mUpdate) {
//            url = AppConfig.URL_PLOT_UPDATE;
//        } else {
//            url = AppConfig.URL_PLOT_CREATE;
//        }
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Register Response: " + response.toString());
//                hideDialog();
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//                    if (!error) {
//                        if (mUpdate) {
//
//                        } else {
//
//                            final PlotDetail mLog = new PlotDetail();
//                            mLog.basicPlotDetail(mplotid, mfplotdetail.toString());
//                            myList.add(mLog);
//                            dbPlot.addFplotDetail(mplotid, mfplotdetail.toString());
//                            mRecyclerAdapter.notifyData(myList);
//                            mRecyclerView.setVisibility(View.VISIBLE);
//                            points = new ArrayList<>();
//                            for (int i = 0; i < markerList.size(); i++) {
//                                markerList.get(i).remove();
//                            }
//                            markerList = new ArrayList<>();
//
//                        }
//                    } else {
//                        String errorMsg = jObj.getString("message");
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Registration Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting params to register url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("farmerid", mfarmerid);
//                params.put("plotid", mplotid);
//                params.put("fplotdetail", mfplotdetail);
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
//    }
//
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}
