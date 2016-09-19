package com.zdfy.purereader.http.protocol;

import com.alibaba.fastjson.JSON;
import com.zdfy.purereader.domain.ZhiHuInfo;
import com.zdfy.purereader.domain.ZhiHuInfo.StoriesEntity;

import java.util.List;
/**
 * Created by ZhangPeng on 2016/9/18.
 */
public class ZhiHuProtocol extends BaseProtocol<List<StoriesEntity>> {
    @Override
    protected List<StoriesEntity> parseData(String result) {
        List<StoriesEntity> datas = null;
        ZhiHuInfo zhiHuInfo = JSON.parseObject(result, ZhiHuInfo.class);
        if (zhiHuInfo!=null){
            datas=zhiHuInfo.getStories();
        }
        return datas;
    }
}
