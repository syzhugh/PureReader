package com.zdfy.purereader.domain;

import java.util.List;

/**
 * Created by ZhangPeng on 2016/9/18.
 */
public class ZhiHuInfo {
    /**
     * date : 20160917
     * stories : [{"ga_prefix":"091722","id":8803440,"images":["http://pic4.zhimg.com/8c882956e2c314b0f8ee7c2a0b0dc32f.jpg"],"title":"小事 · 被羞辱的女同学","type":0},{"ga_prefix":"091721","id":8801976,"images":["http://pic4.zhimg.com/d1cc9b23e91e791d386bb148043c17c7.jpg"],"multipic":true,"title":"宇航员的尿裤、女性的裙裤，这片子细节太有意思了","type":0},{"ga_prefix":"091720","id":8803189,"images":["http://pic4.zhimg.com/44ded01ab8260b6eea11a52922d262bb.jpg"],"title":"研究吃什么能变聪明，有位教授一直拿自己做实验","type":0},{"ga_prefix":"091719","id":8802728,"images":["http://pic4.zhimg.com/9676c2e5f7ec72bd89fa01a47ad0207b.jpg"],"title":"为什么会说「家不是一个讲道理的地方」？","type":0},{"ga_prefix":"091718","id":8802364,"images":["http://pic4.zhimg.com/5ae8a9e8209ce2f6a60e291841701a27.jpg"],"title":"既然许多疾病可以自愈，我们可不可以少吃药，多靠自愈？","type":0},{"ga_prefix":"091717","id":8799113,"images":["http://pic2.zhimg.com/a5c37c0e6e299f2c063b947955379079.jpg"],"title":"知乎好问题 · 如何让下班之后的生活变得丰富而且健康？","type":0},{"ga_prefix":"091716","id":8787734,"images":["http://pic4.zhimg.com/339d3f71fed12d847ca5abdb3910618b.jpg"],"multipic":true,"title":"翻滚的岩浆就在眼前，来感受全球十大最灼人的火山探险","type":0},{"ga_prefix":"091715","id":8796967,"images":["http://pic3.zhimg.com/b84a0f40263465622806a99ec905116e.jpg"],"title":"咖啡酸到底是什么？有人爱之，有人恶之","type":0},{"ga_prefix":"091714","id":8802142,"images":["http://pic2.zhimg.com/53bb1b879c94e251666be7828c270ebd.jpg"],"title":"假期结束不想上班，感觉自己「职业倦怠」该怎么办","type":0},{"ga_prefix":"091713","id":8798267,"images":["http://pic2.zhimg.com/000491b68090c43e2138cdfd952a8609.jpg"],"title":"最后的时刻，花 100 万买 10 个月的命，你买吗？","type":0},{"ga_prefix":"091712","id":8801556,"images":["http://pic4.zhimg.com/83ebc8c844360703273e0d6660c60857.jpg"],"title":"大误 · 妲己给纣王为什么喂的是葡萄？","type":0},{"ga_prefix":"091711","id":8797491,"images":["http://pic1.zhimg.com/51ad5a5016d983486d4c096003cdb124.jpg"],"multipic":true,"title":"想在家里为自己的精神空间划一个「结界」，那就是和室啦","type":0},{"ga_prefix":"091710","id":8801369,"images":["http://pic4.zhimg.com/9c9b252fe574cbd503c861edfa61a853.jpg"],"title":"作为「人肉驱动车辆」，自行车的功率和扭矩怎么样？","type":0},{"ga_prefix":"091709","id":8800351,"images":["http://pic2.zhimg.com/1cbd23ad70422974fce25528522f41c1.jpg"],"title":"先有了我的家乡 York，才有了美国的 New York","type":0},{"ga_prefix":"091708","id":8801654,"images":["http://pic2.zhimg.com/285c0d55230ec85fb430b46d714f8f35.jpg"],"title":"投行常说的「财务模型」，其实就是用 Excel 做的","type":0},{"ga_prefix":"091707","id":8801139,"images":["http://pic1.zhimg.com/20382971f48562a73d99988cc1dd4e2c.jpg"],"title":"天宫二号带的这台原子钟，还能让 GPS 定位更精准","type":0},{"ga_prefix":"091707","id":8794417,"images":["http://pic4.zhimg.com/2b49c628558b0ee38952f6bd13d7513b.jpg"],"title":"「终于测出来啦，这物种和说好的果然都不一样\u2026\u2026」","type":0},{"ga_prefix":"091707","id":8800363,"images":["http://pic2.zhimg.com/4c617ce5c4f8e8ba4a2d51482d4ef38d.jpg"],"title":"为什么老年人更容易上当受骗？","type":0},{"ga_prefix":"091707","id":8801653,"images":["http://pic2.zhimg.com/0e4bed8837e1c47c3f0894dcc4069bed.jpg"],"title":"读读日报 24 小时热门 TOP 5 · 台风来袭的夜晚，我成了获利者","type":0},{"ga_prefix":"091706","id":8800327,"images":["http://pic3.zhimg.com/034c1f1ac36b46502f45a9e568d8548a.jpg"],"title":"瞎扯 · 如何正确地吐槽","type":0}]
     */

    private String date;
    /**
     * ga_prefix : 091722
     * id : 8803440
     * images : ["http://pic4.zhimg.com/8c882956e2c314b0f8ee7c2a0b0dc32f.jpg"]
     * title : 小事 · 被羞辱的女同学
     * type : 0
     */

    private List<StoriesEntity> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public static class StoriesEntity {
        private String ga_prefix;
        private int id;
        private String title;
        private int type;
        private List<String> images;

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
