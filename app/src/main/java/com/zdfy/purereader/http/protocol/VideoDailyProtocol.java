package com.zdfy.purereader.http.protocol;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.zdfy.purereader.domain.VideoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yaozong on 2016/9/25.
 */

public class VideoDailyProtocol extends BaseProtocol<List<VideoInfo.IssueListBean.ItemListBean>> {

    private long lastTime;
    private String lastPage;

    @Override
    protected List<VideoInfo.IssueListBean.ItemListBean> parseData(String result) {
        VideoInfo info = JSON.parseObject(result, VideoInfo.class);
        if (info != null)
            lastPage = info.getNextPageUrl();

        ArrayList<VideoInfo.IssueListBean.ItemListBean> list = new ArrayList<>();

        addToList(list, info.getIssueList().get(0).getItemList());
        addToList(list, info.getIssueList().get(1).getItemList());

        return list;
    }

    private void addToList(ArrayList<VideoInfo.IssueListBean.ItemListBean> list, List<VideoInfo.IssueListBean.ItemListBean> itemList) {
        for (int i = 0; i < itemList.size(); i++) {
            String dataType = itemList.get(i).getType();
            if ("video".equals(dataType) || "textHeader".equals(dataType)) {
                list.add(itemList.get(i));
            }
        }
    }

    public String getLastPage() {
        return lastPage;
    }
}
