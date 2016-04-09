package search;

/**
 * 
 * @author ajkerzner@smcm.edu
 *
 */
public abstract class Search
{

	int[]					items;
	int						item;
	int						steps				= 0;

	long					start_time;
	long					end_time;
	long					time;
	long					total_time	= 0;

	public int		index;
	public Result	result			= Result.UNDEF;

	public Search(int[] items, int item)
	{
		this.items = items;
		this.item = item;
		// this.steps;
		this.result = Result.UNDEF;
	}

	public abstract Search next();

	protected abstract void calculate();

	// Throws indexOutOfBounds

	protected Result search()
	{
		if (index < 0 || index > items.length - 1)
		{
			this.result = Result.UNDEF;
			return this.result;
		}

		int value = items[index];

		if (item < value)
		{
			this.result = Result.LEFT;
			if (index == 0)
			{
				// Left of leftmost value
				this.result = Result.NOT_FOUND;
			}
		}
		else if (item == value)
		{
			this.result = Result.FOUND;
		}
		else
		{
			this.result = Result.RIGHT;
			if (index == items.length - 1)
			{
				// Right of rightmost value
				this.result = Result.NOT_FOUND;
			}
		}

		return this.result;

	}

	/**
	 *
	 *
	 */
	public String toString()
	{
		// Step #: searching bounds [#,#] of array of length ###, result: $$$.
		String string = "Step " + steps;
		string = string + ": comparing value #" + index + " result: ";
		string = string + result.toString() + "." + "\n";
		return string;
	}

	public abstract Object[] getRow();
}
