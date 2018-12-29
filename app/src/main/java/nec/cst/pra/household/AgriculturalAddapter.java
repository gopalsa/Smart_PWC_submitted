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

public class AgriculturalAddapter extends RecyclerView.Adapter<AgriculturalAddapter.MyViewHolder> {

        private Context mainActivityUser;
        private ArrayList<Agricultural> AgriList;
        private OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView particulars,tickAppro,fertilizer;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                particulars =(TextView) view.findViewById(R.id.particulars);
                tickAppro =(TextView) view.findViewById(R.id.tickAppro);
                fertilizer =(TextView) view.findViewById(R.id.fertilizer);

                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
    public AgriculturalAddapter(Context mainActivityUser, ArrayList<Agricultural> moviesList, OnItemClick onItemClick) {
            this.AgriList = moviesList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
    public void notifyData(ArrayList<Agricultural> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.AgriList = myList;
        notifyDataSetChanged();
    }
    public AgriculturalAddapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.agricultural_row, parent, false);

        return new AgriculturalAddapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AgriculturalAddapter.MyViewHolder holder, final int position) {
        Agricultural bean = AgriList.get(position);
        holder.particulars.setText(bean.particulars);
        holder.tickAppro.setText(bean.tickAppro);
        holder.fertilizer.setText(bean.fertilizer);


        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemAgriClick(position);
            }
        });
    }
    public  int getItemCount(){
        return AgriList.size();
    }

}
