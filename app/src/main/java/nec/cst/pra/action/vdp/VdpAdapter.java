package nec.cst.pra.action.vdp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import nec.cst.pra.action.OnItemClick;
import nec.cst.pra.R;

import java.util.ArrayList;

/**
 * Created by SanAji on 30-11-2018.
 */



    public class VdpAdapter extends RecyclerView.Adapter<VdpAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<Vdp> vdpList;
        public OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView vdpPlace,vdpAction;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                vdpPlace =(TextView) view.findViewById(R.id.vdpAddplace);
                vdpAction =(TextView) view.findViewById(R.id.vdpAction);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public VdpAdapter(Context mainActivityUser, ArrayList<Vdp> vdpList, OnItemClick onItemClick) {
            this.vdpList = vdpList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<Vdp> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.vdpList = myList;
            notifyDataSetChanged();
        }
        public VdpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vdp_preview, parent, false);

            return new VdpAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(VdpAdapter.MyViewHolder holder, final int position) {
            Vdp bean = vdpList.get(position);
            holder.vdpPlace.setText(bean.getVdpAddplace());
            holder.vdpAction.setText(bean.getVdpAction());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemVdpClick(position);
                }
            });
        }
        public  int getItemCount(){
            return vdpList.size();
        }


    }
