package com.example.gtercn.car.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class AlbumGallery extends Gallery {
	
	public AlbumGallery(Context context) {
		super(context);
	}

	public AlbumGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AlbumGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
	  {
	    return false;
	  }

	public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
	  {
	    return super.onScroll(paramMotionEvent1, paramMotionEvent2, paramFloat1, paramFloat2);
	  }

	public void setSelection(int paramInt)
	  {
	    super.setSelection(paramInt);
	  }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

}
