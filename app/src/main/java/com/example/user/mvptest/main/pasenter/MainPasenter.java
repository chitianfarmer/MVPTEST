package com.example.user.mvptest.main.pasenter;

import com.alibaba.fastjson.JSONObject;

/**
 * 项目名称：MVPTEST
 * 类描述：MainPasenter 描述:中间实现类接口
 * 创建人：songlijie
 * 创建时间：2017/6/13 15:04
 * 邮箱:814326663@qq.com
 */
public interface MainPasenter {
    void requestData(int page,int pageSize);
    void onItemClick(JSONObject url);
    void onDestory();
}
