package nec.cst.pra.village;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import nec.cst.pra.R;
import nec.cst.pra.app.GlideApp;

public class ImportantMemberAdapter extends RecyclerView.Adapter<ImportantMemberAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<ImportantPerson> moviesList;
    private MemberClick memberClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView image, cancelImg;
        private final TextView title, subTitle;


        public MyViewHolder(View view) {
            super(view);
            image = (CircleImageView) view.findViewById(R.id.image);
            cancelImg = (CircleImageView) view.findViewById(R.id.cancelImg);
            title = (TextView) view.findViewById(R.id.title);
            subTitle = (TextView) view.findViewById(R.id.subTitle);
        }
    }


    public ImportantMemberAdapter(Context mainActivityUser, ArrayList<ImportantPerson> moviesList,
                                  MemberClick memberClick) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;
        this.memberClick = memberClick;
    }

    public void notifyData(ArrayList<ImportantPerson> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.important_re_person, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ImportantPerson member = moviesList.get(position);
        holder.title.setText(member.getName());
        holder.subTitle.setText(member.getDesignation());
        GlideApp.with(mainActivityUser).load(member.getImage())
                .thumbnail(0.5f)
                .placeholder(R.drawable.profilemember)
                .into(holder.image);

        if (position == 0) {
            holder.cancelImg.setVisibility(View.GONE);
        } else {
            holder.cancelImg.setVisibility(View.VISIBLE);
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberClick.onMemberClick(position);
            }
        });

        holder.cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberClick.onDeleteMemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
