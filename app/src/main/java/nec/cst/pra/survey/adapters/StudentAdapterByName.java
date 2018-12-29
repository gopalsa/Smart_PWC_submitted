package nec.cst.pra.survey.adapters;

import android.content.Context;

import java.util.List;
import java.util.Map;

import nec.cst.pra.R;

/**
 * @author Vladislav Zhukov (https://github.com/zhukic)
 */

public class StudentAdapterByName extends BaseStudentAdapter {


    public StudentAdapterByName(List<Response> itemList, Context context, Map<String, Integer> departmentCount) {
        super(itemList, context, departmentCount);
    }

    @Override
    public boolean onPlaceSubheaderBetweenItems(int position) {

        final Response task = taskList.get(position);
        final Response nextStudent = taskList.get(position + 1);
        return !task.getIsValid().equals(nextStudent.getIsValid());
    }

    @Override
    public void onBindItemViewHolder(final MovieViewHolder holder, final int position) {
        final Response task = taskList.get(position);

        holder.name.setText(task.student);
        if (task.gps != null && task.gps.length() > 0) {
            holder.gps.setText(task.gps);
        }
        holder.response.setText(task.response);
        holder.isValid.setText(task.isValid);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClicked(task));
        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(context.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onBindSubheaderViewHolder(SubheaderHolder subheaderHolder, int nextItemPosition) {
        super.onBindSubheaderViewHolder(subheaderHolder, nextItemPosition);
        final Response nextStudent = taskList.get(nextItemPosition);
        subheaderHolder.mSubheaderText.setText(nextStudent.getIsValid());
        subheaderHolder.countBadge.setText(String.valueOf(departmentCountMap.get(nextStudent.getIsValid())));
    }
}
