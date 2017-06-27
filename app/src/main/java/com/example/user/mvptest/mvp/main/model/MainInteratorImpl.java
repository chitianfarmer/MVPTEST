package com.example.user.mvptest.mvp.main.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.user.mvptest.http.HttpUtils;
import com.example.user.mvptest.http.okhttputils.okhttp.callback.StringCallback;
import com.example.user.mvptest.mvp.main.pasenter.MainInterator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 项目名称：MVPTEST
 * 类描述：MainInteratorImpl 描述:网络请求接口回调实现类
 * 创建人：songlijie
 * 创建时间：2017/6/13 14:42
 * 邮箱:814326663@qq.com
 */
public class MainInteratorImpl implements MainInterator {
    /**
     * 主页数据请求
     * @param page
     * @param pageSize
     * @param listener
     */
    @Override
    public void requestData(int page, int pageSize,final onMainLoadDataFinishedListener listener) {
        listener.showLoadingDialog();
        HttpUtils.requestData(page, pageSize, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onRequestError(e.getMessage());
                listener.hideLoadingDialog();
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
                        listener.onRequestSuccess(objects);
                    }
                }else{
                    listener.onRequestError(errmsg);
                }
                listener.hideLoadingDialog();
            }
        });
    }

    /**
     * recyclerview的item点击
     * @param object
     * @param listener
     */
    @Override
    public void onItemClick(JSONObject object,final onMainLoadDataFinishedListener listener) {
        String roomId = object.getString("id");
        HttpUtils.requestPadnaLiveDetail(roomId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onRequestError(e.getMessage());
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
                        listener.onItemClickResult(object1.toJSONString());
                        break;
                    default:
                        listener.onRequestError(errmsg);
                        break;
                }
            }
        });
    }

}
