package com.example.gtercn.car.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @date 2016-5-18
 * 正则表达式操作类
 */
public class TRegularExpression {

    /**
     * 判断数字是否为移动电话号码 11位长，国内电话号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile(
                "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0,6,7,8])|(14[5,7]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断字符串是否为电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断是否为身份证号的正则表达式
     *
     * @param text
     * @return
     */
    public static boolean isPersonId(String text) {
        String regx = "[0-9]{17}[xX]";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /**
     * 档案编号
     * @param id
     * @return
     */
    public static boolean isPaperId(String id) {
        String str = "^[A-Z]\\d+$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(id);
        return m.matches();
    }
    /**
     * 判断所传字符串是否全部为汉字的正则表达式
     *
     * @param text
     * @return
     */
    public static boolean isChineseFonts(String text) {

        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher matcher = p.matcher(text);
        //matcher.find()返回的是是否含有中文;
        return matcher.matches();//true为全部是汉字，否则是false
    }
}
