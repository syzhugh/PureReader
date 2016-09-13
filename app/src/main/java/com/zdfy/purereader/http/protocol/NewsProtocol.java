package com.zdfy.purereader.http.protocol;

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

        NewsInfo newsInfo = JSON.parseObject(result, NewsInfo.class);
        if (newsInfo != null&&newsInfo.getShowapi_res_body()!=null&&newsInfo.getShowapi_res_body().getPagebean()!=null) {
            List<ContentlistEntity> mdatas = newsInfo.getShowapi_res_body().getPagebean().getContentlist();
            return mdatas;
        }
        return null;
    }
}
