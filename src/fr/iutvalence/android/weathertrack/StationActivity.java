package fr.iutvalence.android.weathertrack;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Application's station tracking related activity.<br/>
 * This activity retrieves station data from the server (as a JSON array) and
 * displays them as a list.<br/>
 * 
 * @author sebastienjean
 * 
 */
public class StationActivity extends Activity implements OnClickListener
{
	/**
	 * The tracked station.
	 */
	private Station station;
	
	/**
	 * Station data.
	 */
	private ArrayList<StationData> data;
	
	/**
	 * List adapter used to display station data.
	 */
	private StationDetailsAdapter stationDetailsAdapter;
	
	/**
	 * Progress dialog shown while retrieving data.
	 */
	private ProgressDialog progressDialog;

	/**
	 * Returns the string representation of a given data type.
	 * @param dataType the data type
	 * @return the string representation of <tt>dataType</tt>
	 */
	private final String dataTypeToString(DataTypeEnum dataType)
	{
		// DATE, TEMP1, TEMP2, PRESSURE, WIND_SPEED, WIND_DIR, HYGRO, LUX
		if (dataType == DataTypeEnum.DATE)
			return getResources().getString(R.string.data_type_date);
		if (dataType == DataTypeEnum.TEMP1)
			return getResources().getString(R.string.data_type_temp1);
		if (dataType == DataTypeEnum.TEMP2)
			return getResources().getString(R.string.data_type_temp2);
		if (dataType == DataTypeEnum.PRESSURE)
			return getResources().getString(R.string.data_type_pressure);
		if (dataType == DataTypeEnum.HYGRO)
			return getResources().getString(R.string.data_type_hygro);
		if (dataType == DataTypeEnum.LUX)
			return getResources().getString(R.string.data_type_lux);
		if (dataType == DataTypeEnum.WIND_DIR)
			return getResources().getString(R.string.data_type_windir);
		if (dataType == DataTypeEnum.WIND_SPEED)
			return getResources().getString(R.string.data_type_winspeed);
		return null;
	}

	/**
	 * Returns the string representation of the unit associated to a given data type.
	 * @param dataType the data type
	 * @return the string representation of the unit associated to <tt>dataType</tt>
	 */
	private final String dataTypeToUnitString(DataTypeEnum dataType)
	{
		// DATE, TEMP1, TEMP2, PRESSURE, WIND_SPEED, WIND_DIR, HYGRO, LUX;
		if (dataType == DataTypeEnum.DATE)
			return "";
		if (dataType == DataTypeEnum.TEMP1)
			return "°C";
		if (dataType == DataTypeEnum.TEMP2)
			return "°C";
		if (dataType == DataTypeEnum.PRESSURE)
			return "hPa";
		if (dataType == DataTypeEnum.HYGRO)
			return "%";
		if (dataType == DataTypeEnum.LUX)
			return "lux";
		if (dataType == DataTypeEnum.WIND_DIR)
			return "m/s";
		if (dataType == DataTypeEnum.WIND_SPEED)
			return "°";
		return null;
	}

	/**
	 * Internal method used to change the favorite status (selected or not), inducing changing
	 * associated icon and its "clickability".
	 * 
	 * @param favorite the favorite status (set as favorite or not)
	 */
	private final void setFavorite(boolean favorite)
	{
		ImageView favoriteIconView = (ImageView) findViewById(R.id.StationActivityImageViewFavorite);
		
		Drawable favoriteIconDrawable = null;
		
		if (favorite)
			favoriteIconDrawable= getResources().getDrawable(R.drawable.ic_favorite_station_checked);
		else 
			favoriteIconDrawable= getResources().getDrawable(R.drawable.ic_favorite_station_unchecked);
		
		favoriteIconView.setImageDrawable(favoriteIconDrawable);
		favoriteIconView.setEnabled(!favorite);
		favoriteIconView.invalidate();
		
	}

	// TODO finish writing comment
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station);

		this.station = ((MainApplication) this.getApplication()).getCurrentStation();
		((TextView) this.findViewById(R.id.StationActivityTestViewStationID)).setText(this.station.getID());
		
		ImageView favoriteIconView = (ImageView) findViewById(R.id.StationActivityImageViewFavorite);
		
		Station favorite = ((MainApplication) this.getApplication()).getFavoriteStation();
	
		this.setFavorite((favorite != null) && (this.station.equals(favorite)));
		
		favoriteIconView.setOnClickListener(this);
		this.data = new ArrayList<StationData>();
		this.stationDetailsAdapter = new StationDetailsAdapter(this);
		ListView listView = (ListView) findViewById(R.id.StationActivityListViewStationDetails);
		listView.setAdapter(this.stationDetailsAdapter);
		this.progressDialog = ProgressDialog.show(this, getResources().getString(R.string.download_progress_dialog_title),
				getResources().getString(R.string.download_progress_dialog_message), true);

		new Thread(null, new Runnable()
		{
			@SuppressWarnings("synthetic-access")
			public void run()
			{
				StationActivity.this.getStationDetails();
			}
		}, "stationDataRetriever").start();
	}

	// TODO finish writing comment
	/**
	 * 
	 */
	private void getStationDetails()
	{
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		this.data.clear();
		
		this.data.add(new StationData(DataTypeEnum.TEMP1, 23.5f));
		this.data.add(new StationData(DataTypeEnum.PRESSURE, 1015));
		this.data.add(new StationData(DataTypeEnum.HYGRO, 64));

		runOnUiThread(new Runnable()
		{
			@SuppressWarnings("synthetic-access")
			public void run()
			{
				StationActivity.this.progressDialog.dismiss();
				StationActivity.this.stationDetailsAdapter.notifyDataSetChanged();
			}
		});
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_station, menu);
//		return true;
//	}

	// TODO finish writing comment
	/**
	 * @author sebastienjean
	 *
	 */
	private class StationDetailsAdapter extends AbstractObjectListAdapter<StationData>
	{
		@SuppressWarnings("synthetic-access")
		public StationDetailsAdapter(Context context)
		{
			super(context, R.layout.station_data_row, StationActivity.this.data);
		}

		@SuppressWarnings("synthetic-access")
		protected View displayObject(int position, View view, ViewGroup parent)
		{			
			StationData stationData =   StationActivity.this.data.get(position);
			TextView type = (TextView) view.findViewById(R.id.StationDataTextViewDataName);
			TextView value = (TextView) view.findViewById(R.id.StationDataTextViewDataValue);
		
			if (stationData != null)
			{
				type.setText(StationActivity.this.dataTypeToString(stationData.getType()));
				value.setText(stationData.getValue().toString() + dataTypeToUnitString(stationData.getType()));
			}
			else
			{
				type.setText(getString(R.string.no_title));
				value.setText(getString(R.string.no_content));
			}
			return view;
		}
	}

	@Override
	public void onClick(View v)
	{
		((MainApplication) this.getApplication()).setFavoriteStation(this.station);
		this.setFavorite(true);
	}
}
