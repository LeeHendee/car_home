package com.example.gtercn.car.utils;

import com.example.gtercn.car.bean.CarWashBean;
import com.example.gtercn.car.bean.FourServiceBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/6.
 */
//冒泡排序
public class SortUtil {
    private CarWashBean.ResultBean[] ids;
    private ArrayList<CarWashBean.ResultBean> arrayList;

    //由高到低
    public static ArrayList<FourServiceBean> HighToLow(ArrayList<FourServiceBean> arrayList) {
        for (int i = 0; i < arrayList.size() - 1; i++) {
            //内层控制比较的个数
            for (int j = 0; j < arrayList.size() - 1 - i; j++) {
                String score1 = arrayList.get(j).getScore();
                String score2 = arrayList.get(j + 1).getScore();
                double d1;
                double d2;
                if (score1.equals("")==false){
                    d1= Double.valueOf(score1);
                }else {
                    d1=Double.valueOf("1");
                }
                if(score2.equals("")==false){
                    d2 = Double.valueOf(score2);
                }else {
                    d2 =Double.valueOf("1");
                }
                //进行相邻的两个数的比较
                if (d1 < d2) {
                    FourServiceBean resultBean = arrayList.get(j);
                    arrayList.set(j, arrayList.get(j + 1));
                    arrayList.set((j + 1), resultBean);
                }
            }
        }
        return arrayList;
    }

    //由低到高
    public static ArrayList<FourServiceBean> LowToHigh(ArrayList<FourServiceBean> arrayList) {
        for (int i = 0; i < arrayList.size() - 1; i++) {
            //内层控制比较的个数
            for (int j = 0; j < arrayList.size() - 1 - i; j++) {
                String score1 = arrayList.get(j).getScore();
                String score2 = arrayList.get(j + 1).getScore();
                double d1;
                double d2;
                if (score1.equals("")==false){
                    d1= Double.valueOf(score1);
                }else {
                    d1=Double.valueOf("1");
                }
                if(score2.equals("")==false){
                    d2 = Double.valueOf(score2);
                }else {
                    d2 =Double.valueOf("1");
                }
                //进行相邻的两个数的比较
                if (d1 > d2) {
                    FourServiceBean resultBean = arrayList.get(j);
                    arrayList.set(j, arrayList.get(j + 1));
                    arrayList.set((j + 1), resultBean);
                }
            }
        }
        return arrayList;
    }

    //距离由近到远
    public static ArrayList<FourServiceBean> LowToHighDistance(ArrayList<FourServiceBean> arrayList) {
        for (int i = 0; i < arrayList.size() - 1; i++) {
            //内层控制比较的个数
            for (int j = 0; j < arrayList.size() - 1 - i; j++) {
                //进行相邻的两个数的比较
                if (arrayList.get(j).getDistance() > arrayList.get(j + 1).getDistance()) {
                    FourServiceBean resultBean = arrayList.get(j);
                    arrayList.set(j, arrayList.get(j + 1));
                    arrayList.set((j + 1), resultBean);
                }
            }
        }
        return arrayList;
    }
}