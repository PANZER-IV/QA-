package com.example.a31284.chatqa;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class QAManagerActivity extends AppCompatActivity {

    Button add,delete;
    EditText question,answer;
    String getQ,getA;

    private final int SHOWINFOSUCCESS=0;
    private final int SHOWINFONOTFOUND=1;
    private final int SHOWINFOEXCEPT=2;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qamanager);

        add=(Button)findViewById(R.id.add);
        delete=(Button)findViewById(R.id.delete);
        question=(EditText)findViewById(R.id.input_question);
        answer=(EditText)findViewById(R.id.input_answer);


        //username="user1";
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
    }

    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){//具体消息，具体显示
                case SHOWINFOSUCCESS:
                    //show.setText((String)msg.obj);
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case SHOWINFONOTFOUND:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;

            }
        }
    };

    public void add(View view) {
        getQ=question.getText().toString();
        getA=answer.getText().toString();

        new Thread(){

            private HttpURLConnection connection;
            String logo="2";

            @Override
            public void run() {

                try {
                    //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                    // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                    String data= "username="+ URLEncoder.encode(username,"utf-8")+"&logo="+URLEncoder.encode(logo,"utf-8")+
                            "&qMessage="+URLEncoder.encode(getQ,"utf-8")+"&aMessage="+URLEncoder.encode(getA,"utf-8");
                    String path="http://192.168.43.185:8080/chatWeb/FindQAServlet";
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
                        message.obj="增加QA对错误";
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what=SHOWINFOEXCEPT;
                    message.obj="增加QA对错误";
                    handler.sendMessage(message);
                }
            }
        }.start();//不要忘记开线程

    }

    public void delete(View view) {
        getQ=question.getText().toString();
        getA=answer.getText().toString();

        new Thread(){

            private HttpURLConnection connection;
            String logo="1";
            @Override
            public void run() {

                try {
                    //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                    // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                    String data= "username="+ URLEncoder.encode(username,"utf-8")+"&logo="+URLEncoder.encode(logo,"utf-8")+
                            "&qMessage="+URLEncoder.encode(getQ,"utf-8")+"&aMessage="+URLEncoder.encode(getA,"utf-8");
                    String path="http://192.168.43.185:8080/chatWeb/FindQAServlet";
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
                        message.obj="删除QA对错误";
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what=SHOWINFOEXCEPT;
                    message.obj="删除QA对错误";
                    handler.sendMessage(message);
                }
            }
        }.start();//不要忘记开线程

    }

    public void check(View view) {
        getQ=question.getText().toString();
        getA=answer.getText().toString();

        new Thread(){

            private HttpURLConnection connection;
            String logo="0";
            @Override
            public void run() {

                try {
                    //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                    // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                    String data= "username="+ URLEncoder.encode(username,"utf-8")+"&logo="+URLEncoder.encode(logo,"utf-8")+
                            "&qMessage="+URLEncoder.encode(getQ,"utf-8")+"&aMessage="+URLEncoder.encode(getA,"utf-8");
                    String path="http://192.168.43.185:8080/chatWeb/FindQAServlet";
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
                        message.obj="删除QA对错误";
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what=SHOWINFOEXCEPT;
                    message.obj="删除QA对错误";
                    handler.sendMessage(message);
                }
            }
        }.start();//不要忘记开线程

    }
}
