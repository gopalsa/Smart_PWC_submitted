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

public class PeopleattendAdapter extends RecyclerView.Adapter<PeopleattendAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Peopleattend> peopleattendList;
    private OnFGDItemClick onFGDItemClick;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView men,women,total;
        LinearLayout parentLinear;

        public MyViewHolder(View view){
            super((view));
            men =(TextView) view.findViewById(R.id.men);
            women =(TextView) view.findViewById(R.id.women);
            total =(TextView) view.findViewById(R.id.total);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }

    public PeopleattendAdapter(Context mainActivityUser, ArrayList<Peopleattend> peopleattendList, MainActivityFGD onItemClick) {
        this.mainActivityUser = mainActivityUser;
        this.peopleattendList = peopleattendList;
        this.onFGDItemClick = (OnFGDItemClick) onItemClick;
    }

    public void notifyData(ArrayList<Peopleattend> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.peopleattendList = myList;
        notifyDataSetChanged();
    }
    public PeopleattendAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.peopleattendrow, parent, false);

        return new PeopleattendAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PeopleattendAdapter.MyViewHolder holder, final int position) {
        Peopleattend bean = peopleattendList.get(position);
        holder.men.setText(bean.men);
        holder.women.setText(bean.women);
        holder.total.setText(bean.total);


        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFGDItemClick.itemPeopleattendClick(position);
            }
        });
    }
    public  int getItemCount(){
        return peopleattendList.size();
    }

}
