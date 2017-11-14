package info.androidhive.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlotNewAdapter extends RecyclerView.Adapter<PlotNewAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<PlotDetail> moviesList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView plotimg;
        private final TextView plotname;
        TextView plotarea;

        public MyViewHolder(View view) {
            super(view);
            plotimg = (CircleImageView) view.findViewById(R.id.profile_image);
            plotname = (TextView) view.findViewById(R.id.plotname);
            plotarea = (TextView) view.findViewById(R.id.areaofplot);

        }
    }


    public PlotNewAdapter(Context mainActivityUser, ArrayList<PlotDetail> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;
    }

    public void notifyData(ArrayList<PlotDetail> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plot_horizontal, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PlotDetail plotDetail = moviesList.get(position);
        try {
            JSONObject fPlotDetailJson = new JSONObject(plotDetail.getFPlotDetail().toString());
            holder.plotname.setText(fPlotDetailJson.getString("plotname"));
            holder.plotarea.setText(fPlotDetailJson.getString("plotarea"));
            Glide.with(mainActivityUser).load(fPlotDetailJson.getString("ploturl"))
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.profile)
                    .into(holder.plotimg);
        } catch (JSONException e) {
            Toast.makeText(mainActivityUser, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
