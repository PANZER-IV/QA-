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

public class FileManageActivity extends AppCompatActivity {

    Button preprocess;
    String username;
    TextView showinfo;
    private final int SHOWINFOSUCCESS=0;
    private final int SHOWINFONOTFOUND=1;
    private final int SHOWINFOEXCEPT=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manage);
        preprocess=(Button)findViewById(R.id.preprocess);
        Bundle extras = getIntent().getExtras();
        //username="user1";
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

    public void getPreprocess(View view) {

        new Thread(){

            private HttpURLConnection connection;
            @Override
            public void run() {

                try {
                    //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                    // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                    String data= "username="+ URLEncoder.encode(username,"utf-8")+"&sign="+URLEncoder.encode("5","utf-8");
                    String path="http://192.168.43.185:8080/chatWeb/PythonServlet";
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
                        message.obj="预处理异常";
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what=SHOWINFOEXCEPT;
                    message.obj="预处理异常";
                    handler.sendMessage(message);
                }
            }
        }.start();//不要忘记开线程
        Toast.makeText(getApplicationContext(),"预处理需要十五分钟左右~",Toast.LENGTH_LONG).show();
    }


    public void gotoupload(View view) {
        Intent intent = new Intent(FileManageActivity.this,FileExplorerActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.PersonalInfo:
                intent=new Intent(FileManageActivity.this,ShowUserInfoActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.userfiles:
                intent=new Intent(FileManageActivity.this,FileManageActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.qamanager:
                intent=new Intent(FileManageActivity.this,QAManagerActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;

        }
        return true;
    }


}
