package com.example.wenjuan.feature.activity.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wenjuan.feature.activity.utils.AnalysisUtils;

;

public class MyInfoView {
    public ImageView iv_head_icon;
    private LinearLayout ll_head;
    private RelativeLayout rl_course_history, rl_setting;
    private TextView tv_user_name;
    private Activity mContext;
    private LayoutInflater mInflater;
    private View mCurrentView;

    public MyInfoView(Activity context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }



    /**
     * 登录成功后设置我的界面
     */
    public void setLoginParams(boolean isLogin) {
        if (isLogin) {
            tv_user_name.setText(com.example.wenjuan.feature.activity.utils.AnalysisUtils.readLoginUserName
                    (mContext));
        } else {
            tv_user_name.setText("点击登陆");
        }
    }

    /**
     * 获取当前在导航栏上方显示对应的View
     */


    /**
     * 显示当前导航栏上方所对应的view界面
     */


    /**
     * 从sharedPreferences中读取登录状态
     */
    private boolean readLoginStatus() {
        SharedPreferences sp = mContext.getSharedPreferences("loginInfo",
                Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin", false);
        return isLogin;
    }
}




