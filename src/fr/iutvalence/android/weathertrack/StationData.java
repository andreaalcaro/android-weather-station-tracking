package fr.iutvalence.android.weathertrack;

/**
 * Class allowing to handle data reported by a station. <tt>StationData</tt>
 * objects are immutable.
 * 
 * @author sebastienjean
 * 
 */
public class StationData
{
	/**
	 * Data type.
	 */
	private final DataTypeEnum type;

	/**
	 * Data value. The value reference is generic, since value's type depends on
	 * data type.<br/>
	 * <ul>
	 * <li> <tt>double</tt> for <tt>TEMP1</tt> and <tt>TEMP2</tt></li>
	 * <li> <tt>int</tt> for <tt>PRESSURE</tt>, <tt>LUX</tt>, <tt>HYGRO</tt>,
	 * <tt>WIN_DIR</tt>, <tt>WIN_SPEED</tt></li>
	 * <li> <tt>java.util.Calendar</tt> for <tt>DATE</tt>.
	 */
	private final Object value;

	/**
	 * Creates a <tt>StationData</tt> instance from given type/value elements.
	 * 
	 * @param type
	 *            data type
	 * @param value
	 *            value
	 */
	public StationData(DataTypeEnum type, Object value)
	{
		super();
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns data type.
	 * 
	 * @return data type.
	 */
	public DataTypeEnum getType()
	{
		return this.type;
	}

	/**
	 * Returns value.
	 * 
	 * @return value
	 */
	public Object getValue()
	{
		return this.value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "StationData [type=" + this.type + ", value=" + this.value + "]";
	}
}
