package com.zdfy.purereader.utils.protocol;

import com.alibaba.fastjson.JSON;
import com.zdfy.purereader.domain.NewsInfo;
import com.zdfy.purereader.domain.NewsInfo.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;

import java.util.List;

;

/**
 * Created by ZhangPeng on 2016/9/7.
 */
public class NewsProtocol extends BaseProtocol<List<ContentlistEntity>> {
    @Override
    protected List<ContentlistEntity> parseData(String result) {
        List<ContentlistEntity> mdatas = null;
        NewsInfo newsInfo = JSON.parseObject(result, NewsInfo.class);
        if (newsInfo != null) {
            mdatas = newsInfo.getShowapi_res_body().getPagebean().getContentlist();
        }
        return mdatas;
    }
}
