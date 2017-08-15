package com.cnbs.utilslibrarydemo.calendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnbs.utilslibrary.viewUtils.toast.CenterHintToast;
import com.cnbs.utilslibrarydemo.R;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */

public class AAAdapter extends RecyclerView.Adapter<AAAdapter.MyViewHolder> {

    private Context context;
    private List<String> remarkList;

    public AAAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<String> remarkList){
        this.remarkList = remarkList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_, parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TextView textView = holder.textView;
        textView.setText(remarkList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new CenterHintToast(context,"跳转到"+remarkList.get(position)+"记录的详情");
            }
        });

    }

    @Override
    public int getItemCount() {
        return remarkList==null?0:remarkList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_);
        }
    }
}




