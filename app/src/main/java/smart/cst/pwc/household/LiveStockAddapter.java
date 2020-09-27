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

public class LiveStockAddapter extends RecyclerView.Adapter<LiveStockAddapter.MyViewHolder> {

        private Context mainActivityUser;
        private ArrayList<LiveStock> LiveList;
        private OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView animals,shelter,average,wasteKgs;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                animals =(TextView) view.findViewById(R.id.animals);
                shelter =(TextView) view.findViewById(R.id.shelter);
                average =(TextView) view.findViewById(R.id.average);
                wasteKgs =(TextView) view.findViewById(R.id.wasteKgs);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
    public LiveStockAddapter(Context mainActivityUser, ArrayList<LiveStock> moviesList, OnItemClick onItemClick) {
            this.LiveList = moviesList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
    public void notifyData(ArrayList<LiveStock> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.LiveList = myList;
        notifyDataSetChanged();
    }
    public LiveStockAddapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.livestock_row, parent, false);

        return new LiveStockAddapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LiveStockAddapter.MyViewHolder holder, final int position) {
        LiveStock bean = LiveList.get(position);
        holder.animals.setText(bean.animals);
        holder.shelter.setText(bean.shelter);
        holder.average.setText(bean.average);
        holder.wasteKgs.setText(bean.wasteKgs);


        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemLiveStockClick(position);
            }
        });
    }
    public  int getItemCount(){
        return LiveList.size();
    }

}
