package com.example.gtercn.car.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bigkoo.pickerview.TimePickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/3/20.
 */

public class GetTimeData {
    private Context context;
    private EditText v;

    private String time;
    private TimePickerView pvTime;
    private boolean is;

    public GetTimeData(Context context , EditText v){
        this.context = context;
        this.v=v;
        is=false;
        getData();
    }
    public GetTimeData(Context context , EditText v,String time){
        this.context = context;
        this.time=time;
        this.v=v;
        is=true;
        getData();
    }
    /**
     * 获取时间
     */
    private void getData() {

        pvTime = new TimePickerView( context, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime = new TimePickerView(context, TimePickerView.Type.YEAR_MONTH_DAY);

        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                if(is) {
                    if (time != null) {
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                        Calendar c1 = Calendar.getInstance();
                        Calendar c2 = Calendar.getInstance();
                        TLog.i("SelfDrivingIssueActivity","-----------date------------>"+date);
                        try {
                            c1.setTime(df.parse(time));
                            c2.setTime(date);
                            int result = c1.compareTo(c2);
                            if (result == 0) {
                                v.setError(null);
                                v.setText(getTime(date));
                            } else if (result < 0) {
                                TLog.e("getData","c1 < c2");
                                v.setError(null);
                                v.setText(getTime(date));
                            } else {
                                v.requestFocus();
                                v.setError("时间选择有误");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }else{
                    TLog.e("getData","not time");
                    v.setError(null);
                    v.setText(getTime(date));
                    is=false;
                }
            }
        });
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager im = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                pvTime.show();
                return true;
            }

//            @Override
//            public void onClick(View v) {
//                //隐藏软键盘
//                InputMethodManager im = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                pvTime.show();
//            }
        });

    }

    /**
     * 获取的时间形式
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        return format.format(date);
    }
}
