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

public class AvgrateAdapter extends RecyclerView.Adapter<AvgrateAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Avgrate> avgrateList;
    private OnFGDItemClick onFGDItemClick;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView cropname,min,max;
        LinearLayout parentLinear;

        public MyViewHolder(View view){
            super((view));
            cropname =(TextView) view.findViewById(R.id.cropname);
            min =(TextView) view.findViewById(R.id.min);
            max =(TextView) view.findViewById(R.id.max);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public AvgrateAdapter(Context mainActivityUser, ArrayList<Avgrate> avgrateList, MainActivityFGD onItemClick) {
        this.mainActivityUser = mainActivityUser;
        this.avgrateList = avgrateList;
        this.onFGDItemClick = (OnFGDItemClick) onItemClick;
    }

    public void notifyData(ArrayList<Avgrate> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.avgrateList = myList;
        notifyDataSetChanged();
    }
    public AvgrateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.avgraterow, parent, false);

        return new AvgrateAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AvgrateAdapter.MyViewHolder holder, final int position) {
        Avgrate bean = avgrateList.get(position);
        holder.cropname.setText(bean.cropname);
        holder.min.setText(bean.min);
        holder.max.setText(bean.max);


        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFGDItemClick.itemAvgrateClick(position);
            }
        });
    }
    public  int getItemCount(){
        return avgrateList.size();
    }

}
