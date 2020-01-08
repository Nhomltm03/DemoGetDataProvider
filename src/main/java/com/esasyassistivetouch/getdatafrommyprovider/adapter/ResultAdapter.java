package com.esasyassistivetouch.getdatafrommyprovider.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esasyassistivetouch.getdatafrommyprovider.R;
import com.esasyassistivetouch.getdatafrommyprovider.model.Student;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ListItemViewHolder> {

    private List<Student> listResult;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ResultAdapter(List<Student> listResult, Context context) {
        this.listResult = listResult;
        this.context = context;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.resul_item, parent, false);
        return new ListItemViewHolder(view, onItemClickListener);
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        Student student = listResult.get(position);
        holder.tvStudentID.setText(student.getId());
        holder.tvStudentName.setText(student.getName());
        holder.tvStudentUni.setText(student.getUniversity());

    }

    @Override
    public int getItemCount() {
        return listResult.size();
    }

    public void setItemOnclickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView  tvStudentID,tvStudentName,tvStudentUni;
        OnItemClickListener onItemClickListener;

        ListItemViewHolder(@NonNull View itemView, OnItemClickListener mOnItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvStudentID = itemView.findViewById(R.id.tv_student_id);
            tvStudentName = itemView.findViewById(R.id.tv_student_name);
            tvStudentUni = itemView.findViewById(R.id.tv_student_university);
            this.onItemClickListener = mOnItemClickListener;
        }

        @Override
        public void onClick(View view) {

            onItemClickListener.onClick(view, getAdapterPosition(), false);

        }
    }
}