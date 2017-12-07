package com.example.gtercn.car.api;

import com.example.gtercn.car.interfaces.ResponseCallbackHandler;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.interfaces.ResponseStringListener;
import com.example.gtercn.car.utils.TLog;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2016-5-18.
 * 接口管理类
 */
public class ApiManager {
    private static final String TAG = ApiManager.class.getSimpleName();

    /**
     * sharePreference file's name
     */
    public static final String SHARE_NAME = "Car";

    /**
     * 向导控制参数，
     * true: 运行了向导
     * false：没有运行向导
     */
    public static final String APP_FIRST = "hasGuide";

    /**
     * 城市编码字段
     */
    public static final String CITY_CODE = "city_code";

    /**
     * latitude, longitude.
     */
    public static final String LAT = "latitude";
    public static final String LNG = "longitude";


    /**
     * 域名或服务器 + 端口号
     */
    //内网
//    private static final String HTTPS = "https://192.168.1.192/car_home/app/v1";
//    private static final String HTTP = "http://192.168.1.192/car_home/app/v1";

    //外网测试环境
    private static final String HTTPS = "https://api.shunjiatianxia.com/car_home/app/v1";
    private static final String HTTP = "http://api.shunjiatianxia.com/car_home/app/v1";

    public static final String URL_RESCUE = HTTP + "/open/service/rescuelist";

    public static final String URL_APPOINTMENT = "";

    /**
     * 同步服务器时间
     */
    public static final String URL_TIMESTAMP = HTTP + "/open/server/resources";

    /**
     * 登录地址
     */

    public static final String URL_LOGIN = HTTPS + "/account/open/login";

    /**
     * 无用户名和密码登录地址
     */

    public static final String URL_AUTO_LOGIN = HTTP + "/account/open/info";

    /**
     * 注册地址
     */
    public static final String URL_REGISTER = HTTPS + "/account/open/register";
    /**
     * 更新头像
     */
    public static final String URL_HEADER = HTTPS + "/user/avatar/change";
    /**
     * 退出登录
     */
    public static final String URL_LOGOUT = HTTPS + "/account/open/logout";
    /**
     * 注册获取验证码
     */
    public static final String URL_VERIFICATION = HTTPS + "/account/open/verify/unregister";

    /**
     * 我的收藏
     */
    public static final String URL_MY_FAVORADD = HTTP + "/user/personal/favor";

    /**
     * 添加收藏
     */
    public static final String URL_FAVORADD = HTTP + "/favor/add";

    /**
     * 删除收藏
     */
    public static final String URL_FAVOR_DEL = HTTP + "/favor/delete";


    /**
     * 删除收藏
     */
    public static final String URL_FAVORDEL = HTTP + "/favor/delete";
    /**
     * 达人榜分类
     */
    public static final String URL_EXPERT_TYPE = HTTP + "/open/expert/type";

    /**
     * 达人榜
     */
    public static final String URL_EXPERT_TOP = HTTP + "/open/expert/top";

    /**
     * 达人圈列表
     */
    public static final String URL_EXPERT_CIRCLE_LIST = HTTP + "/expert/open/article/list";
    /**
     * 达人圈浏览数增加
     */
    public static final String URL_ARTICLE_GLANCENUM = HTTP +"/expert/open/article/glancenum";

    /**
     * 达人圈详情页查询
     */
    public static final String URL_ARTICLE_DETAIL_QUERY = HTTP +"/expert/open/article/query";
    /**
     * 问题墙列表
     *
     */
    public static final String URL_QUESTION_WALL_LIST = HTTP + "/expert/open/question/list";

    /**
     * 问题墙详情页;
     */
    public static final String URL_QUESTION_WALL_DETAIL = HTTP + "/expert/open/question/query";

    /**
     * 回复列表
     */
    public static final String URL_REPLY_LIST = HTTP + "/expert/reply/list";

    /**
     * 提交回复
     */
    public static final String URL_REPLY_SUBMIT = HTTP + "/expert/reply/add";

    /**
     * 提交问题
     */
    public static final String URL_QUESTION_SUBMIT = HTTP + "/expert/question/add";
    /**
     * 主页广告条
     */
    public static final String URL_AD = HTTP + "/open/home/advert/list";

    /**
     * 忘记密码
     */
    public static final String URL_FORGETPASSWORD = HTTPS + "/account/open/password/forget";

    /**
     * 修改手机号码第一步
     */
    public static final String URL_CHAGEPHONEONE = HTTPS + "/account/phone/change";

    /**
     * 修改手机号码第二步
     */
    public static final String URL_CHAGEPHONETWO = HTTPS + "/account/phone/change/next";

    /**
     * 忘记密码手机验证码
     */
    public static final String URL_REGISTERVERIFY = HTTPS + "/account/open/verify/register";

    /**
     * 自驾游列表
     */
    public static final String URL_SELF_TRAVEL = HTTP + "/selfdriving/open/list";
    /**
     * 自驾游详情评论列表
     */
    public static final String URL_SELF_COMMENT = HTTP + "/selfdriving/open/comment/list";
    /**
     * 自驾游详情评论子评论列表
     */
    public static final String URL_SELF_SUBCLASS_COMMENT = HTTP + "/selfdriving/open/comment/reply/list";
    /**
     * 自驾游详情评论回复
     */
    public static final String URL_SELF_COMMENT_REPLY = HTTP + "/selfdriving/comment";
    /**
     * 自驾游详情评论子评论回复
     */
    public static final String URL_SELF_SUBCLASS_COMMENT_REPLY = HTTP + "/selfdriving/comment/reply";
    /**
     * 自驾游发布
     */
    public static final String URL_SELF_ISSUE = HTTP + "/selfdriving/release";
    /**
     * 自驾游报名
     */
    public  static  final String URL_SELF_ENROL = HTTP + "/selfdriving/enrol";
    /**
     * 自驾游取消报名
     */
    public static final String URL_SELF_REMOVE_ENROL = HTTP +"/selfdriving/remove/enrol";
    /**
     * 自驾游活动取消
     */
    public static final String URL_SELF_CANCEL = HTTP + "/selfdriving/cancel";
    /**
     * 自驾游详情
     */
    public static final String URL_SELF_DETAIL = HTTP+"/selfdriving/open/searchone";
    /**
     * 自驾游名单
     */
    public static final String URL_SELF_ROLL = HTTP +"/selfdriving/searchname";
    /**
     * 我的活动列表
     */
    public static final String URL_MY_ACTIVITY = HTTP + "/selfdriving/activity/list";

    /**
     * 四服务列表
     */
    public static final String URL_FOUR_SERVE = HTTP +"/open/service/servicetype/list";
    /**
     * 四服务详情
     */
    public static final String URL_FOUR_SERVE_DETAIL = HTTP + "/open/service/servicetype/detail";
    /**
     * 促销
     */
    public static final String URL_PROMOTION = HTTP + "/open/promotion/list";
    /**
     * 促销详情
     */
    public static final String URL_PROMOTION_DETAIL = HTTP+"/open/promotion/query";
    /**
     * 促销搜索
     */
    public static final String URL_PROMOTION_SEARCH = HTTP + "/open/promotion/search";
    /**
     * 咨询
     */
    public static final String URL_MESSAGE = HTTP + "/open/information/list";
    /**
     * 咨询详情
     */
    public static final String URL_MESSAGE_DETAIL = HTTP+"/open/information/query";
    /**
     * 救援详情
     */
    public static final String URL_RESCURE_DETAIL = HTTP +"/open/service/rescuetype/detail";
    /**
     * Home搜索
     */
    public static final String URL_SEARCH_HOME = HTTP + "/open/home/search";
    /**
     * 咨询搜索
     */
    public static final String URL_MESSAGE_SEARCH = HTTP + "/open/information/search";

    /**
     * 我的提问
     */
    public static final String URL_MY_QUESTION = HTTP + "/user/personal/question";


    /**
     * 我的文章
     */
    public static final String URL_MY_ARTICLE = HTTP + "/user/personal/article";

    /**
     * 意见反馈
     */
    public static final String URL_FEEDBACK = HTTP + "/feedback/advice";

    /**
     * 个人信息
     */
    public static final String URL_PERSONAL_INFO = HTTP + "/user/personal/info";

    /**
     * 修改个人信息
     */
    public static final String URL_PERSONAL_INFO_CHANGE = HTTPS + "/user/userinfo/change";


    /**
     * 救援列表
     */
    public static final String URL_RESCUE_LIST = HTTP + "/open/service/rescue/list";

    /**
     * 城市列表
     */
    public static final String URL_CITY_LIST = HTTP + "/open/home/city/list";


    /**
     * isHttp 控制接口数据
     * true:从网络访问数据
     * false：从本地加载仿真数据
     */
    public static final Boolean isHttp = true;


    /**
     * 自驾游列表
     *
     * @param map
     * @return
     */
    public static void SelfDriving(Map<String, String> map, ResponseCallbackHandler handler, int type,
                                   String tag) {
        if (isHttp) {
            ApiHttp.SelfDriving(map, handler, type, tag);
        } else {
            ApiLocal.SelfDriving(handler, type, tag);
        }
    }

    /**
     * 自驾游详情评论
     *
     * @param jsonObject
     * @return
     */
    public static void SelfComment(JSONObject jsonObject, ResponseJSONObjectListener handler, int type,
                                   String tag) {
        if (isHttp) {
            TLog.i(TAG, "------>" + "CarWash");
            ApiHttp.SelfComment(jsonObject, handler, type, tag);
        } else {
            // ApiLocal.SelfComment(handler, type, tag);
        }
    }

    /**
     * 自驾游详情评论子评论
     *
     * @param jsonObject
     * @return
     */
    public static void SelfSubComment(JSONObject jsonObject, ResponseJSONObjectListener handler, int type,
                                      String tag) {
        if (isHttp) {
            TLog.i(TAG, "------>" + "CarWash");
            ApiHttp.SelfSubComment(jsonObject, handler, type, tag);
        } else {
            // ApiLocal.SelfComment(handler, type, tag);
        }
    }

    /**
     * 自驾游详情评论回复
     */
    public static void SelfCommentReply(Map<String, String> map, JSONObject jsonObject, ResponseJSONObjectListener handler, int type,
                                        String tag) {
        if (isHttp) {
            TLog.i(TAG, "------>" + "CarWash");
            ApiHttp.SelfCommentReply(map, jsonObject, handler, type, tag);
        } else {
            // ApiLocal.SelfComment(handler, type, tag);
        }
    }

    /**
     * 自驾游详情评论子评论回复
     */
    public static void SelfSubCommentReply(Map<String, String> map, JSONObject jsonObject, ResponseJSONObjectListener handler, int type,
                                           String tag) {
        if (isHttp) {
            TLog.i(TAG, "------>" + "CarWash");
            ApiHttp.SelfSubCommentReply(map, jsonObject, handler, type, tag);
        } else {
            // ApiLocal.SelfComment(handler, type, tag);
        }
    }

    /**
     * 自驾游发布
     */
    public static void SelfIssue(String url, Map filemap, Map map, ResponseStringListener handler, int type,
                                 String tag) {
        if (isHttp) {
            ApiHttp.SelfIssue(url, filemap, map, handler, type, tag);
        } else {
            //  ApiLocal.Message(handler, type, tag);
        }
    }

    /**
     * 自驾游报名
     */
    public static void SelfEnrol(String url ,JSONObject jsonObject , ResponseJSONObjectListener handler, int type, String tag ){
        if (isHttp) {

            ApiHttp.SelfEnrol(url, jsonObject, handler, type, tag);
        } else {
            //  ApiLocal.Message(handler, type, tag);
        }
    }
    /**
     * 自驾游取消报名
     */
    public static void SelfRemoveEnrol(String url ,JSONObject jsonObject , ResponseJSONObjectListener handler, int type, String tag ){
        if (isHttp) {

            ApiHttp.SelfRemoveEnrol(url, jsonObject, handler, type, tag);
        } else {
            //  ApiLocal.Message(handler, type, tag);
        }
    }


    /**
     * 促销列表
     *
     * @param map
     * @return
     */
    public static void Promotion(String url,JSONObject map, ResponseJSONObjectListener handler, int type,
                                 String tag) {
        if (isHttp) {
            ApiHttp.Promotion( url,map, handler, type, tag);
        } else {
            //  ApiLocal.Promotion(handler, type, tag);
        }
    }

    /**
     * 促销搜索列表
     *
     * @param map
     * @return
     */
    public static void PromotionSearch(Map map, Map filemap,ResponseStringListener handler, int type,
                                       String tag) {
        if (isHttp) {
            ApiHttp.PromotionSearch(map, filemap,handler, type, tag);
        } else {
            //  ApiLocal.Promotion(handler, type, tag);
        }
    }

    /**
     * 咨询列表
     *
     * @param map
     * @return
     */
    public static void Message(JSONObject map, ResponseJSONObjectListener handler, int type,
                               String tag) {
        if (isHttp) {
            ApiHttp.Message(map, handler, type, tag);
        } else {
            //  ApiLocal.Message(handler, type, tag);
        }
    }
    /**
     * 咨询搜索列表
     *
     * @param map
     * @return
     */
    public static void MessageSearch(Map map, Map filemap,ResponseStringListener handler, int type,
                                     String tag) {
        if (isHttp) {
            ApiHttp.MessageSearch(map, filemap,handler, type, tag);
        } else {
            //  ApiLocal.Message(handler, type, tag);
        }
    }

    /**
     * 广告条
     */
    public static void HomeAd(JSONObject map, ResponseJSONObjectListener handler, int type, String tag) {
        if (isHttp) {
            ApiHttp.HomeAd(map, handler, type, tag);
        } else {
           // ApiLocal.HomeAd(handler, type, tag);
        }
    }

    /**
     * 退去登录
     */
    public static void Logout(JSONObject map, ResponseJSONObjectListener handler, int type, String tag) {
        if (isHttp) {
            ApiHttp.Logout(map, handler, type, tag);
        } else {
            //   ApiLocal.Logout(handler,type,tag);
        }
    }

    /**
     * 添加收藏
     */
    public static void FavorAdd(String token, JSONObject map, ResponseJSONObjectListener handler, int type, String tag) {
        if (isHttp) {
            ApiHttp.FavorAdd(token, map, handler, type, tag);
        } else {
            //   ApiLocal.Logout(handler,type,tag);
        }
    }

    /**
     * 删除收藏
     */
    public static void FavorDel(String token, JSONObject map, ResponseJSONObjectListener handler, int type, String tag) {
        if (isHttp) {
            ApiHttp.FavorDel(token, map, handler, type, tag);
        } else {
            //   ApiLocal.Logout(handler,type,tag);
        }
    }
}
