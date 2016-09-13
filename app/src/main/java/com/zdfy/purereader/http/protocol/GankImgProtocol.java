package com.zdfy.purereader.http.protocol;

import com.alibaba.fastjson.JSON;
import com.zdfy.purereader.domain.GankImgInfo;
import com.zdfy.purereader.domain.GankImgInfo.ResultsEntity;

import java.util.List;

/**
 * Created by ZhangPeng on 2016/9/12.
 */
public class GankImgProtocol extends BaseProtocol<List<ResultsEntity>> {
    @Override
    protected List<ResultsEntity> parseData(String result) {
        List<ResultsEntity> results = null;
        GankImgInfo gankImgInfo = JSON.parseObject(result, GankImgInfo.class);
        if (gankImgInfo!=null){
             results = gankImgInfo.getResults();
        }
        return results;
    }
}
