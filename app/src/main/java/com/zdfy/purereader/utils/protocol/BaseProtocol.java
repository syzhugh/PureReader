package com.zdfy.purereader.utils.protocol;

import com.zdfy.purereader.utils.HttpUtils;
import com.zdfy.purereader.utils.IOUtils;
import com.zdfy.purereader.utils.StringUtils;
import com.zdfy.purereader.utils.UiUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ZhangPeng on 2016/9/7.
 */
public abstract class BaseProtocol<T> {
    public T getData(String UrlForName) {
        String result = getCache(UrlForName);
        if (StringUtils.isEmpty(result)) {
            result = getDataFromNet(UrlForName);
        }
        if (result != null) {
            return parseData(result);
        }
        return null;
    }

    /**
     * 解析数据,由子类实现
     *
     * @param result
     * @return
     */
    protected abstract T parseData(String result);

    /**
     * 从网络获取数据,由子类实现
     *
     * @param urlForName
     * @return
     */
    protected String getDataFromNet(String urlForName) {
        String result = HttpUtils.doPost(urlForName, null);
        if (!StringUtils.isEmpty(result)) {
            setCache(urlForName, result);
            return result;
        }
        return null;
    }
        /**
         * 设置缓存
         *
         * @param UrlForName 地址名为key值
         * @param json       json串为value
         */

    public void setCache(String UrlForName, String json) {
        File cacheDir = UiUtils.getContext().getCacheDir();
        File cacheFile = new File(cacheDir, UrlForName);
        FileWriter fw = null;
        try {
            fw = new FileWriter(cacheFile);
            //设置过期的时间
            long deadLine = System.currentTimeMillis() + 60 * 1000 * 30;
            fw.write(deadLine + "\n");
            fw.write(json);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(fw);
        }
    }

    /**
     * 读取缓存
     *
     * @param UrlForName
     * @return
     */
    public String getCache(String UrlForName) {
        File cacheDir = UiUtils.getContext().getCacheDir();
        File cacheFile = new File(cacheDir, UrlForName);
        if (cacheFile.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(cacheFile));
                String deadLine = br.readLine();
                long deadTime = Long.parseLong(deadLine);
                if (System.currentTimeMillis() < deadTime) {
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(br);
            }
        }
        return null;
    }
}
