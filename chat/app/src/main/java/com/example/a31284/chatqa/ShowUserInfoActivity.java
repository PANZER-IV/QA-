package com.example.a31284.chatqa;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class ShowUserInfoActivity extends AppCompatActivity {

    private TextView show_username,show_files;
    private Button show;
    private final int SHOWINFOSUCCESS=0;
    private final int SHOWINFONOTFOUND=1;
    private final int SHOWINFOEXCEPT=2;
    private  String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_info);
        show_username=(TextView)findViewById(R.id.show_username);
        show_files=(TextView)findViewById(R.id.show_files);
        Bundle extras = getIntent().getExtras();
        //username="user1";
        username = extras.getString("username");
        show_username.setText("用户名："+username);
    }
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){//具体消息，具体显示
                case SHOWINFOSUCCESS:
                    show_files.setText("已上传文档：\n"+(String)msg.obj);
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case SHOWINFONOTFOUND:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;

            }
        }
    };

    public void clickToshow(View view) {
            //联网操作要开子线程，在主线程不能更新UI
            new Thread(){

                private HttpURLConnection connection;
                @Override
                public void run() {

                    try {
                        //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                        // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                        String data= "username="+ URLEncoder.encode(username,"utf-8")+"&sign="+URLEncoder.encode("4","utf-8");
                        String path="http://192.168.43.185:8080/chatWeb/ShowInfoServlet";
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
                            message.obj="显示个人信息异常...请稍后再试";
                            handler.sendMessage(message);
                        }
                    } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                        e.printStackTrace();
                        Message message = Message.obtain();
                        message.what=SHOWINFOEXCEPT;
                        message.obj="显示个人信息异常...请稍后再试";
                        handler.sendMessage(message);
                    }
                }
            }.start();//不要忘记开线程

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
//            case R.id.PersonalInfo:
//                intent=new Intent(MainActivity.this,ShowUserInfoActivity.class);
//                intent.putExtra("username",username);
//                startActivity(intent);
//                break;
            case R.id.userfiles:
                intent=new Intent(ShowUserInfoActivity.this,FileManageActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.qamanager:
                intent=new Intent(ShowUserInfoActivity.this,QAManagerActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;

        }
        return true;
    }

}
