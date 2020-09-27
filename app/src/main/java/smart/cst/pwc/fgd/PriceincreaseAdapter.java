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

public class PriceincreaseAdapter extends RecyclerView.Adapter<PriceincreaseAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Priceincrease> priceincrList;
    private OnFGDItemClick onFGDItemClick;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView produce,yesnum,nonum;
        LinearLayout parentLinear;

        public MyViewHolder(View view){
            super((view));
            produce =(TextView) view.findViewById(R.id.produce);
            yesnum =(TextView) view.findViewById(R.id.yesnum);
            nonum =(TextView) view.findViewById(R.id.nonum);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public PriceincreaseAdapter(Context mainActivityUser, ArrayList<Priceincrease> priceincrList, MainActivityFGD onItemClick) {
        this.mainActivityUser = mainActivityUser;
        this.priceincrList = priceincrList;
        this.onFGDItemClick = (OnFGDItemClick) onItemClick;
    }

    public void notifyData(ArrayList<Priceincrease> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.priceincrList = myList;
        notifyDataSetChanged();
    }
    public PriceincreaseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.priceincreaserow, parent, false);

        return new PriceincreaseAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PriceincreaseAdapter.MyViewHolder holder, final int position) {
        Priceincrease bean = priceincrList.get(position);
        holder.produce.setText(bean.produce);
        holder.yesnum.setText(bean.yesnum);
        holder.nonum.setText(bean.nonum);


        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFGDItemClick.itemPriceincreaseClick(position);
            }
        });
    }
    public  int getItemCount(){
        return priceincrList.size();
    }

}
