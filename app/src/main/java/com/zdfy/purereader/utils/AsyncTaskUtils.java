package com.zdfy.purereader.utils;

import android.os.AsyncTask;

/**
 * Created by ZhangPeng on 2016/9/8.
 */
public class AsyncTaskUtils extends AsyncTask<String,Void,String>{
    private String mResult;
    private String url;
    public String getResult(String url) {
        this.url=url;
        return mResult;
    }

    @Override
        protected String doInBackground(String... strings) {
            return HttpUtils.doPost(url,null);
        }
        @Override
        protected void onPostExecute(String s) {
            mResult = s;
            super.onPostExecute(s);
        }
}
