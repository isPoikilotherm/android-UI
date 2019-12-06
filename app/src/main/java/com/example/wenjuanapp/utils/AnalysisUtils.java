package com.example.wenjuanapp.utils;
import android.content.Context;
import android.content.SharedPreferences;

public class AnalysisUtils {
    public static String readLoginUserName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginInfo",
                Context.MODE_PRIVATE);
        String userName = sp.getString("loginUserName", "");
        return userName;
    }
    public static String readProjectName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("project",
                Context.MODE_PRIVATE);
        String project_Name = sp.getString("project_name", "");
        return project_Name;
    }
//    public static Integer readProjectName(Context context){
//        SharedPreferences sp = context.getSharedPreferences("projectinfo",
//                Context.MODE_PRIVATE);
//        int project_id = sp.getInt("project_id",1);
//        return project_id;
//    }

}

