package com.zdfy.purereader.utils.protocol;

import com.zdfy.purereader.domain.NewsInfo.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;

import java.util.List;

;

/**
 * Created by ZhangPeng on 2016/9/7.
 */
public class NewsProtocol extends BaseProtocol<List<ContentlistEntity>> {
    @Override
    protected List<ContentlistEntity> parseData(String result) {
        return null;
    }
}
