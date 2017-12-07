package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : LiHang.
 * data : 2016/12/20.
 * description:
 */
public class ReplyBean implements Serializable {


    /**
     * result : [{"id":"09bbb6d4c66149428c51f0f2f3e48fe7","user_id":"a7810bebc1f74a6fa4af4705440e5cfb","to_user_id":"53e930f4eab24d3db501f50d9258455f","type_id":0,"question_id":"1","item_id":"b522312f99c04304bde0f8ea61013587","content":"提问者回复某个回复者","delete_flag":0,"insert_time":"2016-12-27","update_time":"2016-12-27","nickname":"天天","to_nickname":null,"reply_num":null},{"id":"b522312f99c04304bde0f8ea61013587","user_id":"53e930f4eab24d3db501f50d9258455f","to_user_id":"a7810bebc1f74a6fa4af4705440e5cfb","type_id":0,"question_id":"1","item_id":"da134577957f41f9a25940574c670c68","content":"这是回复某个回复者","delete_flag":0,"insert_time":"2016-12-27","update_time":"2016-12-27","nickname":null,"to_nickname":"天天","reply_num":null},{"id":"da134577957f41f9a25940574c670c68","user_id":"a7810bebc1f74a6fa4af4705440e5cfb","to_user_id":"3ca8cd3b67cb4a518367c9d7700eabf4","type_id":0,"question_id":"1","item_id":"","content":"这是回复提问者","delete_flag":0,"insert_time":"2016-12-27","update_time":"2016-12-27","nickname":"天天","to_nickname":"test","reply_num":null}]
     * err_code : 0
     * err_message : OK
     * message : null
     */

    private String err_code;
    private String err_message;
    private Object message;
    /**
     * id : 09bbb6d4c66149428c51f0f2f3e48fe7
     * user_id : a7810bebc1f74a6fa4af4705440e5cfb
     * to_user_id : 53e930f4eab24d3db501f50d9258455f
     * type_id : 0
     * question_id : 1
     * item_id : b522312f99c04304bde0f8ea61013587
     * content : 提问者回复某个回复者
     * delete_flag : 0
     * insert_time : 2016-12-27
     * update_time : 2016-12-27
     * nickname : 天天
     * to_nickname : null
     * reply_num : null
     */

    private List<ResultBean> result;

    @Override
    public String toString() {
        return "ReplyBean{" +
                "err_code='" + err_code + '\'' +
                ", err_message='" + err_message + '\'' +
                ", message=" + message +
                ", result=" + result +
                '}';
    }

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

    public static class ResultBean implements Serializable {
        private String id;
        private String user_id;
        private String to_user_id;
        private int type_id;
        private String question_id;
        private String item_id;
        private String content;
        private int delete_flag;
        private String insert_time;
        private String update_time;
        private String nickname;
        private Object to_nickname;
        private Object reply_num;

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

        public String getTo_user_id() {
            return to_user_id;
        }

        public void setTo_user_id(String to_user_id) {
            this.to_user_id = to_user_id;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getTo_nickname() {
            return to_nickname;
        }

        public void setTo_nickname(Object to_nickname) {
            this.to_nickname = to_nickname;
        }

        public Object getReply_num() {
            return reply_num;
        }

        public void setReply_num(Object reply_num) {
            this.reply_num = reply_num;
        }


        @Override
        public String toString() {
            return "ResultBean{" +
                    "id='" + id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", to_user_id='" + to_user_id + '\'' +
                    ", type_id=" + type_id +
                    ", question_id='" + question_id + '\'' +
                    ", item_id='" + item_id + '\'' +
                    ", content='" + content + '\'' +
                    ", delete_flag=" + delete_flag +
                    ", insert_time='" + insert_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", to_nickname=" + to_nickname +
                    ", reply_num=" + reply_num +
                    '}';
        }
    }
}
