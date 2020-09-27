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

public class CultivatecostAdapter extends RecyclerView.Adapter<CultivatecostAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Cultivatecost> cultivatecostList;
    private OnFGDItemClick onFGDItemClick;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView factors,cropone,croptwo,cropthree;
        LinearLayout parentLinear;

        public MyViewHolder(View view){
            super((view));
            factors =(TextView) view.findViewById(R.id.factors);
            cropone =(TextView) view.findViewById(R.id.cropone);
            croptwo =(TextView) view.findViewById(R.id.croptwo);
            cropthree =(TextView) view.findViewById(R.id.cropthree);

            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public CultivatecostAdapter(Context mainActivityUser, ArrayList<Cultivatecost> cultivatecostList, MainActivityFGD onItemClick) {
        this.mainActivityUser = mainActivityUser;
        this.cultivatecostList = cultivatecostList;
        this.onFGDItemClick = (OnFGDItemClick) onItemClick;
    }

    public void notifyData(ArrayList<Cultivatecost> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.cultivatecostList = myList;
        notifyDataSetChanged();
    }
    public CultivatecostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cultivatecostrow, parent, false);

        return new CultivatecostAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CultivatecostAdapter.MyViewHolder holder, final int position) {
        Cultivatecost bean = cultivatecostList.get(position);
        holder.factors.setText(bean.factors);
        holder.cropone.setText(bean.cropone);
        holder.croptwo.setText(bean.croptwo);
        holder.cropthree.setText(bean.cropthree);

                holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFGDItemClick.itemCultivatecostClick(position);
            }
        });
    }
    public  int getItemCount(){
        return cultivatecostList.size();
    }

}
