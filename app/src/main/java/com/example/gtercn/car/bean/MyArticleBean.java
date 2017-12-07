package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : LiHang.
 * data : 2017/2/23.
 * description:
 */
public class MyArticleBean implements Serializable {


    /**
     * result : [{"id":"1545c357ce1846f59e5de3e939703081","user_id":"a57348bc5608401fa9f7c11563e7c7b6","type":3,"title":"森雅R7的西行漫游记","support_number":0,"favor_number":0,"glance_number":0,"content":"人生就是一次充满未知的旅行，在乎的是沿途的风景，在乎的是看风景的心情，旅行不会因为美丽的风景终止。走过的路成为背后的风景，不能回头不能停留，若此刻停留，将会错过更好的风景，保持一份平和，保持一份清醒。享受每一刻的感觉，欣赏每一处的风景，这就是人生。 一个人，一辆车，一次冒险，一次朝圣之旅，意外的一场雪，让这次旅行变得急剧考验，考验着我，同时也考验着森雅R7。途中的曲折真的可以写本小说了，在路线的选择上开始比较犯难，后来觉得去蔚县看打树花是一个不错的选择，一个爱情浪漫的故事，如果不是亲临感受，以为只是一个民间艺术，通过五彩缤纷的舞美，展示出人对爱情的执着与美好生活的向往。年轻、时尚的森雅R7陪我重走青春路，让自己仿佛找到昔日年轻时的风采。既然是西行漫游记，一次朝圣之旅净化自己心灵，卸下一份虚伪，获得一心安宁这不正是自己想要的吗。 通过几天的接触，行驶1600多公里，森雅R7此行稳定的发挥，让我充满信心，即便行驶途中遇到大雪，也没有阻挡继续前行的脚步。ABS、EBD、刹车辅助、牵引力控制、车身稳定系统和上坡辅助，安全配置非常丰富。不足十万的售价，这说明厂家真的很有诚意。 当用一颗淡然的心面对生活时，用一个本真的自我体悟人生时！一场旅行是最好的选择，一次静心之旅。","res_url_list":"http://192.168.1.71/carhome/question/1545c357ce1846f59e5de3e939703081/1485217964461.JPEG","delete_flag":0,"insert_time":"2017-01-25 08:51:31","update_time":"2017-02-17 09:51:55","publish_time":null,"expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":null,"motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":null,"html_url":"http://192.168.1.71/carhome/car_home_html/article2017-1-9.html"}]
     * err_code : 0
     * err_message : OK
     * message : null
     */

    private String err_code;
    private String err_message;
    private Object message;
    /**
     * id : 1545c357ce1846f59e5de3e939703081
     * user_id : a57348bc5608401fa9f7c11563e7c7b6
     * type : 3
     * title : 森雅R7的西行漫游记
     * support_number : 0
     * favor_number : 0
     * glance_number : 0
     * content : 人生就是一次充满未知的旅行，在乎的是沿途的风景，在乎的是看风景的心情，旅行不会因为美丽的风景终止。走过的路成为背后的风景，不能回头不能停留，若此刻停留，将会错过更好的风景，保持一份平和，保持一份清醒。享受每一刻的感觉，欣赏每一处的风景，这就是人生。 一个人，一辆车，一次冒险，一次朝圣之旅，意外的一场雪，让这次旅行变得急剧考验，考验着我，同时也考验着森雅R7。途中的曲折真的可以写本小说了，在路线的选择上开始比较犯难，后来觉得去蔚县看打树花是一个不错的选择，一个爱情浪漫的故事，如果不是亲临感受，以为只是一个民间艺术，通过五彩缤纷的舞美，展示出人对爱情的执着与美好生活的向往。年轻、时尚的森雅R7陪我重走青春路，让自己仿佛找到昔日年轻时的风采。既然是西行漫游记，一次朝圣之旅净化自己心灵，卸下一份虚伪，获得一心安宁这不正是自己想要的吗。 通过几天的接触，行驶1600多公里，森雅R7此行稳定的发挥，让我充满信心，即便行驶途中遇到大雪，也没有阻挡继续前行的脚步。ABS、EBD、刹车辅助、牵引力控制、车身稳定系统和上坡辅助，安全配置非常丰富。不足十万的售价，这说明厂家真的很有诚意。 当用一颗淡然的心面对生活时，用一个本真的自我体悟人生时！一场旅行是最好的选择，一次静心之旅。
     * res_url_list : http://192.168.1.71/carhome/question/1545c357ce1846f59e5de3e939703081/1485217964461.JPEG
     * delete_flag : 0
     * insert_time : 2017-01-25 08:51:31
     * update_time : 2017-02-17 09:51:55
     * publish_time : null
     * expert_name : null
     * expert_portrait : null
     * user_portrait : null
     * reply_num : null
     * motto : null
     * telephone_number : null
     * expert_wechat_number : null
     * nickname : null
     * is_favored : null
     * html_url : http://192.168.1.71/carhome/car_home_html/article2017-1-9.html
     */

    private List<ResultBean> result;

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_message() {
        return err_message;
    }

    public void setErr_message(String err_message) {
        this.err_message = err_message;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MyArticleBean{" +
                "err_code='" + err_code + '\'' +
                ", err_message='" + err_message + '\'' +
                ", message=" + message +
                ", result=" + result +
                '}';
    }

    public static class ResultBean implements Serializable {
        private String id;
        private String user_id;
        private int type;
        private String title;
        private int support_number;
        private int favor_number;
        private int glance_number;
        private String content;
        private String res_url_list;
        private int delete_flag;
        private String insert_time;
        private String update_time;
        private Object publish_time;
        private Object expert_name;
        private Object expert_portrait;
        private Object user_portrait;
        private Object reply_num;
        private Object motto;
        private Object telephone_number;
        private Object expert_wechat_number;
        private Object nickname;
        private Object is_favored;
        private String html_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSupport_number() {
            return support_number;
        }

        public void setSupport_number(int support_number) {
            this.support_number = support_number;
        }

        public int getFavor_number() {
            return favor_number;
        }

        public void setFavor_number(int favor_number) {
            this.favor_number = favor_number;
        }

        public int getGlance_number() {
            return glance_number;
        }

        public void setGlance_number(int glance_number) {
            this.glance_number = glance_number;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRes_url_list() {
            return res_url_list;
        }

        public void setRes_url_list(String res_url_list) {
            this.res_url_list = res_url_list;
        }

        public int getDelete_flag() {
            return delete_flag;
        }

        public void setDelete_flag(int delete_flag) {
            this.delete_flag = delete_flag;
        }

        public String getInsert_time() {
            return insert_time;
        }

        public void setInsert_time(String insert_time) {
            this.insert_time = insert_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public Object getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(Object publish_time) {
            this.publish_time = publish_time;
        }

        public Object getExpert_name() {
            return expert_name;
        }

        public void setExpert_name(Object expert_name) {
            this.expert_name = expert_name;
        }

        public Object getExpert_portrait() {
            return expert_portrait;
        }

        public void setExpert_portrait(Object expert_portrait) {
            this.expert_portrait = expert_portrait;
        }

        public Object getUser_portrait() {
            return user_portrait;
        }

        public void setUser_portrait(Object user_portrait) {
            this.user_portrait = user_portrait;
        }

        public Object getReply_num() {
            return reply_num;
        }

        public void setReply_num(Object reply_num) {
            this.reply_num = reply_num;
        }

        public Object getMotto() {
            return motto;
        }

        public void setMotto(Object motto) {
            this.motto = motto;
        }

        public Object getTelephone_number() {
            return telephone_number;
        }

        public void setTelephone_number(Object telephone_number) {
            this.telephone_number = telephone_number;
        }

        public Object getExpert_wechat_number() {
            return expert_wechat_number;
        }

        public void setExpert_wechat_number(Object expert_wechat_number) {
            this.expert_wechat_number = expert_wechat_number;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public Object getIs_favored() {
            return is_favored;
        }

        public void setIs_favored(Object is_favored) {
            this.is_favored = is_favored;
        }

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id='" + id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", type=" + type +
                    ", title='" + title + '\'' +
                    ", support_number=" + support_number +
                    ", favor_number=" + favor_number +
                    ", glance_number=" + glance_number +
                    ", content='" + content + '\'' +
                    ", res_url_list='" + res_url_list + '\'' +
                    ", delete_flag=" + delete_flag +
                    ", insert_time='" + insert_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", publish_time=" + publish_time +
                    ", expert_name=" + expert_name +
                    ", expert_portrait=" + expert_portrait +
                    ", user_portrait=" + user_portrait +
                    ", reply_num=" + reply_num +
                    ", motto=" + motto +
                    ", telephone_number=" + telephone_number +
                    ", expert_wechat_number=" + expert_wechat_number +
                    ", nickname=" + nickname +
                    ", is_favored=" + is_favored +
                    ", html_url='" + html_url + '\'' +
                    '}';
        }
    }
}
