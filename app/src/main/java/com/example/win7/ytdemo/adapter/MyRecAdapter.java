package com.example.win7.ytdemo.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.win7.ytdemo.R;

import java.util.ArrayList;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/15 16:49
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyRecAdapter extends RecyclerView.Adapter<MyRecAdapter.ViewHolder> {
    private Context           mContext;
    private ArrayList<Bitmap> mData;
    private static final int IMAGE = 1;//调用系统相册-选择图片

    public MyRecAdapter(Context context, ArrayList<Bitmap> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public MyRecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_recy_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    private int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;

    @Override
    public void onBindViewHolder(MyRecAdapter.ViewHolder holder, final int position) {
        if (position == 0) {
            //第一个条目不显示删除按键
            holder.img_delet.setVisibility(View.GONE);
            //            holder.tv_pro_uping.setVisibility(View.GONE);
            holder.img_add_photo.setImageBitmap(mData.get(position));
            // 设置点击事件添加图片
            holder.img_add_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //第二个参数是需要申请的权限
                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //权限还没有授予，需要在这里写申请权限的代码
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                    } else {
                        //权限已经被授予，在这里直接写要执行的相应方法即可
                        //调用相册
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        Activity activity = (Activity) mContext;
                        activity.startActivityForResult(intent, IMAGE);
                    }
                }
            });
        } else {
            holder.img_add_photo.setImageBitmap(mData.get(position));
            //            holder.tv_pro_uping.setVisibility(View.VISIBLE);
            //            String upSorF = mState.get(position - 1);
            //            if (upSorF.equals("")) {
            //                holder.tv_pro_uping.setText("正在上传");
            //            } else if (upSorF.equals("成功")) {
            //                holder.tv_pro_uping.setText("上传成功");
            //            } else {
            //                holder.tv_pro_uping.setText("上传失败");
            //            }
        }

        holder.img_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                //                mState.remove(position - 1);
                //                mPicUrlList.remove(position - 1);
                //                mImgPath = "";
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        //展示条目数
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_add_photo;
        ImageView img_delet;
        //        TextView  tv_pro_uping;

        public ViewHolder(View itemView) {
            super(itemView);
            img_add_photo = (ImageView) itemView.findViewById(R.id.img_add_photo);
            img_delet = (ImageView) itemView.findViewById(R.id.img_delet);
            //            tv_pro_uping = (TextView) itemView.findViewById(R.id.tv_pro_uping);
        }
    }
}
