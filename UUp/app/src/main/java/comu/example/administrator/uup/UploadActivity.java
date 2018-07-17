package comu.example.administrator.uup;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class UploadActivity extends AppCompatActivity {

        private static final String TAG = "uploadImage";
        private static String requestURL = "http://192.168.43.201:8080/Android/FileUpLoadServlet";

        TextView apkPathText;
        Button select,up;

        String apkPath;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_upload);
                apkPathText = (TextView) findViewById(R.id.apkPathText);
                Bundle extras = getIntent().getExtras();
                apkPath = extras.getString("apk_path");
                apkPathText.setText(apkPath);

        }

        public void filePicker(View view) {
                Intent intent=new Intent(UploadActivity.this,FileExplorerActivity.class);
                startActivity(intent);
        }


        public void fileupload(View view){
                File file = new File (apkPath);
                //File file = new File("data2.txt");
                if (file != null) {

                        UploadUtils.uploadFile(file, requestURL);

                }
        }
}