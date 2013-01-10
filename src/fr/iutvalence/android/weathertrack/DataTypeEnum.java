package fr.iutvalence.android.weathertrack;

/**
 * Enumeration for data types reported by weather stations
 * 
 * @author sebastienjean
 *
 */
public enum DataTypeEnum
{
	/**
	 * Timestamp
	 */
	DATE, 
	/**
	 * Primary temperature (often external), in Celsius degrees
	 */
	TEMP1, 
	/**
	 * Secondary temperature (often internal), in Celsius degrees
	 */
	TEMP2,  
	/**
	 * Pressure, in hectoPascals
	 */
	PRESSURE, 
	/**
	 * Wind speed, in meters per second
	 */
	WIND_SPEED,	
	/**
	 * Wind direction (relative to North), in degrees
	 */
	WIND_DIR, 
	/**
	 * relative humidity, in percent 
	 */
	HYGRO, 
	/**
	 * Luminosity, in Lux
	 */
	LUX;
	
	/**
	 * Returns the string representation of the unit associated to a given data type.
	 * @return the string representation of the unit associated to <tt>dataType</tt>
	 */
	public String unitToString()
	{
		// DATE, TEMP1, TEMP2, PRESSURE, WIND_SPEED, WIND_DIR, HYGRO, LUX;
		if (this == DATE)
			return "";
		if (this == TEMP1)
			return "°C";
		if (this == TEMP2)
			return "°C";
		if (this == PRESSURE)
			return "hPa";
		if (this == HYGRO)
			return "%";
		if (this == LUX)
			return "lux";
		if (this == WIND_DIR)
			return "m/s";
		if (this == WIND_SPEED)
			return "°";
		return null;
	}
}
