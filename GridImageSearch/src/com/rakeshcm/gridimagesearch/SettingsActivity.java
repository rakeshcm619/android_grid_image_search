package com.rakeshcm.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {

	Spinner spColor;
	Spinner spSize;
	Spinner spType;
	EditText etSite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		init();
	}
	
	private void init() {
		etSite = (EditText) findViewById(R.id.etSite);
		setColorSpinner();
		setSizeSpinner();
		setTypeSpinner();
		
		String settingsSize = getIntent().getStringExtra("size");
		String settingsColor = getIntent().getStringExtra("color");
		String settingsType = getIntent().getStringExtra("type");
		String settingsSite = getIntent().getStringExtra("site");
		
		int sizeIndex = getIndex(spSize, settingsSize);
		int colorIndex = getIndex(spColor, settingsColor);
		int typeIndex = getIndex(spType, settingsType);
		spSize.setSelection(sizeIndex);
		spColor.setSelection(colorIndex);
		spType.setSelection(typeIndex);
		etSite.setText(settingsSite);
	}
	
	private void setColorSpinner() {
		spColor = (Spinner) findViewById(R.id.spColor);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.image_color, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spColor.setAdapter(adapter);
	}
	
	private void setSizeSpinner() {
		spSize = (Spinner) findViewById(R.id.spSize);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.image_size, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spSize.setAdapter(adapter);
	}
	
	private void setTypeSpinner() {
		spType = (Spinner) findViewById(R.id.spType);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.image_type, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spType.setAdapter(adapter);
	}
	
	public void onSave (View v) {
		String imgColor = spColor.getSelectedItem().toString();
		String imgType = spType.getSelectedItem().toString();
		String imgSize = spSize.getSelectedItem().toString();
		String imgSite = etSite.getText().toString();
		
		// TODO: will be refactored to Serializable object later
		Intent i = new Intent();
		i.putExtra("size", imgSize);
		i.putExtra("color", imgColor);
		i.putExtra("type", imgType);
		i.putExtra("site", imgSite);
		setResult(RESULT_OK, i);
		finish();
	}
	
	public void onCancel (View v) {
		setResult(RESULT_CANCELED);
		finish();
	}
	
	private int getIndex(Spinner spinner, String myString) {
		int index = 0;
		for (int i=0;i<spinner.getCount();i++){
			if (spinner.getItemAtPosition(i).equals(myString)){
				index = i;
			}
		}
		return index;
	}
}
