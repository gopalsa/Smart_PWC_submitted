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

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Storage> storageList;
    private OnFGDItemClick onFGDItemClick;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView facility,yes,no,farmdist;
        LinearLayout parentLinear;

        public MyViewHolder(View view){
            super((view));
            facility =(TextView) view.findViewById(R.id.facility);
            yes =(TextView) view.findViewById(R.id.yes);
            no =(TextView) view.findViewById(R.id.no);
            farmdist =(TextView) view.findViewById(R.id.farmdist);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public StorageAdapter(Context mainActivityUser, ArrayList<Storage> storageList, MainActivityFGD onItemClick) {
        this.mainActivityUser = mainActivityUser;
        this.storageList = storageList;
        this.onFGDItemClick = (OnFGDItemClick) onItemClick;
    }

    public void notifyData(ArrayList<Storage> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.storageList = myList;
        notifyDataSetChanged();
    }
    public StorageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.storagerow, parent, false);

        return new StorageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StorageAdapter.MyViewHolder holder, final int position) {
        Storage bean = storageList.get(position);
        holder.facility.setText(bean.facility);
        holder.yes.setText(bean.yes);
        holder.no.setText(bean.no);
        holder.farmdist.setText(bean.farmdist);


        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFGDItemClick.itemStorageClick(position);
            }
        });
    }
    public  int getItemCount(){
        return storageList.size();
    }

}
