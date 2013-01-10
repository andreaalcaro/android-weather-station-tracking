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
}
