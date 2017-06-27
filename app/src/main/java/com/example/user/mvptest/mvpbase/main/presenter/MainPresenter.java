package com.example.user.mvptest.mvpbase.main.presenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.http.HttpUtils;
import com.example.user.mvptest.http.okhttputils.okhttp.callback.StringCallback;
import com.example.user.mvptest.mvpbase.main.contract.MainContract;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 项目名称：MVPTEST
 * 类描述：MainPresenter 描述:
 * 创建人：songlijie
 * 创建时间：2017/6/27 15:32
 * 邮箱:814326663@qq.com
 */
public class MainPresenter implements MainContract.Pasenter {

    private MainContract.View mainView;

    public MainPresenter(MainContract.View mainView) {
        this.mainView = mainView;
        this.mainView.setPresenter(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void requestData(int page, int pageSize) {
        mainView.showLoadingDialog();
        HttpUtils.requestData(page, pageSize, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mainView.hideLoadingDialog();
                mainView.onRequestError(e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                List<JSONObject> objects = new ArrayList<JSONObject>();
                JSONObject jsonObject = JSONObject.parseObject(response);
                String errno = jsonObject.getString("errno");
                String errmsg = jsonObject.getString("errmsg");
                if (errno.equals("0")){
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray items = data.getJSONArray("items");
                    if (items!=null && items.size()!=0){
                        for (int i = 0; i <items.size() ; i++) {
                            JSONObject object = items.getJSONObject(i);
                            if (!objects.contains(object)){
                                objects.add(object);
                            }
                        }
                        mainView.onRequestSuccess(objects);
                    }
                }else{
                    mainView.onRequestError(errmsg);
                }
                mainView.hideLoadingDialog();
            }
        });
    }

    @Override
    public void onItemClick(JSONObject url) {
        String roomId = url.getString("id");
        HttpUtils.requestPadnaLiveDetail(roomId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mainView.onRequestError(e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                int code = jsonObject.getIntValue("errno");
                String errmsg = jsonObject.getString("errmsg");
                switch (code){
                    case 0:
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONObject videoinfo = data.getJSONObject("videoinfo");
                        JSONObject roominfo = data.getJSONObject("roominfo");
                        String videoTitle = roominfo.getString("name");
                        String videoImg = roominfo.getJSONObject("pictures").getString("img");
                        JSONObject object1 = new JSONObject();
                        object1.put("title",videoTitle);
                        object1.put("img",videoImg);
                        if (videoinfo !=null){
                            String plflag = videoinfo.getString("plflag");
                            String room_key = videoinfo.getString("room_key");
                            String videoUrl = "http://pl-hls" + plflag.substring(plflag.indexOf("_") + 1, plflag.length()) + ".live.panda.tv/live_panda/" + room_key + ".m3u8";
                            object1.put("url",videoUrl);
                        }
                        mainView.onItemOnClickResult(object1.toJSONString());
                        break;
                    default:
                        mainView.onRequestError(errmsg);
                        break;
                }
            }
        });
    }

    @Override
    public void onDestory() {
        mainView = null;
    }
}
