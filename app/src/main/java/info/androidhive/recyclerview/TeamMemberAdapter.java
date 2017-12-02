package info.androidhive.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TeamMemberAdapter extends RecyclerView.Adapter<TeamMemberAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Member> moviesList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final CustomFontTextView name;
        private final CustomFontTextView pincode;
        CustomFontTextView designation;
        CustomFontTextView designationinpra;
        CustomFontTextView address;
        CustomFontTextView phone;
        CustomFontTextView experience;
        CustomFontTextView education;
        CustomFontTextView weblink;
        CustomFontTextView geotag;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            name = (CustomFontTextView) view.findViewById(R.id.name);
            designation = (CustomFontTextView) view.findViewById(R.id.designation);
            designationinpra = (CustomFontTextView) view.findViewById(R.id.designationinpra);
            address = (CustomFontTextView) view.findViewById(R.id.address);
            phone = (CustomFontTextView) view.findViewById(R.id.phone);
            education = (CustomFontTextView) view.findViewById(R.id.education);
            experience = (CustomFontTextView) view.findViewById(R.id.experience);
            weblink = (CustomFontTextView) view.findViewById(R.id.socialmedia);
            geotag = (CustomFontTextView) view.findViewById(R.id.geotag);
            pincode = (CustomFontTextView) view.findViewById(R.id.pincode);
        }
    }


    public TeamMemberAdapter(Context mainActivityUser, ArrayList<Member> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;
    }

    public void notifyData(ArrayList<Member> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_member_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Member member = moviesList.get(position);
        holder.name.setText(member.getName());
        holder.designation.setText(member.getDesignation());
        holder.designationinpra.setText(member.getDesignationinpra());
        holder.address.setText(member.getAddress());
        holder.phone.setText(member.getPhone());
        holder.education.setText(member.getEducation());
        holder.experience.setText(member.getExperience());
        holder.weblink.setText(member.getSocialmedia());
        holder.geotag.setText(member.getGeotag());
        holder.pincode.setText(member.getPincode());
        Glide.with(mainActivityUser).load(member.getImage())
                .thumbnail(0.5f)
                .placeholder(R.drawable.profilemember)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
