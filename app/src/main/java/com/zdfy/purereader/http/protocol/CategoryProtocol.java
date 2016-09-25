package com.zdfy.purereader.http.protocol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zdfy.purereader.domain.VideoCategoryInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yaozong on 2016/9/25.
 */

public class CategoryProtocol extends BaseProtocol<List<VideoCategoryInfo>> {
    @Override
    protected List<VideoCategoryInfo> parseData(String result) {
        List<VideoCategoryInfo> list = new ArrayList<>();
        JSONArray array = JSONArray.parseArray(result);
        for (int i = 0; i < array.size(); i++) {
            list.add(JSON.parseObject(array.get(i).toString(), VideoCategoryInfo.class));
        }
        return list;
    }
}
