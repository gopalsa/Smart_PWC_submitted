package smart.cst.pwc.fgd;

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
 * Created by jmpriyanka on 01-12-2018.
 */

public class AvgwageAdapter extends RecyclerView.Adapter<AvgwageAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Avgwage> avgwageList;
    private OnFGDItemClick onFGDItemClick;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView activity,male,female;
        LinearLayout parentLinear;

        public MyViewHolder(View view){
            super((view));
            activity =(TextView) view.findViewById(R.id.activity);
            male =(TextView) view.findViewById(R.id.male);
            female =(TextView) view.findViewById(R.id.female);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public AvgwageAdapter(Context mainActivityUser, ArrayList<Avgwage> avgwageList, MainActivityFGD onItemClick) {
        this.mainActivityUser = mainActivityUser;
        this.avgwageList = avgwageList;
        this.onFGDItemClick = (OnFGDItemClick) onItemClick;
    }

    public void notifyData(ArrayList<Avgwage> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.avgwageList = myList;
        notifyDataSetChanged();
    }
    public AvgwageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.avgwagerow, parent, false);

        return new AvgwageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AvgwageAdapter.MyViewHolder holder, final int position) {
        Avgwage bean = avgwageList.get(position);
        holder.activity.setText(bean.activity);
        holder.male.setText(bean.male);
        holder.female.setText(bean.female);


        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFGDItemClick.itemAvgwageClick(position);
            }
        });
    }
    public  int getItemCount(){
        return avgwageList.size();
    }

}
