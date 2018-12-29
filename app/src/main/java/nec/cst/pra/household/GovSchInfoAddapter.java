package nec.cst.pra.household;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nec.cst.pra.R;

/**
 * Created by arthi on 11/27/2018.
 */

public class GovSchInfoAddapter extends RecyclerView.Adapter<GovSchInfoAddapter.MyViewHolder> {

        private Context mainActivityUser;
        private ArrayList<GovSchInfo> GovList;
        private OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView govName,govpersonBenfit;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                govName =(TextView) view.findViewById(R.id.govName);
                govpersonBenfit =(TextView) view.findViewById(R.id.govpersonBenfit);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
    public GovSchInfoAddapter(Context mainActivityUser, ArrayList<GovSchInfo> moviesList, OnItemClick onItemClick) {
            this.GovList = moviesList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
    public void notifyData(ArrayList<GovSchInfo> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.GovList = myList;
        notifyDataSetChanged();
    }
    public GovSchInfoAddapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.govschinfo_row, parent, false);

        return new GovSchInfoAddapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GovSchInfoAddapter.MyViewHolder holder, final int position) {
        GovSchInfo bean = GovList.get(position);
        holder.govName.setText(bean.govName);
        holder.govpersonBenfit.setText(bean.govpersonBenfit);



        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemGovschClick(position);
            }
        });
    }
    public  int getItemCount(){
        return GovList.size();
    }

}
