package smart.cst.pwc.needs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import smart.cst.pwc.R;


public class MasterNeedsAdapter extends RecyclerView.Adapter<MasterNeedsAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Need> moviesList;
    private NeedClick needClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title, countTxt;
        Switch available;
        EditText distance;
        ImageView addClick, minusClick;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            countTxt = (TextView) view.findViewById(R.id.countTxt);
            available = (Switch) view.findViewById(R.id.available);
            distance = (EditText) view.findViewById(R.id.distance);
            addClick = (ImageView) view.findViewById(R.id.addClick);
            minusClick = (ImageView) view.findViewById(R.id.minusClick);

        }
    }


    public MasterNeedsAdapter(Context mainActivityUser, ArrayList<Need> moviesList, NeedClick needClick) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;
        this.needClick = needClick;

    }

    public void notifyData(ArrayList<Need> myList) {
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    public void notifyDataItem(ArrayList<Need> myList, int position) {
        this.moviesList = myList;
        notifyItemChanged(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.need_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Need bean = moviesList.get(position);
        holder.title.setText(bean.getName());
        holder.countTxt.setText(bean.getCount());
        holder.available.setChecked(Boolean.parseBoolean(bean.isAvailable));
        holder.distance.setText(bean.distance);

        holder.addClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                needClick.onAddClick(position, Integer.parseInt(bean.count) + 1);
            }
        });

        holder.minusClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                needClick.onMinusClick(position, Integer.parseInt(bean.count) - 1);
            }
        });

        holder.available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                needClick.onAvailable(isChecked, position);
            }
        });
//        holder.distance.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                needClick.onDistance(position, s.toString());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
