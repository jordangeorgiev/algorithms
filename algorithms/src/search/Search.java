package search;

import java.util.ArrayList;

/**
 * 
 * @author ajkerzner@smcm.edu
 *
 */
public abstract class Search
{

	// Bound bound;
	int[]								items;
	int									item;
	int									steps;

	int									left;
	int									right;
	public int					index;
	int									nextIndex;

	long								start_nano_time;
	long								end_nano_time;
	long								diff_nano_time;
	long								total_nano_time	= 0;

	ArrayList<Integer>	relVars					= new ArrayList<Integer>();

	public Result				result;

	public Search(int[] items, int item)
	{
		this(items, item, 0, items.length - 1);
	}

	public Search(int[] items, int item, int left, int right)
	{
		this.items = items;
		this.item = item;
		this.steps = 0;
		this.right = right;
		this.left = left;
		this.result = Result.UNDEF;
		// this.nextIndex = 0;

	}

	public abstract void getNextStep();

	// Throws indexOutOfBounds

	public Result search()
	{
		int value = items[index];
		// getNextStep();
		if (item < value)
		{
			this.result = Result.LEFT;
		}
		else if (item == value)
		{
			this.result = Result.EQUAL;
		}
		else if (item > value)
		{
			this.result = Result.RIGHT;
		}
		else
		{
			this.result = Result.UNDEF;
		}

		return this.result;
	}

	public void next()
	{

		if ((this.result == Result.EQUAL) || (this.result == Result.NOTFOUND))
		{
			return;
		}
		else
		{
			// this.index = this.nextIndex;
			getNextStep();
		}

	}

	/**
	 *
	 *
	 */
	public String toString()
	{
		// String bounds = "[" + left + "," + right + "]";

		// Step #: searching bounds [#,#] of array of length ###, result: $$$.
		String string = "Step " + steps;
		// string = string + bounds + " of array of length ";
		string = string + ", comparing value #" + index + " result: ";
		string = string + result.toString() + "." + "\n";
		return string;
	}

	public abstract Object[] getRow();
}
