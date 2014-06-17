package com.rakeshcm.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText etSearchBar;
	private GridView gvGridImages;
	
	private int SETTINGS_REQUEST_CODE = 30;
	private int PAGE_COUNT = 8;
	private String settingsSize = "";
	private String settingsType = "";
	private String settingsColor = "";
	private String settingsSite = "";
	
	private boolean isFetchComplete = false;
	
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageArrayAdapter imageAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		imageAdapter = new ImageArrayAdapter(this, imageResults);
		gvGridImages.setAdapter(imageAdapter);
		gvGridImages.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,
					int position, long id) {
				// TODO Auto-generated method stub
				ImageResult imgResult = imageResults.get(position);
				
				Intent i = new Intent(getApplicationContext(), ImageDetailActivity.class);
				i.putExtra("result", imgResult);
				startActivity(i);
			}
		});
		
		gvGridImages.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if((firstVisibleItem + visibleItemCount + PAGE_COUNT > totalItemCount) 
						&& isFetchComplete && !(etSearchBar.getText().toString().equalsIgnoreCase(""))) {
					apiSearchImages(true, firstVisibleItem+1);
				}
			}
		});
	}
	
	
	private void init() {
		etSearchBar = (EditText) findViewById(R.id.etSearchBar);
		gvGridImages = (GridView) findViewById(R.id.gvGridImages);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }
	
	public void onImageSearch(View v) {
		apiSearchImages(false, 0);
	}
	
	
	private void apiSearchImages(final boolean isCalledDueTOScroll, int start) {
		String query = etSearchBar.getText().toString();
		if(!isCalledDueTOScroll) {
			Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
		}
		else {
			//Toast.makeText(getBaseContext(), "Fetching next " + PAGE_COUNT + " results", Toast.LENGTH_SHORT).show();
		}
		
		AsyncHttpClient client = new AsyncHttpClient();
		isFetchComplete = false;
		client.get("https://ajax.googleapis.com/ajax/services/search/images?"
				+ "rsz=" + PAGE_COUNT 
				+ "&start=" + start 
				+ "&imgcolor=" + settingsColor 
				+ "&imgtype=" + settingsType
				+ "&imgsz=" + settingsSize
				+ "&as_sitesearch=" + settingsSite
				+ "&v=1.0"
				+ "&q=" + Uri.encode(query), 
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						// TODO Auto-generated method stub
						//super.onSuccess(response);
						JSONArray imageJsonResults = null;
						try {
							imageJsonResults = response.getJSONObject(
									"responseData").getJSONArray("results");
							if(!isCalledDueTOScroll) {
								imageResults.clear();
							}
							imageResults.addAll(ImageResult.fromJSONArray(imageJsonResults));
							imageAdapter.notifyDataSetChanged();
							isFetchComplete = true;
							//Log.d("DEBUG", imageResults.toString());
						}
						catch (JSONException ex) {
							ex.printStackTrace();
						}
					}
				});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK) {
			if (requestCode == SETTINGS_REQUEST_CODE) {
				settingsSize = data.getStringExtra("size");
				settingsColor = data.getStringExtra("color");
				settingsType = data.getStringExtra("type");
				settingsSite = data.getStringExtra("site");
				Toast.makeText(this, "Settings Saved!", Toast.LENGTH_SHORT).show();
				onImageSearch(new View(getBaseContext()));
			}
		}
	}
	
	public void onSettings(MenuItem mi) {
		Toast.makeText(getBaseContext(), "Change settings.", Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(this, SettingsActivity.class);
		// TODO: will be refactored to Serializable object later
		i.putExtra("size", settingsSize);
		i.putExtra("color", settingsColor);
		i.putExtra("type", settingsType);
		i.putExtra("site", settingsSite);
		startActivityForResult(i, SETTINGS_REQUEST_CODE);
	}
}
