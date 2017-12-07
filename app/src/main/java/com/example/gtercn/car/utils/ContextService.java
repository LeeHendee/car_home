package com.example.gtercn.car.utils;

import android.content.Context;

import java.io.Serializable;

/**
 * Context class
 * Created by Administrator on 2016-5-18.
 */
public class ContextService implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Context mContext;

	private static ContextService service = new ContextService();
	private ContextService(){ }
	
	public static ContextService getInstance(){
		return service;
	}
	
	public void setContext(Context context){
		this.mContext = context;
	}
	
	public Context getContext(){
		return this.mContext;
	}
	
}
