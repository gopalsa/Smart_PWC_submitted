package nec.cst.pra.action.uba;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import nec.cst.pra.action.OnItemClick;
import nec.cst.pra.R;

import java.util.ArrayList;

/**
 * Created by SanAji on 02-12-2018.
 */



    public class UbaAdapter extends RecyclerView.Adapter<UbaAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<Uba> ubaList;
        public OnItemClick onItemClick;

    public void notifyData(ArrayList<Uba> ubaArrayList) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView ubaPostal,ubaTelephone,ubaMobile,ubaFax,ubaEmail;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                ubaPostal =(TextView) view.findViewById(R.id.ubaPostal);
                ubaTelephone =(TextView) view.findViewById(R.id.ubaTelephone);
                ubaMobile =(TextView) view.findViewById(R.id.ubaMobile);
                ubaFax =(TextView) view.findViewById(R.id.ubaFax);
                ubaEmail =(TextView) view.findViewById(R.id.ubaEmail);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public UbaAdapter(Context mainActivityUser, ArrayList<Uba> ubaList, OnItemClick onItemClick) {
            this.ubaList = ubaList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
       
        public UbaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.uba_preview, parent, false);

            return new UbaAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(UbaAdapter.MyViewHolder holder, final int position) {
            Uba bean = ubaList.get(position);
            holder.ubaPostal.setText(bean.getubaPostal());
            holder.ubaTelephone.setText(bean.getUbaTelephone());
            holder.ubaFax.setText(bean.getUbaFax());
            holder.ubaEmail.setText(bean.getUbaEmail());
            holder.ubaMobile.setText(bean.getUbaMobile());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemUbaClick(position);
                }
            });
        }
        public  int getItemCount(){
            return ubaList.size();
        }

    }
