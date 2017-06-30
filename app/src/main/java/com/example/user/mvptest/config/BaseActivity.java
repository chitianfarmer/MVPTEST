package com.example.user.mvptest.config;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.user.mvptest.http.okhttputils.okhttp.OkHttpUtils;

/**
 * 项目名称：MVPTEST
 * 类描述：BaseActivity 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/13 16:52
 * 邮箱:814326663@qq.com
 */
public class BaseActivity extends AppCompatActivity {
    public String TAG = BaseActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.add(this);
    }

    @Override
    protected void onDestroy() {
        ActivityUtils.remove(this);
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }
    public void toast(String msg){
        ToastTools.shortToast(this,msg);
    }
    public void toastLong(String msg){
        ToastTools.longToast(this,msg);
    }
}
