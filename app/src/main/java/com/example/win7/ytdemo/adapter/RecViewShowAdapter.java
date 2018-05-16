package com.example.win7.ytdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.win7.ytdemo.R;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/16 9:27
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class RecViewShowAdapter extends RecyclerView.Adapter<RecViewShowAdapter.ViewHolder> {
    private Context      mContext;
    private List<Bitmap> mData;

    public RecViewShowAdapter(Context context, List<Bitmap> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_recy_item, parent, false);
        // 实例化viewholder
        RecViewShowAdapter.ViewHolder viewHolder = new RecViewShowAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bitmap bitmap = mData.get(position);
        holder.img_add_photo.setImageBitmap(bitmap);
        holder.img_delet.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        //展示条目数
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_add_photo, img_delet;

        public ViewHolder(View itemView) {
            super(itemView);
            img_add_photo = (ImageView) itemView.findViewById(R.id.img_add_photo);
            img_delet = (ImageView) itemView.findViewById(R.id.img_delet);
        }
    }
}
