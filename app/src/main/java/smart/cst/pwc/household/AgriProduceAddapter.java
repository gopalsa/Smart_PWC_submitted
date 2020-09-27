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

public class AgriProduceAddapter extends RecyclerView.Adapter<AgriProduceAddapter.MyViewHolder> {

        private Context mainActivityUser;
        private ArrayList<AgriProduce> AgriproList;
        private OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView crop,cropPrev,product;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                crop =(TextView) view.findViewById(R.id.crop);
                cropPrev =(TextView) view.findViewById(R.id.cropPrev);
                product =(TextView) view.findViewById(R.id.product);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
    public AgriProduceAddapter(Context mainActivityUser, ArrayList<AgriProduce> moviesList, OnItemClick onItemClick) {
            this.AgriproList = moviesList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
    public void notifyData(ArrayList<AgriProduce> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.AgriproList = myList;
        notifyDataSetChanged();
    }
    public AgriProduceAddapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.agriproduce_row, parent, false);

        return new AgriProduceAddapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AgriProduceAddapter.MyViewHolder holder, final int position) {
        AgriProduce bean = AgriproList.get(position);
        holder.crop.setText(bean.crop);
        holder.cropPrev.setText(bean.cropPrev);
        holder.product.setText(bean.product);


        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemAgriproduceClick(position);
            }
        });
    }
    public  int getItemCount(){
        return AgriproList.size();
    }

}
