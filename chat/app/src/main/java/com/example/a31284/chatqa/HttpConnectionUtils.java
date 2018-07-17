package com.example.a31284.chatqa;

import android.os.Message;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 获取联网连接
 */

public class HttpConnectionUtils {
    public static HttpURLConnection getConnection(String data,String path) throws Exception {

        //通过URL对象获取联网对象
        //URL url= new URL("http://192.168.43.185:8080/chatWeb/LoginServlet");
        // URL url= new URL("http://192.168.43.185:8080/chatWeb/Dservlet");

        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型

        URL url= new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");//设置post请求
        connection.setReadTimeout(999999999);//设置5s的响应时间
        connection.setDoOutput(true);//允许输出
        connection.setDoInput(true);//允许输入
        //设置请求头，以键值对的方式传输（以下这两点在GET请求中不用设置）
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded ");
        //connection.setRequestProperty("Content-Type","multipart/form-data");
//        connection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
//                + BOUNDARY);
        connection.setRequestProperty("Content-Length",data.length()+"");//设置请求体的长度
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());//进行传输操作
        //判断服务端返回的响应码，这里是http协议的内容
        return connection;
    }

}
