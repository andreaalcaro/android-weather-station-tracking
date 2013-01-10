package fr.iutvalence.android.weathertrack;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.app.Application;

/**
 * Application proving access to globals, cache management, ...
 * 
 * @author sebastienjean
 *
 */
public class MainApplication extends Application
{
	/**
	 * List of known stations.
	 */
	private List<Station> stations;

	/**
	 * Station that is currently selected by the user.
	 * This reference remains <tt>null</tt> until the user picks a station to track.
	 */
	private Station currentStation;
	
	/**
	 * Station that is currently declared as favorite by the user.
	 * This reference remains <tt>null</tt> until the user checks a station as favorite.
	 */
	private Station favoriteStation;

	
	/**
	 * Date of the last request sent to the server to retrieve the list of known stations.
	 */
	private Calendar lastStationListRequestDate;
	
	/**
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		this.stations = new ArrayList<Station>();
		this.currentStation = null;
		this.favoriteStation = null;
		this.lastStationListRequestDate = null;
	}

	/**
	 * Returns list of known stations.
	 * @return list of known stations
	 */
	protected List<Station> getStations()
	{
		return Collections.synchronizedList(this.stations);
	}
	
	/**
	 * Returns the station with a given ID (if it exists).
	 * @param id the ID of the station to retrieve
	 * @return the station whose ID matches <tt>id</tt>, <tt>null</tt> otherwise
	 */
	protected synchronized Station getStationByID(String id)
	{
		for (Station s : this.stations)
			if (s.getID().equals(id))
				return s;

		return null;
	}
	
	/**
	 * Returns current station.
	 * @return current station.
	 */
	protected Station getCurrentStation()
	{
		return this.currentStation;
	}
	
	/**
	 * Sets current station to a given one.
	 * @param currentStation current station
	 */
	protected void setCurrentStation(Station currentStation)
	{
		this.currentStation = currentStation;
	}

	/**
	 * Sets current station to the one having a given ID.<br/>
	 * if the station is unknown, <tt>currentStation</tt> is set to <tt>null</tt>. 
	 * @param stationID station's id
	 */
	protected void setCurrentStationByID(String stationID)
	{
		this.currentStation = this.getStationByID(stationID);
	}
	
	/**
	 * Returns favorite station.
	 * @return favorite station.
	 */
	protected Station getFavoriteStation()
	{
		return this.favoriteStation;
	}

	/**
	 * Sets favorite station.
	 * @param favoriteStation favorite station
	 */
	protected void setFavoriteStation(Station favoriteStation)
	{
		this.favoriteStation = favoriteStation;
	}

	/**
	 * Returns the date of the last request sent to the server to retrieve the list of known stations.
	 * @return the date of the last request sent to the server to retrieve the list of known stations
	 */
	protected Calendar getLastStationListRequestDate()
	{
		return this.lastStationListRequestDate;
	}

	/**
	 * Sets the date of the last request sent to the server to retrieve the list of known stations.
	 * @param lastStationListRequestDate the date of the last request sent to the server to retrieve the list of known stations
	 */
	protected void setLastStationListRequestDate(Calendar lastStationListRequestDate)
	{
		this.lastStationListRequestDate = lastStationListRequestDate;
	}
	
	
}
