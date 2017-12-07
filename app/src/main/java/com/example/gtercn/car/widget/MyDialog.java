package com.example.gtercn.car.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.MyDialogList;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class MyDialog extends Dialog implements android.view.View.OnClickListener {


    private Context context;
    private TextView txt;
    private Button phone1button;
    private Button phone2button;
    private Button clearbutton;
    private List<String> mPhoneList;
    private LeaveMyDialogListener listener;
    private float density = 1.5f;
    private ListView mListView;
    private MyDialog myDialog;
    public interface LeaveMyDialogListener {
        void onClick(View view);
    }

    public MyDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public MyDialog(Context context, int theme, LeaveMyDialogListener listener, List<String> mPhoneList,MyDialog myDialog) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.listener = listener;
        this.mPhoneList = mPhoneList;
        this.myDialog=myDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mydialog);
        mListView = (ListView) findViewById(R.id.mydialog_listview);
        MyDialogList myDialogList = new MyDialogList(context, mPhoneList,this);
        mListView.setAdapter(myDialogList);
        clearbutton = (Button) findViewById(R.id.mydialog_clear);
        clearbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        listener.onClick(v);
    }


}