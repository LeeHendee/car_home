package com.example.gtercn.car.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.bean.CityListBean;
import com.example.gtercn.car.utils.LocateState;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author zaaach on 2016/1/26.
 */
public class CityListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 2;

    private static final String TAG = "CityListAdapter";

    private Context mContext;
    private LayoutInflater inflater;
    private List<CityListBean.ResultBean> mCities;
    private HashMap<String, Integer> letterIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity = "阜新市";
    private String locatedCityCode = "210900";

    public CityListAdapter(Context mContext, List<CityListBean.ResultBean> mCities) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.inflater = LayoutInflater.from(mContext);
        if (mCities == null) {
            mCities = new ArrayList<>();
        }
        CityListBean.ResultBean bean = new CityListBean.ResultBean();
        bean.setCity_name(locatedCity);
        bean.setCity_code(locatedCityCode);
        mCities.add(0, bean);
//        mCities.add(1, new CityListBean.ResultBean("热门", "1"));

        int size = mCities.size();
        TLog.i(TAG, "----->>city.size is " + size);
        letterIndexes = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index < size; index++) {
            //当前城市拼音首字母
            String currentLetter = TAppUtils.getFirstLetter(mCities.get(index).getCity_phoneticize());
            TLog.i(TAG, "----->>currentLetter is " + currentLetter);
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? TAppUtils.getFirstLetter(mCities.get(index - 1).getCity_phoneticize()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }

    /**
     * 更新定位状态
     * @param state
     */
    public void updateLocateState(int state, String city) {
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public CityListBean.ResultBean getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        TLog.i(TAG, "----->>getView cities is " + mCities.toString());
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:     //定位
                TLog.i(TAG, "---->>getView case = 0");
                view = inflater.inflate(R.layout.view_locate_city, parent, false);
                ViewGroup container = (ViewGroup) view.findViewById(R.id.layout_locate);
                TextView state = (TextView) view.findViewById(R.id.tv_located_city);
                switch (locateState) {
                    case LocateState.LOCATING:
                        state.setText(mContext.getString(R.string.locating));
                        break;
                    case LocateState.FAILED:
                        state.setText(R.string.located_failed);
                        break;
                    case LocateState.SUCCESS:
                        state.setText(locatedCity);
                        break;
                }
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (locateState == LocateState.FAILED) {
                            //重新定位
                            if (onCityClickListener != null) {
                                onCityClickListener.onLocateClick();
                            }
                        } else if (locateState == LocateState.SUCCESS) {
                            //返回定位城市
                            if (onCityClickListener != null) {
                                String cityCode = getCityCodeByName(locatedCity);
                                onCityClickListener.onCityClick(locatedCity, cityCode);
                            }
                        }
                    }
                });
                break;

            case 1:     //所有
                TLog.i(TAG, "---->>getView case = 2");
                if (view == null) {
                    view = inflater.inflate(R.layout.item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
                    view.setTag(holder);
                } else {
                    holder = (CityViewHolder) view.getTag();
                }
                if (position >= 0) {
                    final String city = mCities.get(position).getCity_name();
                    final String cityCode = mCities.get(position).getCity_code();
                    TLog.i(TAG, "---->>city name is  = " + city);
                    holder.name.setText(city);
                    String currentLetter = TAppUtils.getFirstLetter(mCities.get(position).getCity_phoneticize());
                    String previousLetter = position >= 1 ? TAppUtils.getFirstLetter(mCities.get(position - 1).getCity_phoneticize()) : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)) {
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter);
                    } else {
                        holder.letter.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onCityClickListener != null) {
                                onCityClickListener.onCityClick(city, cityCode);
                            }
                        }
                    });
                }
                break;
        }
        return view;
    }

    public String getCityCodeByName(String name) {
        String cityCode = null;
        for (CityListBean.ResultBean bean : mCities) {
            if (bean.getCity_name().contains(name)) {
                cityCode = bean.getCity_code();
                break;
            }
        }
        if (!TextUtils.isEmpty(cityCode)) {
            return cityCode;
        } else
            return SharedPreferenceHelper.getValue(ApiManager.CITY_CODE);
    }

    public static class CityViewHolder {
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListener(OnCityClickListener listener) {
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener {
        void onCityClick(String name, String cityCode);

        void onLocateClick();
    }
}
