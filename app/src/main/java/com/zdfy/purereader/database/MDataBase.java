package com.zdfy.purereader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.zdfy.purereader.domain.VideoFindInfo;
import com.zdfy.purereader.domain.VideoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yaozong on 2016/9/26.
 */

public class MDataBase {
    private Context context;
    private DBHelper helper;
    private SQLiteDatabase database;

    public MDataBase(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    public boolean addVideoItem(VideoInfo.IssueListBean.ItemListBean.DataBean dataBean) {
        if (checkVideoItem(dataBean.getId()))
            return false;

        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.TAB1COLUME2_REMOTEID, dataBean.getId());
        values.put(DBHelper.TAB1COLUME3_DATA, JSONObject.toJSONString(dataBean));
        if (database.insert(DBHelper.TAB1NAME, null, values) > 0) {
            return true;
        }
        database.close();

        return false;
    }

    public boolean addVideoItem(VideoFindInfo.ItemListBean.DataBean dataBean) {
        if (checkVideoItem(dataBean.getId()))
            return false;
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.TAB1COLUME2_REMOTEID, dataBean.getId());
        values.put(DBHelper.TAB1COLUME3_DATA, JSONObject.toJSONString(dataBean));
        if (database.insert(DBHelper.TAB1NAME, null, values) > 0) {
            return true;
        }
        database.close();
        return false;
    }

    public boolean delItem(int _id) {
        database = helper.getWritableDatabase();
        int i = database.delete(DBHelper.TAB1NAME,
                DBHelper.TAB1COLUME2_REMOTEID + " = ?", new String[]{"" + _id});
        database.close();
        return i > 0;
    }

    public boolean delAll() {
        database = helper.getWritableDatabase();
        int i = database.delete(DBHelper.TAB1NAME, null, null);
        database.close();

        return i > 0;
    }


    public boolean checkVideoItem(int remoteId) {
        database = helper.getReadableDatabase();
        String sqlStr = String.format("select * from %s where %s = %s;",
                DBHelper.TAB1NAME,
                DBHelper.TAB1COLUME2_REMOTEID,
                String.valueOf(remoteId)
        );
        Log.i("info", "MDataBase:checkVideoItem----------------------");
        Log.i("info", "" + sqlStr);
        Cursor cursor = database.rawQuery(sqlStr, null);
        if (cursor.moveToNext())
            return true;

        cursor.close();
        database.close();
        return false;
    }

    public List<VideoInfo.IssueListBean.ItemListBean.DataBean> getAll() {
        List<VideoInfo.IssueListBean.ItemListBean.DataBean> list = new ArrayList();
        database = helper.getReadableDatabase();
        String sqlStr = String.format("select * from %s;", DBHelper.TAB1NAME);
        Log.i("info", "MDataBase:getAll----------------------");
        Log.i("info", "sqlStr:" + sqlStr);
        Cursor cursor = database.rawQuery(sqlStr, null);
        while (cursor.moveToNext()) {
            String _data = cursor.getString(cursor.getColumnIndex(DBHelper.TAB1COLUME3_DATA));
            list.add(JSONObject.parseObject(_data, VideoInfo.IssueListBean.ItemListBean.DataBean.class));
        }
        cursor.close();
        database.close();

        Log.i("info", "MDataBase:getAll----------------------");
        Log.i("info", "list:" + list.size());
        return list;
    }
}
