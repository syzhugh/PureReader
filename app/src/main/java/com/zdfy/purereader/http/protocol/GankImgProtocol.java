package com.zdfy.purereader.http.protocol;

import com.zdfy.purereader.domain.GankImgInfo;

import java.util.List;

/**
 * Created by ZhangPeng on 2016/9/12.
 */
public class GankImgProtocol extends BaseProtocol<List<GankImgInfo.ResultsEntity>> {
    @Override
    protected List<GankImgInfo.ResultsEntity> parseData(String result) {
        return null;
    }
}
