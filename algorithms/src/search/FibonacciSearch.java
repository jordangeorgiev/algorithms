package search;

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
	int	len			= items.length - 1;
	int	fib			= 2;
	int	fib1		= 1;
	int	fib2		= 1;
	int	offset	= -1;

	public FibonacciSearch(int[] items, int item)
	{
		super(items, item);
		relVars.add(fib);
		relVars.add(fib1);
		relVars.add(fib2);
	}

	@Override
	public void getNextStep()
	{
		start_nano_time = System.nanoTime();
		if (steps == 0)
		{
			// this gets the variables to a good starting point for us to start
			// looking at the array
			while (fib < len)
			{
				fib2 = fib1;
				fib1 = fib;
				fib = fib1 + fib2;
			}
		}

		// this makes sure that we don't try and look outside of our array
		index = Math.min((offset + fib2), len);

		/*
		 * Pre: This algorithm takes in an array that is assumed to be sorted and
		 * composed of only integers, and an integer to search for.
		 * 
		 * Post: This algorithm returns the index at which the int we are looking
		 * for is found.
		 */
		if (fib <= 1)
		{
			result = Result.NOTFOUND;
		}
		else if (item < items[index])
		{

			fib = fib2;
			fib1 = fib1 - fib2;
			fib2 = fib - fib1;
			result = Result.LEFT;
		}
		else if (item > items[index])
		{
			fib = fib1;
			fib1 = fib2;
			fib2 = fib - fib1;
			// offset helps make sure that if fib,1,or 2 repeat then we still look at
			// a different index
			offset = index;
			result = Result.RIGHT;
		}
		else if (item == items[index])
		{
			result = Result.EQUAL;
		}
		else
		{
			result = Result.UNDEF;
		}
		// this is so we can print the values in the GUI
		relVars.set(0, fib);
		relVars.set(1, fib1);
		relVars.set(2, fib2);

		end_nano_time = System.nanoTime();
		diff_nano_time = end_nano_time - start_nano_time;
		total_nano_time = total_nano_time + diff_nano_time;
	}

	public Object[] getRow()
	{
		return new Object[] { steps, index, result.toString(), diff_nano_time, total_nano_time, fib, fib1, fib2 };
	}

}
