package com.zdfy.purereader.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yaozong on 2016/9/20.
 */

public class VideoInfo implements Serializable{


    private String newestIssueType;
    private String nextPageUrl;
    private long nextPublishTime;

    private List<IssueListBean> issueList;

    public String getNewestIssueType() {
        return newestIssueType;
    }

    public void setNewestIssueType(String newestIssueType) {
        this.newestIssueType = newestIssueType;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public long getNextPublishTime() {
        return nextPublishTime;
    }

    public void setNextPublishTime(long nextPublishTime) {
        this.nextPublishTime = nextPublishTime;
    }

    public List<IssueListBean> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<IssueListBean> issueList) {
        this.issueList = issueList;
    }

    public static class IssueListBean implements Serializable{
        private int count;
        private long date;
        private long publishTime;
        private long releaseTime;
        private String type;
        /**
         * data : {"author":{"description":"干货演讲，分享人生。","icon":"http://img.kaiyanapp.com/be0cdbff1cf1755c7aa78edee08db42b.jpeg","id":142,"latestReleaseTime":1474387831000,"link":"","name":"TED","videoNum":15},"category":"科普","collected":false,"consumption":{"collectionCount":331,"replyCount":30,"shareCount":1172},"cover":{"blurred":"http://img.kaiyanapp.com/98be7affb50cf59292fc1b039161da06.jpeg?imageMogr2/quality/60","detail":"http://img.kaiyanapp.com/a25708af95402984fcb288b6e17e7e99.jpeg?imageMogr2/quality/60","feed":"http://img.kaiyanapp.com/a25708af95402984fcb288b6e17e7e99.jpeg?imageMogr2/quality/60"},"dataType":"VideoBeanForClient","date":1474387200000,"description":"在古罗马法中，如果所有法官都一致认为嫌疑人有罪，该嫌疑人反而会遭到赦免，因为很多人同时意见一致的概率是太小了，因此意见的正确性值得商榷。毫无问题，反而令人怀疑，这就是「一致性悖论」。","duration":242,"id":9348,"idx":0,"playInfo":[{"height":480,"name":"标清","type":"normal","url":"http://baobab.wandoujia.com/api/v1/playUrl?vid=9348&editionType=normal","width":854},{"height":720,"name":"高清","type":"high","url":"http://baobab.wandoujia.com/api/v1/playUrl?vid=9348&editionType=high","width":1280}],"playUrl":"http://baobab.wandoujia.com/api/v1/playUrl?vid=9348&editionType=default","provider":{"alias":"bilibili","icon":"http://img.kaiyanapp.com/b968890a2b3e9ab5ae234175681f8cd4.png","name":"哔哩哔哩动画"},"releaseTime":1474387831000,"tags":[{"actionUrl":"eyepetizer://tag/44/?title=%E7%A7%91%E6%99%AE","id":44,"name":"科普"},{"actionUrl":"eyepetizer://tag/14/?title=%E5%8A%A8%E7%94%BB","id":14,"name":"动画"},{"actionUrl":"eyepetizer://tag/492/?title=%E7%83%A7%E8%84%91","id":492,"name":"烧脑"}],"title":"开学季丨一致性悖论","type":"NORMAL","webUrl":{"forWeibo":"http://wandou.im/2yk5og","raw":"http://www.wandoujia.com/eyepetizer/detail.html?vid=9348"}}
         * type : video
         */

        private List<ItemListBean> itemList;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public long getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(long releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public static class ItemListBean implements Serializable {
            /**
             * author : {"description":"干货演讲，分享人生。","icon":"http://img.kaiyanapp.com/be0cdbff1cf1755c7aa78edee08db42b.jpeg","id":142,"latestReleaseTime":1474387831000,"link":"","name":"TED","videoNum":15}
             * category : 科普
             * collected : false
             * consumption : {"collectionCount":331,"replyCount":30,"shareCount":1172}
             * cover : {"blurred":"http://img.kaiyanapp.com/98be7affb50cf59292fc1b039161da06.jpeg?imageMogr2/quality/60","detail":"http://img.kaiyanapp.com/a25708af95402984fcb288b6e17e7e99.jpeg?imageMogr2/quality/60","feed":"http://img.kaiyanapp.com/a25708af95402984fcb288b6e17e7e99.jpeg?imageMogr2/quality/60"}
             * dataType : VideoBeanForClient
             * date : 1474387200000
             * description : 在古罗马法中，如果所有法官都一致认为嫌疑人有罪，该嫌疑人反而会遭到赦免，因为很多人同时意见一致的概率是太小了，因此意见的正确性值得商榷。毫无问题，反而令人怀疑，这就是「一致性悖论」。
             * duration : 242
             * id : 9348
             * idx : 0
             * playInfo : [{"height":480,"name":"标清","type":"normal","url":"http://baobab.wandoujia.com/api/v1/playUrl?vid=9348&editionType=normal","width":854},{"height":720,"name":"高清","type":"high","url":"http://baobab.wandoujia.com/api/v1/playUrl?vid=9348&editionType=high","width":1280}]
             * playUrl : http://baobab.wandoujia.com/api/v1/playUrl?vid=9348&editionType=default
             * provider : {"alias":"bilibili","icon":"http://img.kaiyanapp.com/b968890a2b3e9ab5ae234175681f8cd4.png","name":"哔哩哔哩动画"}
             * releaseTime : 1474387831000
             * tags : [{"actionUrl":"eyepetizer://tag/44/?title=%E7%A7%91%E6%99%AE","id":44,"name":"科普"},{"actionUrl":"eyepetizer://tag/14/?title=%E5%8A%A8%E7%94%BB","id":14,"name":"动画"},{"actionUrl":"eyepetizer://tag/492/?title=%E7%83%A7%E8%84%91","id":492,"name":"烧脑"}]
             * title : 开学季丨一致性悖论
             * type : NORMAL
             * webUrl : {"forWeibo":"http://wandou.im/2yk5og","raw":"http://www.wandoujia.com/eyepetizer/detail.html?vid=9348"}
             */



            /*header*/
//            "dataType": "TextHeader",
//            "font": "lobster",
//            "text": "- Sep. 20 -"

            /*advertise*/


            private DataBean data;
            private String type;

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }


            public static class DataBean implements Serializable {

//            "dataType": "TextHeader",
//            "font": "lobster",
//            "text": "- Sep. 20 -"

                private String font;
                private String text;

                public String getFont() {
                    return font;
                }

                public void setFont(String font) {
                    this.font = font;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                /**
                 * description : 干货演讲，分享人生。
                 * icon : http://img.kaiyanapp.com/be0cdbff1cf1755c7aa78edee08db42b.jpeg
                 * id : 142
                 * latestReleaseTime : 1474387831000
                 * link :
                 * name : TED
                 * videoNum : 15
                 */

                private AuthorBean author;
                private String category;
                private boolean collected;
                /**
                 * collectionCount : 331
                 * replyCount : 30
                 * shareCount : 1172
                 */

                private ConsumptionBean consumption;
                /**
                 * blurred : http://img.kaiyanapp.com/98be7affb50cf59292fc1b039161da06.jpeg?imageMogr2/quality/60
                 * detail : http://img.kaiyanapp.com/a25708af95402984fcb288b6e17e7e99.jpeg?imageMogr2/quality/60
                 * feed : http://img.kaiyanapp.com/a25708af95402984fcb288b6e17e7e99.jpeg?imageMogr2/quality/60
                 */

                private CoverBean cover;
                private String dataType;
                private long date;
                private String description;
                private int duration;
                private int id;
                private int idx;
                private String playUrl;
                /**
                 * alias : bilibili
                 * icon : http://img.kaiyanapp.com/b968890a2b3e9ab5ae234175681f8cd4.png
                 * name : 哔哩哔哩动画
                 */

                private ProviderBean provider;
                private long releaseTime;
                private String title;
                private String type;
                /**
                 * forWeibo : http://wandou.im/2yk5og
                 * raw : http://www.wandoujia.com/eyepetizer/detail.html?vid=9348
                 */

                private WebUrlBean webUrl;
                /**
                 * height : 480
                 * name : 标清
                 * type : normal
                 * url : http://baobab.wandoujia.com/api/v1/playUrl?vid=9348&editionType=normal
                 * width : 854
                 */

                private List<PlayInfoBean> playInfo;
                /**
                 * actionUrl : eyepetizer://tag/44/?title=%E7%A7%91%E6%99%AE
                 * id : 44
                 * name : 科普
                 */

                private List<TagsBean> tags;

                public AuthorBean getAuthor() {
                    return author;
                }

                public void setAuthor(AuthorBean author) {
                    this.author = author;
                }

                public String getCategory() {
                    return category;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

                public boolean isCollected() {
                    return collected;
                }

                public void setCollected(boolean collected) {
                    this.collected = collected;
                }

                public ConsumptionBean getConsumption() {
                    return consumption;
                }

                public void setConsumption(ConsumptionBean consumption) {
                    this.consumption = consumption;
                }

                public CoverBean getCover() {
                    return cover;
                }

                public void setCover(CoverBean cover) {
                    this.cover = cover;
                }

                public String getDataType() {
                    return dataType;
                }

                public void setDataType(String dataType) {
                    this.dataType = dataType;
                }

                public long getDate() {
                    return date;
                }

                public void setDate(long date) {
                    this.date = date;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getIdx() {
                    return idx;
                }

                public void setIdx(int idx) {
                    this.idx = idx;
                }

                public String getPlayUrl() {
                    return playUrl;
                }

                public void setPlayUrl(String playUrl) {
                    this.playUrl = playUrl;
                }

                public ProviderBean getProvider() {
                    return provider;
                }

                public void setProvider(ProviderBean provider) {
                    this.provider = provider;
                }

                public long getReleaseTime() {
                    return releaseTime;
                }

                public void setReleaseTime(long releaseTime) {
                    this.releaseTime = releaseTime;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public WebUrlBean getWebUrl() {
                    return webUrl;
                }

                public void setWebUrl(WebUrlBean webUrl) {
                    this.webUrl = webUrl;
                }

                public List<PlayInfoBean> getPlayInfo() {
                    return playInfo;
                }

                public void setPlayInfo(List<PlayInfoBean> playInfo) {
                    this.playInfo = playInfo;
                }

                public List<TagsBean> getTags() {
                    return tags;
                }

                public void setTags(List<TagsBean> tags) {
                    this.tags = tags;
                }

                public static class AuthorBean implements Serializable{
                    private String description;
                    private String icon;
                    private int id;
                    private long latestReleaseTime;
                    private String link;
                    private String name;
                    private int videoNum;

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getIcon() {
                        return icon;
                    }

                    public void setIcon(String icon) {
                        this.icon = icon;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public long getLatestReleaseTime() {
                        return latestReleaseTime;
                    }

                    public void setLatestReleaseTime(long latestReleaseTime) {
                        this.latestReleaseTime = latestReleaseTime;
                    }

                    public String getLink() {
                        return link;
                    }

                    public void setLink(String link) {
                        this.link = link;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getVideoNum() {
                        return videoNum;
                    }

                    public void setVideoNum(int videoNum) {
                        this.videoNum = videoNum;
                    }
                }

                public static class ConsumptionBean implements Serializable{
                    private int collectionCount;
                    private int replyCount;
                    private int shareCount;

                    public int getCollectionCount() {
                        return collectionCount;
                    }

                    public void setCollectionCount(int collectionCount) {
                        this.collectionCount = collectionCount;
                    }

                    public int getReplyCount() {
                        return replyCount;
                    }

                    public void setReplyCount(int replyCount) {
                        this.replyCount = replyCount;
                    }

                    public int getShareCount() {
                        return shareCount;
                    }

                    public void setShareCount(int shareCount) {
                        this.shareCount = shareCount;
                    }
                }

                public static class CoverBean implements Serializable{
                    private String blurred;
                    private String detail;
                    private String feed;

                    public String getBlurred() {
                        return blurred;
                    }

                    public void setBlurred(String blurred) {
                        this.blurred = blurred;
                    }

                    public String getDetail() {
                        return detail;
                    }

                    public void setDetail(String detail) {
                        this.detail = detail;
                    }

                    public String getFeed() {
                        return feed;
                    }

                    public void setFeed(String feed) {
                        this.feed = feed;
                    }
                }

                public static class ProviderBean implements Serializable{
                    private String alias;
                    private String icon;
                    private String name;

                    public String getAlias() {
                        return alias;
                    }

                    public void setAlias(String alias) {
                        this.alias = alias;
                    }

                    public String getIcon() {
                        return icon;
                    }

                    public void setIcon(String icon) {
                        this.icon = icon;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }

                public static class WebUrlBean implements Serializable{
                    private String forWeibo;
                    private String raw;

                    public String getForWeibo() {
                        return forWeibo;
                    }

                    public void setForWeibo(String forWeibo) {
                        this.forWeibo = forWeibo;
                    }

                    public String getRaw() {
                        return raw;
                    }

                    public void setRaw(String raw) {
                        this.raw = raw;
                    }
                }

                public static class PlayInfoBean implements Serializable{
                    private int height;
                    private String name;
                    private String type;
                    private String url;
                    private int width;

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
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

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }
                }

                public static class TagsBean implements Serializable{
                    private String actionUrl;
                    private int id;
                    private String name;

                    public String getActionUrl() {
                        return actionUrl;
                    }

                    public void setActionUrl(String actionUrl) {
                        this.actionUrl = actionUrl;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }
        }
    }
}
