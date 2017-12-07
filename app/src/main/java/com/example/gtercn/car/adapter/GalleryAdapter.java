package com.example.gtercn.car.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {
	private static final String TAG = GalleryAdapter.class.getSimpleName();

	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> mList;
	private int width;
	private int height;
	
	public GalleryAdapter(Context context, List<String> list, int width , int height){
		this.mContext = context;
		this.mList = list;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public int getCount() {
		if(mList != null)
			return mList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		if(mList != null){
			return mList.get(position);
		}else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holder;
		if(convertView == null){
			 holder = new HolderView();
		 	View view = mInflater.inflate(R.layout.album_item, null);
		 	Gallery.LayoutParams lp = new Gallery.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT);
		 	view.setLayoutParams(lp);
		 	holder.image = (ImageView) view.findViewById(R.id.album_item_img);
			holder.page = (TextView) view.findViewById(R.id.album_item_page);
		 	convertView = view;
		 	convertView.setTag(holder);
		}else{
			holder = (HolderView) convertView.getTag();
		}
		
		String url = (String) getItem(position);
		holder.image.setImageResource(R.drawable.icon_default);
		holder.page.setText((position + 1) + "/" + getCount());
		if(!TextUtils.isEmpty(url)){
			if(url.contains("\\")){
				url = TAppUtils.formatUrl(url);
			}
			THttpOpenHelper.newInstance().setImageBitmap(holder.image, url, width, height,
					R.drawable.icon_default,
					R.drawable.icon_default);
		}
		return convertView;
	}
	
	final class HolderView{
		ImageView image;
		TextView page;
	}

}
