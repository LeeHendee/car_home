package com.example.gtercn.car.utils;

/**
 * Created by Administrator on 2016/11/15.
 */

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by Administrator on 2016/8/8.
 */
public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String title) {
        this.title = title;

    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}

