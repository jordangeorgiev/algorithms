package search;

public class BinarySearch extends Search
{
	int	left;
	int	right;

	public BinarySearch(int[] items, int item)
	{
		super(items, item);
		this.start_time = System.nanoTime();
		left = 0;
		right = items.length - 1;
		index = getMidPoint();
		calculate();
		end_time = System.nanoTime();
		time = end_time - start_time;
		this.total_time = time;

	}

	public BinarySearch(int[] items, int item, long total_time, long start_time, int steps, int left, int right)
	{
		super(items, item);
		this.start_time = start_time;
		this.steps = steps;
		this.left = left;
		this.right = right;
		index = getMidPoint();
		calculate();
		end_time = System.nanoTime();
		time = end_time - start_time;
		this.total_time = total_time + time;

	}

	private int getMidPoint()
	{
		return left + (right - left) / 2;
	}

	/**
	 * Get next result
	 */
	protected void calculate()
	{
		search();
		boolean out_of_range = (Math.abs(Math.abs(right) - Math.abs(left)) <= 1);
		if (out_of_range && (result == Result.LEFT || result == Result.RIGHT))
		{
			// result = Result.NOT_FOUND;
		}
	}

	public Search next()
	{
		long next_start_time = System.nanoTime();
		switch (result)
		{
			case LEFT:
				// between left and midpoint
				if (left + 1 == right)
				{
					// Sublength is 1, manually include upper bound.
					return new BinarySearch(items, item, total_time, next_start_time, steps + 1, left + 1, getMidPoint());
				}
				return new BinarySearch(items, item, total_time, next_start_time, steps + 1, left, getMidPoint());
			case RIGHT:
				// between midpoint and right
				if (left + 1 == right)
				{
					// Sublength is 1, manually include upper bound.
					return new BinarySearch(items, item, total_time, next_start_time, steps + 1, getMidPoint() + 1, right);
				}
				return new BinarySearch(items, item, total_time, next_start_time, steps + 1, getMidPoint(), right);
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

	/*
	 * @Override public void getNextStep() { // index = 0;
	 * 
	 * System.out.println("left:  " + left + " Right: " + right); int midPoint =
	 * left + ((right - left) / 2);
	 * 
	 * index = midPoint; System.out.println("index:  " + index);
	 * 
	 * if (items[midPoint] == item) { System.out.print(
	 * "The element you are searching for is located at index:"); // return right;
	 * result = Result.EQUAL; // index = midPoint; } else if (right == left &&
	 * items[right] != item) { System.out.println(
	 * "item is not an element of the array. The value returned is not actually the index of item"
	 * ); // return -1; result = Result.UNDEF; index = left + ((right - left) /
	 * 2);
	 * 
	 * } else if (item <= items[midPoint]) { // return binSearch(items, item,
	 * left, midPoint); right = midPoint - 1; result = Result.LEFT; index = left +
	 * ((right - left) / 2); } else if (item >= items[midPoint]) {
	 * System.out.println(); // return binSearch(items, item, midPoint, right);
	 * left = midPoint + 1; result = Result.RIGHT; index = left + ((right - left)
	 * / 2); } else { System.out.println("Something went wrong"); // return -1;
	 * index = left + ((right - left) / 2); }
	 * 
	 * }
	 */
	public Object[] getRow()
	{
		return new Object[] { steps, index, result.toString(), time, total_time, left, right };
	}

}
