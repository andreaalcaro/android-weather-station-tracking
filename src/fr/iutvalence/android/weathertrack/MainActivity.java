package fr.iutvalence.android.weathertrack;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Application's main activity.<br/>
 * This activity retrieves stations list from the server (as a JSON array) and
 * displays them as a list where the user can further pick a station to track.<br/>
 * If selected, the user can directly track its favorite station using a
 * shortcut.
 * 
 * @author sebastienjean
 * 
 */
public class MainActivity extends Activity implements OnItemClickListener
{
	/**
	 * Application's globals reference.
	 */
	private MainApplication mainApplication;

	/**
	 * List adapter used to display the favorite station.
	 */
	private StationInfoAdapter favoriteStationAdapter;

	/**
	 * List adapter used to display the list of known stations.
	 */
	private StationInfoAdapter stationListAdapter;

	/**
	 * Progress dialog shown while retrieving data
	 */
	private ProgressDialog progressDialog;

	/**
	 * <i>On resuming, refreshes favorite station information (that could have
	 * changed)</i>.
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume()
	{
		super.onResume();

		// Refreshing the favorite station view
		this.favoriteStationAdapter = new StationInfoAdapter(this, R.layout.station_info_row,
				Collections.singletonList(this.mainApplication.getFavoriteStation()));
		ListView favoriteStationView = ((ListView) findViewById(R.id.MainActivityListViewFavoriteStation));
		favoriteStationView.setAdapter(this.favoriteStationAdapter);
		favoriteStationView.setOnItemClickListener(this);
		favoriteStationView.invalidate();
	}

	/**
	 * <i>On creation, requests server to get the list of known station and
	 * displays them, as well as the favorite station (if not <tt>null</tt>
	 * ).</i>
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		this.mainApplication = (MainApplication) this.getApplication();

		// Refreshing the list of known stations view
		this.stationListAdapter = new StationInfoAdapter(this, R.layout.station_info_row,
				this.mainApplication.getStations());
		ListView stationsListView = (ListView) findViewById(R.id.MainActivityListViewStations);
		stationsListView.setAdapter(this.stationListAdapter);
		stationsListView.setOnItemClickListener(this);

		Calendar now = Calendar.getInstance();
		Calendar last = MainActivity.this.mainApplication.getLastStationListRequestDate();

		if ((last == null) || (!(now.get(Calendar.DAY_OF_YEAR) == last.get(Calendar.DAY_OF_YEAR))))
		{
			// Showing a "Please wait" progress dialog while retrieving data
			// from
			// server
			this.progressDialog = ProgressDialog.show(this,
					getResources().getString(R.string.download_progress_dialog_title),
					getResources().getString(R.string.download_progress_dialog_message), true);

			// Retrieving data from server
			new Thread(null, new Runnable()
			{
				@SuppressWarnings("synthetic-access")
				public void run()
				{
					MainActivity.this.getStationList();

					// Adding an event to UI event queue to kill progress dialog
					// and
					// refresh station list view
					runOnUiThread(new Runnable()
					{
						public void run()
						{
							MainActivity.this.progressDialog.dismiss();
							MainActivity.this.stationListAdapter.notifyDataSetChanged();
						}
					});
				}
			}, "stationListRetriever").start();
		}

		// TODO further check the result of getStationList call
		// (that should be a boolean)
		MainActivity.this.mainApplication.setLastStationListRequestDate(now);
	}

	/**
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView,
	 *      android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		String stationID = null;

		// Retrieving the id of the selected station

		if (parent == (AdapterView<?>) this.findViewById(R.id.MainActivityListViewStations))
			stationID = ((TextView) view.findViewById(R.id.StationInfoTextViewStationID)).getText().toString();
		else // favorite station
		if (this.mainApplication.getFavoriteStation() != null)
			stationID = this.mainApplication.getFavoriteStation().getID();

		// If station ID is not null (i.e. not the "not-yet-selected" favorite
		// station), starting a new StationActivity
		if (stationID != null)
		{
			this.mainApplication.setCurrentStationByID(stationID);
			startActivity(new Intent(this, StationActivity.class));
		}
	}

	/**
	 * Requests server to refresh the list of known stations.
	 */
	private void getStationList()
	{
		// TODO remove this (debug purpose)
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// Clear the list of known stations
		this.mainApplication.getStations().clear();

		JSONArray jsonStationList = null;
		URL url = null;
		try
		{
			// TODO no longer hardcode server's URL (for the moment, a mock
			// server is used)
			url = new URL("http://192.168.2.1:8888/stations.json");
		}
		catch (MalformedURLException e1)
		{
			// TODO handle exception
			e1.printStackTrace();
		}
		HttpURLConnection urlConnection = null;
		try
		{
			urlConnection = (HttpURLConnection) url.openConnection();
		}
		catch (IOException e1)
		{
			// TODO handle exception
			e1.printStackTrace();
		}

		// Reading JSON data
		try
		{
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			ByteArrayOutputStream outBuffer = new ByteArrayOutputStream(128);
			int read = -1;
			while ((read = in.read()) != -1)
				outBuffer.write(read);
			jsonStationList = new JSONArray(outBuffer.toString("iso-8859-1"));
		}
		catch (Exception e)
		{
			// TODO handle exception
			e.printStackTrace();
		}
		finally
		{
			urlConnection.disconnect();
		}

		// Clearing stations list
		// TODO no longer clear but sync
		this.mainApplication.getStations().clear();

		// Parsin JSON array to populate the list of stations
		for (int i = 0; i < jsonStationList.length(); i++)
		{
			try
			{
				// Getting next JSON object
				JSONObject jsonStation = jsonStationList.getJSONObject(i);

				// TODO no longer hardcode JSON field name
				String stationID = jsonStation.getString("id");

				// TODO no longer hardcode JSON field name
				String stationDescription = jsonStation.getString("libellé");

				Coordinates stationLocation = null;

				try
				{
					float longitude = Float.parseFloat(jsonStation.getString("longitude"));
					float latitude = Float.parseFloat(jsonStation.getString("latitude"));
					int altitude = Integer.parseInt(jsonStation.getString("altitude"));
					stationLocation = new Coordinates(longitude, latitude, altitude);
				}
				catch (Exception e)
				{
					// If something goes wrong while retrieving location, no
					// location is built
				}

				Station station;
				try
				{
					station = new Station(stationID, stationDescription, stationLocation);
				}
				catch (UnexpectedNullParameterException e)
				{
					continue;
				}

				// Adding station
				this.mainApplication.getStations().add(station);
			}
			catch (JSONException e)
			{
				// // TODO handle exception
				e.printStackTrace();
			}

			// Sorting list of known stations
			Collections.sort(this.mainApplication.getStations());
		}
	}

	/**
	 * Internal class used to display station information (ID/description), used
	 * with both station list and favorite station views.
	 * 
	 * @author sebastienjean
	 * 
	 */
	private class StationInfoAdapter extends AbstractObjectListAdapter<Station>
	{

		/**
		 * Creates a new <tt>StationInfoAdapter</tt> instance, from given
		 * context/layoutID/list of stations.
		 * 
		 * @param context
		 *            the context associated to the list view
		 * @param layoutID
		 *            the layout associated to the list view
		 * @param stations
		 *            the list of stations
		 */
		public StationInfoAdapter(Context context, int layoutID, List<Station> stations)
		{
			super(context, layoutID, stations);
		}

		/**
		 * <i>Only the favorite station item is disable, when not yet
		 * initialized.</i>
		 * 
		 * @see android.widget.BaseAdapter#isEnabled(int)
		 */
		@Override
		public boolean isEnabled(int position)
		{
			return (this.getObjects().get(position) != null);
		}

		/**
		 * @see fr.iutvalence.android.weathertrack.AbstractObjectListAdapter#displayObject(int,
		 *      android.view.View, android.view.ViewGroup)
		 */
		@Override
		protected View displayObject(int position, View view, ViewGroup parent)
		{
			Station station = (Station) this.getItem(position);
			TextView idTextView = (TextView) view.findViewById(R.id.StationInfoTextViewStationID);
			TextView descriptionTextView = (TextView) view.findViewById(R.id.StationInfoTextViewStationDescription);

			if (station == null)
			{
				idTextView.setText(getResources().getString(R.string.no_title));
				descriptionTextView.setText(getResources().getString(R.string.no_content));
			}

			else
			{
				idTextView.setText(station.getID());
				descriptionTextView.setText(station.getDescription());
			}

			return view;
		}
	}
}
