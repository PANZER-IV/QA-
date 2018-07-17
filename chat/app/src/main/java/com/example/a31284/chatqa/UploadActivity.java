package com.example.a31284.chatqa;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;


import static com.example.a31284.chatqa.R.id.upload;

public class UploadActivity extends AppCompatActivity {

    private static final String TAG = "uploadImage";
    private static String requestURL = "http://192.168.43.185:8080/chatWeb/FileUpLoadServlet";
    private final int SHOWINFOSUCCESS=0;
    private final int SHOWINFONOTFOUND=1;
    private final int SHOWINFOEXCEPT=2;

    TextView apkPathText;
    Button select,up;
    String username;
    String apkPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        apkPathText = (TextView) findViewById(R.id.apkPathText);
        Bundle extras = getIntent().getExtras();
        apkPath = extras.getString("apk_path");
        username = extras.getString("username");
        apkPathText.setText(apkPath);
        //username="user1";

    }

    public void filePicker(View view) {
        Intent intent=new Intent(UploadActivity.this,FileManageActivity.class);
        startActivity(intent);
    }




    public void fileupload(View view){
        File file = new File (apkPath);
        //File file = new File("data2.txt");

        new Thread() {

            private HttpURLConnection connection;
            @Override
            public void run() {

                try {
                    //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                    // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                    String data = "username=" + URLEncoder.encode("user1", "utf-8");
                    String path = requestURL;
                    connection = HttpConnectionUtils.getConnection(data, path);
                    int code = connection.getResponseCode();

                }catch(Exception e){

                }

            }
        }.start();
        if (file != null) {

            UploadUtils.uploadFile(file, requestURL);
            Toast.makeText(getApplicationContext(),"上传成功！",Toast.LENGTH_LONG).show();

        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.PersonalInfo:
               intent=new Intent(UploadActivity.this,ShowUserInfoActivity.class);
               intent.putExtra("username",username);
               startActivity(intent);
               break;
            case R.id.userfiles:
                intent=new Intent(UploadActivity.this,FileManageActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.qamanager:
                intent=new Intent(UploadActivity.this,QAManagerActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;

        }
        return true;
    }

}