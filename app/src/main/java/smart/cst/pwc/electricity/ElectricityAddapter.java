package smart.cst.pwc.electricity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import smart.cst.pwc.R;


/**
 * Created by arthi on 11/27/2018.
 */

public class ElectricityAddapter extends RecyclerView.Adapter<ElectricityAddapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Electricity> electricityList;
    private ElectricityClick onItemClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView communityPlace, duration, appliance, count;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super((view));
            communityPlace = (TextView) view.findViewById(R.id.communityPlace);
            duration = (TextView) view.findViewById(R.id.duration);
            appliance = (TextView) view.findViewById(R.id.appliance);
            count = (TextView) view.findViewById(R.id.count);

            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

        }
    }

    public ElectricityAddapter(Context mainActivityUser, ArrayList<Electricity> moviesList, ElectricityClick onItemClick) {
        this.electricityList = moviesList;
        this.mainActivityUser = mainActivityUser;
        this.onItemClick = onItemClick;
    }

    public void notifyData(ArrayList<Electricity> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.electricityList = myList;
        notifyDataSetChanged();
    }

    public ElectricityAddapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.electricity_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ElectricityAddapter.MyViewHolder holder, final int position) {
        Electricity bean = electricityList.get(position);
        holder.communityPlace.setText(bean.name);
        holder.appliance.setText(bean.electricalAppliances);
        holder.count.setText(bean.eCount);
        holder.duration.setText(bean.eHousrs);


        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position);
            }
        });
    }

    public int getItemCount() {
        return electricityList.size();
    }

}
