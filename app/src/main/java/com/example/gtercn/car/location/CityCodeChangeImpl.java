package com.example.gtercn.car.location;

import com.example.gtercn.car.interfaces.ICityCodeChange;
import com.example.gtercn.car.interfaces.ICityCodeChangeListener;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Administrator on 2017-3-3.
 * 定位观测器
 */
public class CityCodeChangeImpl implements ICityCodeChange {
    private static final String TAG = CityCodeChangeImpl.class.getSimpleName();

    private Vector<ICityCodeChangeListener> vector = new Vector<>();

    private static CityCodeChangeImpl instance = null;

    private CityCodeChangeImpl() {
    }

    public synchronized static CityCodeChangeImpl newInstance() {
        if (instance == null) {
            instance = new CityCodeChangeImpl();
        }
        return instance;
    }

    @Override
    public void attchCityCode(ICityCodeChangeListener listener) {
        vector.add(listener);
    }

    @Override
    public void detachCityCode(ICityCodeChangeListener listener) {
        vector.remove(listener);
    }

    @Override
    public void notifyChange() {
        if (vector != null) {
            Iterator<ICityCodeChangeListener> iterator = vector.iterator();
            while (iterator.hasNext()) {
                iterator.next().updateCityCode();
            }
        }
    }
}
