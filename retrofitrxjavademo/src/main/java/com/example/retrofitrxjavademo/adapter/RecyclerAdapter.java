package com.example.retrofitrxjavademo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.retrofitrxjavademo.R;
import com.example.retrofitrxjavademo.data.NewsData;

import java.util.List;

/**
 * Created by yfx on 16/10/28.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<NewsData.NewslistBean> dataList;
    private LayoutInflater inflater;
    public RecyclerAdapter(Context context, List<NewsData.NewslistBean> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsData.NewslistBean newslistBean = dataList.get(position);
        holder.time.setText(newslistBean.getCtime());
        holder.name.setText(newslistBean.getDescription());
        Glide.with(context).load(newslistBean.getPicUrl()).error(R.mipmap.ic_launcher).centerCrop().into(holder.icon);
    }
    public void addData(List<NewsData.NewslistBean> dataList){
        this.dataList.addAll(0,dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView time;
        private ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.person_name);
            time= (TextView) itemView.findViewById(R.id.person_sign);
            icon= (ImageView) itemView.findViewById(R.id.person_face);
        }
    }

}
