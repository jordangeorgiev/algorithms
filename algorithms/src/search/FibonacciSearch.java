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
	// fib(n-2)
	int			fib0						= 0;
	// fib(n-1)
	int			fib1						= 0;
	// fib(n)
	int			fib2						= 1;
	int			offset					= 0;
	Result	previous_result	= Result.UNDEF;

	public FibonacciSearch(int[] items, int item)
	{
		super(items, item);
		this.start_time = System.nanoTime();
		index = Math.min((offset + fib2), items.length - 1);
		calculate();
		end_time = System.nanoTime();
		time = end_time - start_time;
		this.total_time = time;
	}

	public FibonacciSearch(int[] items, int item, long total_time, long start_time, int steps, int fib0, int fib1,
			int fib2, int offset, Result result)
	{
		super(items, item);
		previous_result = result;
		this.start_time = start_time;
		this.steps = steps;
		this.fib0 = fib0;
		this.fib1 = fib1;
		this.fib2 = fib2;
		this.offset = offset;
		// if (search() != Result.FOUND)
		// {
		index = Math.min((this.offset + this.fib2), items.length - 1);
		calculate();
		// }
		end_time = System.nanoTime();
		time = end_time - start_time;
		this.total_time = total_time + time;
		steps = steps + 1;
	}

	/**
	 * Get next fibonacci number
	 */
	private int nextFibonacci()
	{
		this.fib0 = this.fib1;
		this.fib1 = this.fib2;
		// fib2 = fib2 + fib1, formula
		this.fib2 = this.fib1 + this.fib0;

		return this.fib2;
	}

	/**
	 * Get previous fibonacci number
	 */
	private int prevFibonacci()
	{
		if (this.fib0 == 0)
		{
			return resetFibonacci();
		}
		else
		{
			this.fib0 = this.fib1 - this.fib0;
			this.fib1 = this.fib2 - this.fib1;
			this.fib2 = this.fib0 + this.fib1;
		}
		return this.fib2;
	}

	/**
	 * Reset Fibonacci numbers
	 */
	private int resetFibonacci()
	{

		this.fib0 = 0;
		this.fib1 = 0;
		this.fib2 = 1;
		return this.fib2;
	}

	/**
	 * Get next result
	 */
	protected void calculate()
	{
		previous_result = result;
		search();

	}

	public Search next()
	{
		long next_start_time = System.nanoTime();
		// if (((previous_result == Result.LEFT) && (result == Result.RIGHT))
		// || ((previous_result == Result.RIGHT) || (result == Result.LEFT)))
		// {
		switch (result)
		{
			case LEFT:

				prevFibonacci();
				resetFibonacci();
				return new FibonacciSearch(items, item, total_time, next_start_time, steps + 1, this.fib0, this.fib1, this.fib2,
						offset, Result.LEFT);
			case RIGHT:
				nextFibonacci();
				offset = index;
				return new FibonacciSearch(items, item, total_time, next_start_time, steps + 1, this.fib0, this.fib1, this.fib2,
						index, Result.RIGHT);
			case UNDEF:
				return this;
			case FOUND:
				System.out.println("Hi");
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
		return new Object[] { steps, index, result.toString(), time, total_time, fib0, fib1, fib2, offset };
	}

}
