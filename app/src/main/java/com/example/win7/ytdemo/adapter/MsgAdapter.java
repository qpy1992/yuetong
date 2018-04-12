package com.example.win7.ytdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.entity.Msg;
import java.io.File;
import java.util.List;

/**
 * Created by WIN7 on 2018/3/15.
 */

public class MsgAdapter extends BaseAdapter{
    Context mContext;
    List<Msg> list;
    LayoutInflater inflater;

    public MsgAdapter(Context context, List<Msg> list){
        this.mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view==null){
            view = inflater.inflate(R.layout.item_msg,null);
            holder = new Holder();
            holder.tv_nickname = (TextView)view
                    .findViewById(R.id.tv_nickname);
            holder.tv_lastmsg = (TextView)view
                    .findViewById(R.id.tv_lastmsg);
            holder.tv_time = (TextView)view
                    .findViewById(R.id.tv_time);
            holder.iv_icon = (ImageView)view
                    .findViewById(R.id.iv_icon);
            view.setTag(holder);
        }else{
            holder = (Holder)view.getTag();
        }
        String path = list.get(i).getImagepath();
        if(path==null){
            holder.iv_icon.setImageResource(R.drawable.me_light);
        }else {
            File file = new File(path);
            if (file.exists()) {
                Bitmap bm = BitmapFactory.decodeFile(path);
                holder.iv_icon.setImageBitmap(bm);
            } else {
                holder.iv_icon.setImageResource(R.drawable.me_light);
            }
        }
        holder.tv_nickname.setText(list.get(i).getNickname());
        if(list.get(i).getLastmsg()==null){
            holder.tv_lastmsg.setText("");
        }else {
            holder.tv_lastmsg.setText(list.get(i).getLastmsg());
        }
        if(list.get(i).getTime()==null){
            holder.tv_time.setText("");
        }else {
            holder.tv_time.setText(list.get(i).getTime());
        }
        return view;
    }

    class Holder{
        TextView tv_nickname,tv_lastmsg,tv_time;
        ImageView iv_icon;
    }
}
