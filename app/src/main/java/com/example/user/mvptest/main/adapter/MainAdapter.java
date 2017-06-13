package com.example.user.mvptest.main.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.R;
import com.example.user.utils.media.GildeTools.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：MVPTEST
 * 类描述：MainAdapter 描述: 数据适配器
 * 创建人：songlijie
 * 创建时间：2017/6/13 15:31
 * 邮箱:814326663@qq.com
 */
public class MainAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;
    private OnItemClickListener listener;
    private List<JSONObject> jsonObjects = new ArrayList<>();

    public MainAdapter(Context mContext, List<JSONObject> jsonObjects) {
        this.mContext = mContext;
        this.jsonObjects = jsonObjects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recyclerview, null);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder holder1 = (MyViewHolder) holder;
        final JSONObject item = jsonObjects.get(position);
        holder1.tv_name.setText("观看人数:" + item.getString("person_num"));
        holder1.title.setText(item.getString("name"));
        holder1.tv_people.setText( "主播:"+item.getJSONObject("userinfo").getString("nickName"));
        GlideUtils.downLoadRoundTransform(mContext,item.getJSONObject("pictures").getString("img"),holder1.iv_cover,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        holder1.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.OnItemClick(v,(JSONObject)v.getTag());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title,tv_name,tv_people;
        private ImageView iv_cover;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_people = (TextView) itemView.findViewById(R.id.tv_people);
            iv_cover = (ImageView) itemView.findViewById(R.id.iv_cover);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface  OnItemClickListener{
        void OnItemClick(View view,JSONObject jsonObject);
    }
}
