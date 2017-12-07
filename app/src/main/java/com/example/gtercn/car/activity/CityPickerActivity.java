package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.example.gtercn.car.R;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.CityListBean;
import com.example.gtercn.car.adapter.CityListAdapter;
import com.example.gtercn.car.adapter.ResultListAdapter;
import com.example.gtercn.car.location.CityCodeChangeImpl;
import com.example.gtercn.car.utils.LocateState;
import com.example.gtercn.car.widget.SideLetterBar;
import com.example.gtercn.car.location.AppLocation;
import com.example.gtercn.car.utils.SharedPreferenceHelper;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CityPickerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_PICK_CITY = 2333;
    public static final String KEY_PICKED_CITY = "picked_city";

    private static final String TAG = "CityPickerActivity";

    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private TextView backBtn;
    private ViewGroup emptyView;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<CityListBean.ResultBean> mAllCities;

    private CarApplication mApplication;

    private List<CityListBean.ResultBean> mCityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        mApplication = (CarApplication) getApplication();
        initData();
        initView();
        initLocation();
    }

    private void initData() {
        Intent intent = getIntent();
        mCityList = new ArrayList<>();
        if (intent != null) {
            mCityList = (List<CityListBean.ResultBean>) intent.getSerializableExtra("city_list");
        }
        mCityAdapter = new CityListAdapter(this, mCityList);

        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name, String cityCode) {
                back(name, cityCode);
            }

            @Override
            public void onLocateClick() {
                Log.e("onLocateClick", "重新定位...");
                initLocation();
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
            }
        });
        mResultAdapter = new ResultListAdapter(this, null);
    }

    private void initLocation() {
        BDLocation bdLocation = AppLocation.newInstance().getBDLocation();
        if (bdLocation != null) {
            String city = bdLocation.getCity();
            mCityAdapter.updateLocateState(LocateState.SUCCESS, city);
        } else {
            mCityAdapter.updateLocateState(LocateState.FAILED, "定位失败");
        }
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);
        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox = (EditText) findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                String firstAlpha = getFirstSpell(keyword);
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
//                    mResultListView.setVisibility(View.VISIBLE);
                    String letter =firstAlpha.toUpperCase();
                    int position = mCityAdapter.getLetterPosition(letter);
                    mListView.setSelection(position);

                }
            }
        });

        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityCode = mResultAdapter.getItem(position).getCity_code();
                String city = mResultAdapter.getItem(position).getCity_name();
                back(city, cityCode);
            }
        });
        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        backBtn = (TextView) findViewById(R.id.back);
        clearBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    private void back(String city, String cityCode) {
        if (!TextUtils.isEmpty(cityCode)) {
            for (CityListBean.ResultBean bean : mCityList) {
                if (TextUtils.equals(bean.getCity_code(),cityCode)) {
                    SharedPreferenceHelper.setValue(ApiManager.CITY_CODE, bean.getCity_code());
                    CityCodeChangeImpl.newInstance().notifyChange();
                    break;
                }
            }

            Intent goHome = new Intent(this, MainActivity.class);
            goHome.putExtra("city", city);
            startActivity(goHome);
        }
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_clear:
                searchBox.setText("");
                clearBtn.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                mResultListView.setVisibility(View.GONE);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取汉字串拼音首字母，英文字符不变
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String getFirstSpell(String chinese) {
        StringBuilder pybf = new StringBuilder();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                        break;
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(c);
                break;
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

}
