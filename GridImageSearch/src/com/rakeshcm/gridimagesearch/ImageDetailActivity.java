package com.rakeshcm.gridimagesearch;

import com.loopj.android.image.SmartImageView;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ImageDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_detail);
		ActionBar bar = getActionBar();
		bar.hide();
		
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
		SmartImageView ivFullImage = (SmartImageView) findViewById(R.id.ivFullImage);
		ivFullImage.setImageUrl(result.getFullUrl());
	}
	
	public void onCloseImage(View v) {
		finish();
	}
}
