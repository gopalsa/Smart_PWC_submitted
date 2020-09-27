package smart.cst.pwc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import smart.cst.pwc.app.GlideApp;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Plot> moviesList;


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


    public ProfileAdapter(Context mainActivityUser, ArrayList<Plot> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;
    }

    public void notifyData(ArrayList<Plot> myList) {
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
        Plot plot = moviesList.get(position);
        holder.plotname.setText(plot.getPlotname());
        holder.plotarea.setText(plot.getPlotarea());
        GlideApp.with(mainActivityUser).load(plot.getPlotimage())
                .dontAnimate()
                .thumbnail(0.5f)
                .placeholder(R.drawable.profile)
                .into(holder.plotimg);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
