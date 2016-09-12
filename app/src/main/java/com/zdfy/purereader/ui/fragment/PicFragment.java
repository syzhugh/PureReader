package com.zdfy.purereader.ui.fragment;

import android.os.Message;
import android.view.View;

import com.zdfy.purereader.constant.GankApis;
import com.zdfy.purereader.http.protocol.GankImgProtocol;
import com.zdfy.purereader.ui.view.LoadingPage;
/**
 * Created by ZhangPeng on 2016/9/6.
 */
public class PicFragment extends BaseFragment {
    private int page=1;
    private GankImgProtocol mImgProtocol;
    @Override
    protected void handleMsgByChild(Message msg) {
        
    }

    @Override
    protected void initImplViews() {

    }

    @Override
    protected LoadingPage.ResultState onLoad() {
        if (mImgProtocol==null){
            mImgProtocol=new GankImgProtocol();
        }
        return CheckData(mImgProtocol.getData(GankApis.getBaseFuLiImages_Url(page),2));
    }

    @Override
    protected View onCreateSuccessView() {
        return null;
    }

    @Override
    protected void LoadMoreDatas() {

    }

    @Override
    protected void RefreshDatas() {

    }
}
