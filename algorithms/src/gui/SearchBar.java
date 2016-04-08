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

	public Graphics paint(int index, Color color, Graphics g)
	{

		g.setColor(color);

		int x = (index * getWidth()) / length;
		int y = 0;
		int width = getWidth() / length;
		int height = getHeight();
		System.out.println("Printing" + x + " " + y + "," + width + " " + height);
		g.fillRect(x, y, width, height);
		return g;
	}

	public void redraw()
	{
		revalidate();
		repaint();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Iterator<Integer> iterator = indices.listIterator();
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		for (int i = 0; i < count; i++)
		{
			g = paint(iterator.next(), Color.GREEN, g);
		}
	}

	public void add(int index)
	{
		this.indices.add(index);
		count = count + 1;
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
