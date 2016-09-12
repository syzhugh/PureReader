package com.zdfy.purereader.domain;

import java.util.List;

/**
 * Created by ZhangPeng on 2016/9/12.
 */
public class GankImgInfo {


    /**
     * error : false
     * results : [{"_id":"57d5ed65421aa909895ffa5f","createdAt":"2016-09-12T07:48:53.693Z","desc":"9 - 12","publishedAt":"2016-09-12T11:34:31.135Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1f7qgschtz8j20hs0hsac7.jpg","used":true,"who":"代码家"},{"_id":"57d16f1b421aa90d2d8a4f52","createdAt":"2016-09-08T22:00:59.815Z","desc":"09-09","publishedAt":"2016-09-09T11:53:48.777Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f7mixvc7emj20ku0dv74p.jpg","used":true,"who":"代码家"},{"_id":"57d0a893421aa90d312ebeff","createdAt":"2016-09-08T07:53:55.620Z","desc":"9-9","publishedAt":"2016-09-08T11:43:04.471Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1f7lughzrjmj20u00k9jti.jpg","used":true,"who":"代码家"},{"_id":"57cf601d421aa90d2d8a4f3b","createdAt":"2016-09-07T08:32:29.460Z","desc":"09-07","publishedAt":"2016-09-07T11:50:57.951Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1f7kpy9czh0j20u00vn0us.jpg","used":true,"who":"代码家"},{"_id":"57ce103a421aa910f56bd8b9","createdAt":"2016-09-06T08:39:22.211Z","desc":"9-7","publishedAt":"2016-09-06T11:35:21.379Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f7jkj4hl41j20u00mhq68.jpg","used":true,"who":"代码家"},{"_id":"57cc16c9421aa910f56bd8ab","createdAt":"2016-09-04T20:42:49.403Z","desc":"09-05","publishedAt":"2016-09-05T11:32:16.999Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1f7hu7d460oj20u00u075u.jpg","used":true,"who":"daimajia"},{"_id":"57c83167421aa9125fa3edd0","createdAt":"2016-09-01T21:47:19.924Z","desc":"Whatever","publishedAt":"2016-09-02T20:36:28.951Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1f7ef7i5m1zj20u011hdjm.jpg","used":true,"who":"daimajia"},{"_id":"57c6dcd0421aa9125fa3edc5","createdAt":"2016-08-31T21:34:08.961Z","desc":"9-1","publishedAt":"2016-09-01T11:31:19.288Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f7d97id9mbj20u00u0q4g.jpg","used":true,"who":"daimajia"},{"_id":"57c6265c421aa9125d96f53c","createdAt":"2016-08-31T08:35:40.27Z","desc":"8-31","publishedAt":"2016-08-31T11:41:56.41Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034jw1f7cmpd95iaj20u011hjtt.jpg","used":true,"who":"代码家"},{"_id":"57c4fd22421aa9125fa3edb5","createdAt":"2016-08-30T11:27:30.559Z","desc":"8-30","publishedAt":"2016-08-30T11:38:36.625Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034gw1f7bm1unn17j20u00u00wm.jpg","used":true,"who":"代码家"}]
     */

    private boolean error;
    /**
     * _id : 57d5ed65421aa909895ffa5f
     * createdAt : 2016-09-12T07:48:53.693Z
     * desc : 9 - 12
     * publishedAt : 2016-09-12T11:34:31.135Z
     * source : chrome
     * type : 福利
     * url : http://ww1.sinaimg.cn/large/610dc034jw1f7qgschtz8j20hs0hsac7.jpg
     * used : true
     * who : 代码家
     */

    private List<ResultsEntity> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity {
        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
