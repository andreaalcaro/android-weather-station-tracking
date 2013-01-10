package fr.iutvalence.android.weathertrack;


/**
 * Class allowing to handle weather station information (ID, description and location).
 * <tt>Station</tt> objects are immutable. 
 * 
 * @author sebastienjean
 *
 */
public class Station implements Comparable<Station>
{
	/**
	 * Station's ID (short name, supposed to be unique on server).
	 */
	private final String id;
	
	/**
	 * Station's description.
	 */
	private final String description;
	
	/**
	 * Station's location.
	 * Location may be <tt>null</tt> if the station has no location information associated on server.
	 */
	private final Coordinates location;

	/**
	 * Creates a new <tt>Station</tt> instance from given id/description/(location) elements.
	 * @param id stations's id
	 * @param description station's description
	 * @param location station's location
	 * @throws UnexpectedNullParameterException if either mandatory parameters <tt>id</tt> or <tt>description</tt> are <tt>null</tt>
	 */
	public Station(String id, String description, Coordinates location) throws UnexpectedNullParameterException
	{
		super();
		if ((id == null)||(description == null)) throw new UnexpectedNullParameterException();
		this.id = id;
		this.description = description;
		this.location = location;
	}

	/**
	 * Returns station's ID.
	 * @return station's ID
	 */
	public String getID()
	{
		return this.id;
	}

	/**
	 * Returns station's description.
	 * @return station's description
	 */
	public String getDescription()
	{
		return this.description;
	}
	
	/**
	 * Returns station's location.
	 * @return station's location
	 */
	public Coordinates getLocation()
	{
		return this.location;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Station [name=" + this.id + ", description=" + this.description + "location= "+this.location+"]";
	}

	/**
	 * Compares two <tt>Station</tt> objects, performing a lexicographical comparison on stations' ID.  
	 * 
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Station another)
	{
		return this.id.compareTo(another.id);
	}
}
