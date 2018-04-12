package com.example.win7.ytdemo.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.adapter.ChatAdapter;
import com.example.win7.ytdemo.entity.Chat;
import com.example.win7.ytdemo.entity.Item;
import com.example.win7.ytdemo.util.Utils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity {
    EditText et_msg;
    TextView tv_send;
    String nickname;
    RecyclerView rcv_msg;
    ChatAdapter adapter;
    ArrayList<Item> list;
    String content;
    Toolbar toolbar;
    LinearLayout ll_chat,ll_bottom;
    public static final int SHOW_RESPONSE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        setTool();
        setViews();
        setListeners();
    }

    protected void setTool(){
        nickname = getIntent().getStringExtra("nickname");
        toolbar = (Toolbar)findViewById(R.id.id_toolbar);
        toolbar.setTitle(nickname);

        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);


        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setViews(){
        ll_chat = (LinearLayout)findViewById(R.id.ll_chat);
        ll_bottom = (LinearLayout)findViewById(R.id.ll_bottom);
        et_msg = (EditText)findViewById(R.id.et_msg);
        tv_send = (TextView)findViewById(R.id.tv_send);
        rcv_msg = (RecyclerView)findViewById(R.id.rcv_msg);
        list = new ArrayList<>();
        adapter = new ChatAdapter();
        rcv_msg.setHasFixedSize(true);
        rcv_msg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_msg.setAdapter(adapter);
//        adapter.replaceAll(list);
        Utils.autoScrollView(ll_chat, ll_bottom);//弹出软键盘时滚动视图
    }

    protected void setListeners(){
//        et_msg.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                content = s.toString().trim();
//            }
//        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                content = et_msg.getText().toString();
                if(content.equals("")){
                    Toast.makeText(ChatActivity.this,"不能发送空消息",Toast.LENGTH_SHORT).show();
                }else {
                    ArrayList<Item> data = new ArrayList<>();
                    //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                    EMMessage message = EMMessage.createTxtSendMessage(content, nickname);
                    //发送消息
                    EMClient.getInstance().chatManager().sendMessage(message);
                    //将消息放到右边
                    Chat chat = new Chat();
                    chat.setIcon("http://wx.qlogo.cn/mmopen/V1RSN8IUDAyVjF42G3G1lTBAEiaiam5hmhmnm0MmFRmf61oicTJhVTRAOS9tqjhMc5hmRcGNFQR2VACicSic6xq0m34lpPAKIcOdJ/0");
                    chat.setContent(content);
                    data.add(new Item(Item.CHAT_B,chat));
                    adapter.addAll(data);
                    et_msg.setText("");
                    hideKeyBorad(et_msg);
                }
            }
        });
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(final List<EMMessage> messages) {
            //收到消息
            //将消息放到左边
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Item> data1 = new ArrayList<>();
                    Chat chat = new Chat();
                    chat.setIcon("http://wx.qlogo.cn/mmopen/V1RSN8IUDAyVjF42G3G1lTBAEiaiam5hmhmnm0MmFRmf61oicTJhVTRAOS9tqjhMc5hmRcGNFQR2VACicSic6xq0m34lpPAKIcOdJ/0");
                    chat.setContent(messages.get(0).getBody().toString().replace("txt:\"","").replace("\"",""));
                    data1.add(new Item(Item.CHAT_A,chat));
                    adapter.addAll(data1);
                }
            });
            hideKeyBorad(et_msg);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone rt = RingtoneManager.getRingtone(getApplicationContext(), uri);
            rt.play();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息

        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执

        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执

        }
        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回

        }
        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动

        }
    };


    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        //记得在不需要的时候移除listener，如在activity的onDestroy()时
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        super.onDestroy();
    }
}
