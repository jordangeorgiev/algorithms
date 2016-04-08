/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
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
		setBackground(Color.WHITE);
		this.length = length;
		this.count = 0;
		this.indices = new ArrayList<Integer>();
	}

	public void paint(int index, Color color, Graphics g)
	{

		g.setColor(color);

		int x = (index * getWidth()) / length;
		int y = getHeight();
		int width = getWidth() / length;
		int height = getHeight();

		g.drawRect(x, y, width, height);
	}

	public void redraw()
	{
		System.out.println(getWidth());
		revalidate();
		repaint();
	}

	public void paintComponent(Graphics g)
	{
		Iterator<Integer> iterator = indices.listIterator();
		g.clearRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < count; i++)
		{
			paint(iterator.next(), Color.BLACK, g);
		}
		super.paintComponent(g);
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
		redraw();
	}

}
