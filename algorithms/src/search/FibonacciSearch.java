package search;

// this gets the variables to a good starting point for us to start
// looking at the array

// this makes sure that we don't try and look outside of our array

/*
 * Pre: This algorithm takes in an array that is assumed to be sorted and
 * composed of only integers, and an integer to search for.
 * 
 * Post: This algorithm returns the index at which the int we are looking for is
 * found.
 */

/**
 * @reference <a href=
 *            "https://www.informatik.hu-berlin.de/de/forschung/gebiete/wbi/teaching/archive/ss11/vl_algorithmen/09_searching.pdf">
 *            Reference</a>
 * 
 * @author nabackert@smcm.edu
 * 
 *
 *
 */
public class FibonacciSearch extends Search
{
	// int length = items.length - 1;
	int	fib			= 2;
	int	fib1		= 1;
	int	fib2		= 1;
	int	offset	= -1;

	public FibonacciSearch(int[] items, int item)
	{
		super(items, item);
		this.start_time = System.nanoTime();
		while (fib < items.length - 1)
		{
			fib2 = fib1;
			fib1 = fib;
			fib = fib2 + fib1;
		}
		index = Math.min((offset + fib2), items.length - 1);
		calculate();
		end_time = System.nanoTime();
		time = end_time - start_time;
		this.total_time = time;
	}

	public FibonacciSearch(int[] items, int item, long total_time, long start_time, int steps, int fib, int fib1,
			int fib2, int offset)
	{
		super(items, item);
		this.start_time = start_time;
		this.steps = steps;
		this.fib = fib;
		this.fib1 = fib1;
		this.fib2 = fib2;
		this.offset = offset;

		index = Math.min((offset + fib2), items.length - 1);
		calculate();

		end_time = System.nanoTime();
		time = end_time - start_time;
		this.total_time = total_time + time;
		steps = steps + 1;
	}

	/**
	 * Get next result
	 */
	protected void calculate()
	{
		search();
		if (fib <= 1)
		{
			result = Result.NOT_FOUND;
		}
	}

	public Search next()
	{
		long next_start_time = System.nanoTime();

		switch (result)
		{
			case LEFT:

				// fib = fib2;
				// fib1 = fib1 - fib2;
				// fib2 = fib - fib1;
				return new FibonacciSearch(items, item, total_time, next_start_time, steps + 1, fib2, fib1 - fib2, fib - fib1,
						offset);
			case RIGHT:
				// fib = fib1;
				// fib1 = fib2;
				// fib2 = fib - fib1;
				// offset = index
				return new FibonacciSearch(items, item, total_time, next_start_time, steps + 1, fib1, fib2, fib - fib1, index);
			case UNDEF:
				return this;
			case FOUND:
				return this;
			case NOT_FOUND:
				return this;
			default:
				return this;

		}
	}

	{
		// this is so we can print the values in the GUI

	}

	public Object[] getRow()
	{
		return new Object[] { steps, index, result.toString(), time, total_time, fib, fib1, fib2, offset };
	}

}
