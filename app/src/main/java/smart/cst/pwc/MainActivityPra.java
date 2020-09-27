package smart.cst.pwc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import smart.cst.pwc.household.CustClusterHouseHoldActivity;
import smart.cst.pwc.survey.PieChartActivity;

public class MainActivityPra extends AppCompatActivity implements VideoClick {
    private List<Pra> praList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PraAdapter mAdapter;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new PraAdapter(praList, this, this);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        prepareMovieData();
    }

    private void prepareMovieData() {

        Pra pra = new Pra("Historical Timeline", "-07fOqnyXz8", "",
                "https://sites.google.com/site/faopratoolkit/historical-tiemline");
        praList.add(pra);

        pra = new Pra("Gender Analysis", "Cobx2T95et0", "", "https://sites.google.com/site/faopratoolkit/gender-analysis");
        praList.add(pra);

        pra = new Pra("Village Resource Mapping (Village, water and soil maps)", "wlRoYJ2-U2g", "", "https://sites.google.com/site/faopratoolkit/village-resource-mapping");
        praList.add(pra);

        pra = new Pra("Transect Walk", "O0wa42N5Rtw", "", "https://sites.google.com/site/faopratoolkit/transect-walk");
        praList.add(pra);

        pra = new Pra("Seasonal Analysis", "kQPVKviGoKY", "", "https://sites.google.com/site/faopratoolkit/seasonal-analysis");
        praList.add(pra);

        pra = new Pra("Watershed Resources Analysis (forest, agriculture and other resources)", "Irt5E-pbflw", "", "https://sites.google.com/site/faopratoolkit/forest-resource-analysis");
        praList.add(pra);

        pra = new Pra("Livelihood Analysis", "zua01rllZsI", "", "https://sites.google.com/site/faopratoolkit/livelihood-analysis");
        praList.add(pra);

        pra = new Pra("Institutional Analysis", "null", "", "");
        praList.add(pra);

        pra = new Pra("S.W.O.T. Analysis", "29cG63A1_Yc", "", "https://sites.google.com/site/faopratoolkit/s-w-o-t");
        praList.add(pra);

        pra = new Pra("Wealth Ranking", "GY05yaiVu6Q", "", "https://sites.google.com/site/faopratoolkit/wealth-ranking");
        praList.add(pra);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void videoClick(int position) {
        if (praList.get(position).getYear().equals("true")) {
            Intent io = new Intent(MainActivityPra.this, TeamMember.class);
            startActivity(io);
        } else {
            Intent io = new Intent(MainActivityPra.this, MainActivityVideo.class);
            io.putExtra("video", praList.get(position).getGenre());
            startActivity(io);
        }
    }

    @Override
    public void webClick(int position) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(praList.get(position).getUrl()));
            startActivity(browserIntent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Invalid Link", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void mapClick(int position) {
        Intent io = new Intent(MainActivityPra.this, MapsFragActivity.class);
        io.putExtra("tittle", praList.get(position).getTitle());
        startActivity(io);
    }

    @Override
    public void imageClick(int position) {
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(tittle, praList.get(position).getTitle());
        editor.commit();
        if (praList.get(position).getTitle().equals("Household survey")) {
            Intent io = new Intent(MainActivityPra.this, CustClusterHouseHoldActivity.class);
            io.putExtra("tittle", praList.get(position).getTitle());
            startActivity(io);
        } else {
            Intent io = new Intent(MainActivityPra.this, CustomMarkerClusteringDemoActivity.class);
            io.putExtra("tittle", praList.get(position).getTitle());
            startActivity(io);
        }
    }

    @Override
    public void reportClick(int position) {
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(tittle, praList.get(position).getTitle());
        editor.commit();
        if (praList.get(position).getTitle().equals("Household survey")) {
            Intent io = new Intent(MainActivityPra.this, PieChartActivity.class);
            io.putExtra("tittle", praList.get(position).getTitle());
            startActivity(io);
        } else {
            Intent io = new Intent(MainActivityPra.this, FinalReport.class);
            startActivity(io);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {
            Intent io = new Intent(MainActivityPra.this, ProfileActivity.class);
            startActivity(io);
            return true;
        }
        if (id == R.id.upload) {
            Intent intent = new Intent(MainActivityPra.this, UploadActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
