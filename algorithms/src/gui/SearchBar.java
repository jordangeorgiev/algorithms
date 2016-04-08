/**
 * 
 */
package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import javax.swing.JPanel;

/**
 * @author AlexKerzner
 *
 */
public class SearchBar extends JPanel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3716457329313395720L;

	int												length;
	int												count;
	ArrayList<Integer>				indices;

	public SearchBar(int length)
	{
		this.length = length;
		this.count = 0;
		this.indices = new ArrayList<Integer>();
	}

	public void paint(int index, Color color)
	{

		this.getGraphics().setColor(color);

		int x = (index * getWidth()) / length;
		int y = getHeight();
		int width = getWidth() / length;
		int height = getHeight();

		this.getGraphics().drawRect(x, y, width, height);
	}

	public void redraw()
	{
		Iterator<Integer> iterator = indices.listIterator();
		for (int i = 0; i < count; i++)
		{
			paint(iterator.next(), Color.GRAY);
		}
	}

	public void add(int index)
	{
		this.indices.add(index);
		redraw();
	}

	public void reset(int length)
	{
		this.length = length;
		this.count = 0;
		this.indices.clear();
		this.getGraphics().clearRect(0, 0, getWidth(), getHeight());
	}

}
