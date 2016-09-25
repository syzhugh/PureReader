package com.zdfy.purereader.http.protocol;

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
        list.addAll(info.getIssueList().get(0).getItemList());
        list.addAll(info.getIssueList().get(1).getItemList());

        return list;
    }

    public String getLastPage() {
        return lastPage;
    }
}
