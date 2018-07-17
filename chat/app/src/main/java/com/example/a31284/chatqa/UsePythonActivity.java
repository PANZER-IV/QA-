package com.example.a31284.chatqa;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class UsePythonActivity extends AppCompatActivity {

    private TextView show;
    private Button train;
    private String question;
    private final int SHOWINFOSUCCESS=0;
    private final int SHOWINFONOTFOUND=1;
    private final int SHOWINFOEXCEPT=2;
    private  String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_python);
        show=(TextView)findViewById(R.id.show);
        train=(Button) findViewById(R.id.train);
        //Bundle extras = getIntent().getExtras();
        username="user1";
        //username = extras.getString("username");
        question="云容器引擎";
    }
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){//具体消息，具体显示
                case SHOWINFOSUCCESS:
                    show.setText((String)msg.obj);
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case SHOWINFONOTFOUND:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;

            }
        }
    };

    public void train_data(View view) {
        //联网操作要开子线程，在主线程不能更新UI
        new Thread(){

            private HttpURLConnection connection;
            @Override
            public void run() {

                try {
                    //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                    // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                    String data= "username="+ URLEncoder.encode(username,"utf-8")+"&question="+URLEncoder.encode(question,"utf-8")+"&sign="+URLEncoder.encode("6","utf-8");
                    String path="http://192.168.43.185:8080/chatWeb/UsePythonServlet";
                    connection= HttpConnectionUtils.getConnection(data,path);
                    int code = connection.getResponseCode();
                    Intent intent;
                    if(code==200){
                        InputStream inputStream = connection.getInputStream();
                        String str =StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                        Message message = Message.obtain();//更新UI就要向消息机制发送消息
                        message.what=SHOWINFOSUCCESS;//用来标志是哪个消息
                        message.obj=str;//消息主体
                        handler.sendMessage(message);
                    }
                    else {
                        Message message = Message.obtain();
                        message.what=SHOWINFONOTFOUND;
                        message.obj="获取回答异常";
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what=SHOWINFOEXCEPT;
                    message.obj="获取回答异常";
                    handler.sendMessage(message);
                }
            }
        }.start();//不要忘记开线程

    }
}
