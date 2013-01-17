package fr.iutvalence.android.weathertrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Class allowing to display each object of a list.
 *
 * @param <T> The type of objects of the list.
 *
 * @author sebastienjean
 */
public abstract class AbstractObjectListAdapter<T> extends BaseAdapter
{
    /** The list of objects to display. */
    private final List<T> objects;

    /** The context associated to this adapter */
    private final Context context;

    /** The ID of the layout associated to the view where to display each object. */
    private final int layoutID;

    /**
     * Creates a new <tt>ObjectListAdapter</tt> instance from given
     * context/layout/list of objects.
     *
     * @param context  the context associated to the view
     * @param layoutID the ID of the layout associated to the view
     * @param objects  the list of objects
     */
    public AbstractObjectListAdapter(final Context context, final int layoutID, final List<T> objects)
    {
        this.context = context;
        this.layoutID = layoutID;
        this.objects = objects;
    }

    /** @see Adapter#getCount() */
    @Override
    public final int getCount()
    {
        return this.objects.size();
    }

    /** @see Adapter#getItem(int) */
    @Override
    public final Object getItem(final int position)
    {
        return this.objects.get(position);
    }

    /**
     * <i>Unused here</i>.
     *
     * @see Adapter#getItemId(int)
     */
    @Override
    public final long getItemId(final int position)
    {
        return 0;
    }

    /**
     * <i>Here, the behavior is to inflate the layout if necessary, the
     * rendering is further performed by a call to the <tt>displayObject</tt>
     * abstract method.</i>
     *
     * @see Adapter#getView(int, View, ViewGroup)
     */
    @Override
    public final View getView(final int position, final View convertView, final ViewGroup parent)
    {
        View view = convertView;

        // If necessary, inflate the layout to get the view
        if (view == null)
        {
            final LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(this.layoutID, null);
        }

        return this.displayObject(position, view, parent);
    }

    /**
     * Returns the list of objects.
     *
     * @return the list of objects.
     */
    public final List<T> getObjects()
    {
        return this.objects;
    }

    /**
     * Returns layout ID.
     *
     * @return layout ID
     */
    public final int getLayoutID()
    {
        return this.layoutID;
    }

    /**
     * Renders and returns the view associated to the object at a given position
     * in the list.
     *
     * @param position object's position
     * @param view     the view associated to the object (inflated)
     * @param parent   the parent view
     *
     * @return the (rendered) view associated to the object
     */
    protected abstract View displayObject(int position, View view, ViewGroup parent);
}
