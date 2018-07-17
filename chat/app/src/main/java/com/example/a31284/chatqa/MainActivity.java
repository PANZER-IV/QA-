package com.example.a31284.chatqa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.example.a31284.chatqa.HttpConnectionUtils;

public class MainActivity extends AppCompatActivity {
    private List<ChatMessage> list;
    private ListView chat_listview;
    private EditText chat_input;
    private Button chat_send;
    private ChatMessageAdapter chatAdapter;
    private ChatMessage chatMessage = null;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
        initData();
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        //username="user1";
    }

    // 1.初始试图
    private void initView() {
        // 1.初始化
        chat_listview = (ListView) findViewById(R.id.chat_listview);
        chat_input = (EditText) findViewById(R.id.chat_input_message);
        chat_send = (Button) findViewById(R.id.chat_send);
    }

    // 2.设置监听事件
    private void initListener() {
        chat_send.setOnClickListener(onClickListener);
    }

    // 3.初始化数据
    private void initData() {
        list = new ArrayList<ChatMessage>();
        list.add(new ChatMessage("您好!", ChatMessage.Type.INCOUNT, new Date()));
        chatAdapter = new ChatMessageAdapter(list);
        chat_listview.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x1) {
                if (msg.obj != null) {
                    chatMessage = (ChatMessage) msg.obj;
                }
                // 添加数据到list中，更新数据
                list.add(chatMessage);
                chatAdapter.notifyDataSetChanged();
            }
        };
    };

    // 4.发送消息聊天
    private void chat() {
        // 1.判断是否输入内容
        final String send_message = chat_input.getText().toString().trim();
        if (TextUtils.isEmpty(send_message)) {
            Toast.makeText(MainActivity.this, "对不起，您还未发送任何消息",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 2.自己输入的内容也是一条记录，记录刷新
        ChatMessage sendChatMessage = new ChatMessage();
        sendChatMessage.setMessage(send_message);
        sendChatMessage.setData(new Date());
        sendChatMessage.setType(ChatMessage.Type.OUTCOUNT);
        list.add(sendChatMessage);
        chatAdapter.notifyDataSetChanged();
        chat_input.setText("");

        // 3.发送你的消息，去服务器端，返回数据
        new Thread() {
            private HttpURLConnection connection;
            public void run() {
                try{
                    //ChatMessage chat = HttpUtils.sendMessage(send_message);
                    String data= "username="+ URLEncoder.encode(username,"utf-8")+"&question="+URLEncoder.encode(send_message,"utf-8")+"&sign="+URLEncoder.encode("6","utf-8");
                    String path="http://192.168.43.185:8080/chatWeb/UsePythonServlet";
                    connection= HttpConnectionUtils.getConnection(data,path);
                    int code = connection.getResponseCode();
                    if(code==200) {
                        ChatMessage chat = new ChatMessage();
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                        chat.setMessage(str);
                        chat.setData(new Date());
                        chat.setType(ChatMessage.Type.INCOUNT);
                        Message message = Message.obtain();//更新UI就要向消息机制发送消息
                        message.what = 0x1;//用来标志是哪个消息
                        message.obj = chat;//消息主体
                        handler.sendMessage(message);
                    }
                    }catch (Exception e){

                }

//                Message message = new Message();
//                message.what = 0x1;
//                message.obj = chat;
//                handler.sendMessage(message);
            }
        }.start();
    }



    // 点击事件监听
    OnClickListener onClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.chat_send:
                    chat();
                    break;
            }
        }
    };

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.PersonalInfo:
                intent=new Intent(MainActivity.this,ShowUserInfoActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.userfiles:
                intent=new Intent(MainActivity.this,FileManageActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.qamanager:
                intent=new Intent(MainActivity.this,QAManagerActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;

        }
        return true;
    }
}