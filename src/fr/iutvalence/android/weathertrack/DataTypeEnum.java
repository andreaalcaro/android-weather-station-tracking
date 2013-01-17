package fr.iutvalence.android.weathertrack;

/**
 * Enumeration for data types reported by weather stations
 *
 * @author sebastienjean
 */
public enum DataTypeEnum
{
    /** Timestamp */
    DATE(" "), // NON-NLS
    /** Primary temperature (often external), in Celsius degrees */
    TEMP1(" °C"), // NON-NLS
    /** Secondary temperature (often internal), in Celsius degrees */
    TEMP2(" °C"), // NON-NLS
    /** Pressure, in hectoPascals */
    PRESSURE(" hPa"), // NON-NLS
    /** Wind speed, in meters per second */
    WIND_SPEED(" m/s"), // NON-NLS
    /** Wind direction (relative to North), in degrees */
    WIND_DIR(" °"), // NON-NLS
    /** relative humidity, in percent */
    HYGRO(" %"), // NON-NLS
    /** Luminosity, in Lux */
    LUX(" lux"); // NON-NLS

    private final String m_type;

    DataTypeEnum(final String type)
    {
        m_type = type;
    }

    /**
     * Returns the string representation of the unit associated to a given data type.
     *
     * @return the string representation of the unit associated to <tt>dataType</tt>
     */
    public String unitToString()
    {
        return m_type;
    }
}
