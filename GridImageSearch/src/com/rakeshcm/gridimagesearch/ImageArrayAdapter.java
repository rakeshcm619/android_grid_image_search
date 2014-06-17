package com.rakeshcm.gridimagesearch;

import java.util.ArrayList;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ImageArrayAdapter extends ArrayAdapter<ImageResult> {
	public ImageArrayAdapter(Context context, ArrayList<ImageResult> images) {
	    super(context, R.layout.item_image_result, images);
	}
	
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	    // Get the data item for this position
		ImageResult imageInfo = this.getItem(position);    
	    SmartImageView ivImage;
	    if (convertView == null) {
	       LayoutInflater inflater = LayoutInflater.from(getContext());
	       ivImage = (SmartImageView) inflater.inflate(R.layout.item_image_result, parent, false);
	    }
	    else {
	    	ivImage = (SmartImageView) convertView;
	    	ivImage.setImageResource(R.color.transparent);
	    }
	    
	    ivImage.setImageUrl(imageInfo.getThumbUrl());
	    // Return the completed view to render on screen
	    return ivImage;
	}
}
