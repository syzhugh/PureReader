package com.zdfy.purereader.http.protocol;

import com.zdfy.purereader.domain.DouBanInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangPeng on 2016/9/11.
 */
public class DouBanProtocol extends BaseProtocol<List<DouBanInfo>> {

    @Override
    protected List<DouBanInfo> parseData(String result) {
        List<DouBanInfo> datas = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray array = jsonObject.getJSONArray("posts");
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                String thumb_medium = null;
                if (o.getJSONArray("thumbs").length() != 0) {
                    thumb_medium = o.getJSONArray("thumbs").getJSONObject(0).getJSONObject("medium").getString("url");
                }
                String shareUrl = o.getString("url");
                System.out.println("shareUrl"+shareUrl);
                DouBanInfo douBanInfo = new DouBanInfo(
                        o.getInt("id"),
                        o.getString("title"),
                        o.getString("abstract"),
                        thumb_medium
                        ,shareUrl
                );
                datas.add(douBanInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datas;
    }
}
