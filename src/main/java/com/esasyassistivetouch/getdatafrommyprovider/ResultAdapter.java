package com.esasyassistivetouch.getdatafrommyprovider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ListItemViewHolder> {

    private List<String> listResult;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ResultAdapter(List<String> listResult, Context context) {
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
        String result = listResult.get(position);
        holder.tvResult.setText(result);

    }

    @Override
    public int getItemCount() {
        return listResult.size();
    }

    public void setItemOnclickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView  tvResult;
        OnItemClickListener onItemClickListener;

        ListItemViewHolder(@NonNull View itemView, OnItemClickListener mOnItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvResult = itemView.findViewById(R.id.tv_result);
            this.onItemClickListener = mOnItemClickListener;
        }

        @Override
        public void onClick(View view) {

            onItemClickListener.onClick(view, getAdapterPosition(), false);

        }
    }
}