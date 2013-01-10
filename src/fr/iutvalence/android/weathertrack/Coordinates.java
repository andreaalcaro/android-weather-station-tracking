package fr.iutvalence.android.weathertrack;

/**
 * Class allowing to handle immutable locations (latitude/longitude/altitude).<br/>
 * Location elements are represented using <a href="http://developers.google.com/maps/">Google Maps</a> conventions:
 * <ul>
 * <li>Latitude and longitude are expressed in decimal degrees (latitude is positive at North, longitude is positive at East)</li>
 * <li>altitude is a (supposed to be positive) integer</i> 
 * 
 * @author sebastienjean
 *
 */
public class Coordinates
{
	/**
	 * Location's latitude.
	 */
	private final float longitude;

	/**
	 * Location's longitude.
	 */
	private final float latitude;
	
	/**
	 * Location's altitude.
	 */
	private final int altitude;
	
	/**
	 * Creates a new <tt>Coordinates</tt> instance, from given latitude/longitude/altitude elements. 
	 * @param longitude location's longitude
	 * @param latitude location's latitude
	 * @param altitude location's altitude
	 */
	public Coordinates(float longitude, float latitude, int altitude)
	{
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
	}

	/**
	 * Returns location's longitude.
	 * @return location's longitude
	 */
	public float getLongitude()
	{
		return this.longitude;
	}

	/**
	 * Returns location's latitude.
	 * @return location's latitude.
	 */
	public float getLatitude()
	{
		return this.latitude;
	}

	/**
	 * Returns location's altitude.
	 * @return location's altitude.
	 */
	public int getAltitude()
	{
		return this.altitude;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Coordinates [longitude=" + this.longitude + ", latitude=" + this.latitude + ", altitude=" + this.altitude + "]";
	}
}
