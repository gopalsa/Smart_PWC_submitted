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

public class SourceWaterAddapter extends RecyclerView.Adapter<SourceWaterAddapter.MyViewHolder> {

        private Context mainActivityUser;
        private ArrayList<SourceWater> WaterList;
        private OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView sourWater,option,distance;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                sourWater =(TextView) view.findViewById(R.id.sourWater);
                option =(TextView) view.findViewById(R.id.option);
                distance =(TextView) view.findViewById(R.id.distance);


                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
    public SourceWaterAddapter(Context mainActivityUser, ArrayList<SourceWater> moviesList, OnItemClick onItemClick) {
            this.WaterList = moviesList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
    public void notifyData(ArrayList<SourceWater> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.WaterList = myList;
        notifyDataSetChanged();
    }
    public SourceWaterAddapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sourcewater_row, parent, false);

        return new SourceWaterAddapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SourceWaterAddapter.MyViewHolder holder, final int position) {
        SourceWater bean = WaterList.get(position);
        holder.sourWater.setText(bean.sourWater);
        holder.option.setText(bean.option);
        holder.distance.setText(bean.distance);



        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemSourceWaterClick(position);
            }
        });
    }
    public  int getItemCount(){
        return WaterList.size();
    }

}
