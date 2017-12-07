package com.example.gtercn.car.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.example.gtercn.car.widget.recyclerview.SwipeMenuLayout;
import com.example.gtercn.car.widget.recyclerview.SwipeMenuView;

public class SwapWrapperUtils {

    public static SwipeMenuLayout wrap(ViewGroup parent, int layoutId, SwipeMenuView menuView, Interpolator closeInterpolator, Interpolator openInterpolator) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        SwipeMenuLayout swipeMenuLayout = new SwipeMenuLayout(contentView, menuView, closeInterpolator, openInterpolator);
        return swipeMenuLayout;
    }
}
