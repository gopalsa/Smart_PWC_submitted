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

public class CropwisecultivateAdapter extends RecyclerView.Adapter<CropwisecultivateAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Cropwisecultivate> cropwisecultivateList;
    private OnFGDItemClick onFGDItemClick;




    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView corp,kharifland,rabiland,summerland,kharifprod,rabiprod,summerprod,sellprice,totrevenue;
        LinearLayout parentLinear;

        public MyViewHolder(View view){
            super((view));
            corp =(TextView) view.findViewById(R.id.corp);
            kharifland =(TextView) view.findViewById(R.id.kharifland);
            rabiland =(TextView) view.findViewById(R.id.rabiland);
            summerland =(TextView) view.findViewById(R.id.summerland);
            kharifprod =(TextView) view.findViewById(R.id.kharifprod);
            rabiprod =(TextView) view.findViewById(R.id.rabiprod);
            summerprod =(TextView) view.findViewById(R.id.summerprod);
            sellprice=(TextView)view.findViewById(R.id.sellprice);
            totrevenue =(TextView) view.findViewById(R.id.totrevenue);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public CropwisecultivateAdapter(Context mainActivityUser, ArrayList<Cropwisecultivate> cropwisecultivateList, MainActivityFGD onItemClick) {
        this.mainActivityUser = mainActivityUser;
        this.cropwisecultivateList = cropwisecultivateList;
        this.onFGDItemClick = (OnFGDItemClick) onItemClick;
    }

    public void notifyData(ArrayList<Cropwisecultivate> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.cropwisecultivateList = myList;
        notifyDataSetChanged();
    }
    public CropwisecultivateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cropwisecultivaterow, parent, false);

        return new CropwisecultivateAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CropwisecultivateAdapter.MyViewHolder holder, final int position) {
        Cropwisecultivate bean = cropwisecultivateList.get(position);
        holder.corp.setText(bean.corp);
        holder.kharifland.setText(bean.kharifland);
        holder.rabiland.setText(bean.rabiland);
        holder.summerland.setText(bean.summerland);
        holder.kharifprod.setText(bean.kharifprod);
        holder.rabiprod.setText(bean.rabiprod);
        holder.summerprod.setText(bean.summerprod);
        holder.sellprice.setText(bean.sellprice);
        holder.totrevenue.setText(bean.totrevenue);

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFGDItemClick.itemCropwisecultivateClick(position);
            }
        });
    }
    public  int getItemCount(){
        return cropwisecultivateList.size();
    }

}
