//package com.example.a31284.chatqa;
//
//import android.util.Log;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Connection;
//
//public class DBUtils {
//
//    private static String dbDriver="com.mysql.jdbc.Driver";
//    private static String dbUrl="jdbc:mysql://192.168.1.100:3306/chatbotdb";
//    private static String dbUser = "faces";
//    private static String dbPwd = "123456";
//
//    public static Connection getConn(){
//        Log.i("Toast","进入getConn方法");
//        Connection conn=null;
//        try {
//            Class.forName(dbDriver);
//        } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
//            conn= DriverManager.getConnection(dbUrl, dbUser, dbPwd);
//            Log.i("Connect","连接后"+conn);
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return conn;
//    }
//
//}
