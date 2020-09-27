package smart.cst.pwc.household;

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

public class EnergyPowerAddapter extends RecyclerView.Adapter<EnergyPowerAddapter.MyViewHolder> {

        private Context mainActivityUser;
        private ArrayList<EnergyPower> EnergyList;
        private OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView application,nos,duration;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                application =(TextView) view.findViewById(R.id.application);
                nos =(TextView) view.findViewById(R.id.nos);
                duration =(TextView) view.findViewById(R.id.duration);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
    public EnergyPowerAddapter(Context mainActivityUser, ArrayList<EnergyPower> moviesList, OnItemClick onItemClick) {
            this.EnergyList = moviesList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
    public void notifyData(ArrayList<EnergyPower> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.EnergyList = myList;
        notifyDataSetChanged();
    }
    public EnergyPowerAddapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.energypower_row, parent, false);

        return new EnergyPowerAddapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EnergyPowerAddapter.MyViewHolder holder, final int position) {
        EnergyPower bean = EnergyList.get(position);
        holder.application.setText(bean.application);
        holder.nos.setText(bean.nos);
        holder.duration.setText(bean.duration);


        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemEnergyPowerClick(position);
            }
        });
    }
    public  int getItemCount(){
        return EnergyList.size();
    }

}
