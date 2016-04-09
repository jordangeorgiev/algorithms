package search;

public class ExponentialSearch extends Search
{
	public int			length;
	public boolean	boundsFound	= false;

	public ExponentialSearch(int[] items, int item)
	{
		super(items, item);
		length = items.length;
		index = 1;
		nextIndex = 1;
	}

	// could easily modify algorithm to take in as input whether the array is
	// sorted in ascending or descending order and search accordingly
	// Pre: we assume the array sortedArr is sorted in ascending order
	// public int expSearch(int[] sortedArr, int thing){
	//
	// int length = sortedArr.length;
	// int upper = 1; //current upper bound of the part of the array we are
	// searching in
	//
	// //For the purposes of this project we work with
	// //an array of finite size but this algorithm can be modified
	// //slightly to work with infinite lists
	//
	// while (upper < length && sortedArr[upper] < thing){
	// upper *= 2;
	// }
	//
	// upper = Math.min(upper, (length - 1));
	//
	//
	//
	// System.out.println(sortedArr + " " + thing + " " + upper/2 + " " + upper);
	// return binSearch(sortedArr, thing, upper/2, upper);
	// }

	@Override
	public void getNextStep()
	{
		start_nano_time = System.nanoTime();

		if (item > items[length - 1])
		{
			this.result = Result.NOTFOUND;
			return;
		}

		if (index < length)
		{
			if (items[index] == item)
			{
				this.result = Result.EQUAL;
			}
		}
		// int length = sortedArr.length;
		// int upper = 1; //current upper bound of the part of the array we are
		// searching in

		// For the purposes of this project we work with
		// an array of finite size but this algorithm can be modified
		// slightly to work with infinite lists

		if (boundsFound == false)
		{ // If we havent found our upper power of 2 bound

			if ((index < length && items[index] < item))
			{ // if our current power of two is less than the length of the array and
				// the item we are looking for is still greater than the item at the
				// index of the current power of two
				this.result = Result.RIGHT;
				index *= 2;
			}
			else
			{
				this.result = Result.LEFT;
				boundsFound = true;
				// System.out.println("index: " + index + " length:" + length);
				right = Math.min(index, (length - 1));
				left = right / 2;
				// System.out.println("Bounds found: Left: " + left + " Right:" +
				// right);
				int midPoint = left + ((right - left) / 2);

				index = midPoint;
			}
		}
		else
		{

			binSearch();
		}

		end_nano_time = System.nanoTime();
		diff_nano_time = end_nano_time - start_nano_time;
		total_nano_time = total_nano_time + diff_nano_time;
		steps = steps + 1;

		// while (upper < length && sortedArr[upper] < thing){
		// upper *= 2;
		// }
		//
		// upper = Math.min(upper, (length - 1));
		//
		//
		//
		// System.out.println(sortedArr + " " + thing + " " + upper/2 + " " +
		// upper);
		// return binSearch(sortedArr, thing, upper/2, upper);
	}

	public void binSearch()
	{
		// System.out.println("left: " + left + " Right: " + right);

		// System.out.println("index: " + index);

		if (items[index] == item)
		{
			// System.out.println("The element you are searching for is located at
			// index:" + index);
			// return right;
			result = Result.EQUAL;
			// index = midPoint;
		}
		else if (right == left && items[index] != item)
		{
			// System.out.println("item is not an element of the array. The value
			// returned is not actually the index of item");
			// return -1;
			result = Result.NOTFOUND;
			index = left + ((right - left) / 2);

		}
		else if (item <= items[index])
		{
			// return binSearch(items, item, left, midPoint);
			right = index - 1;
			result = Result.LEFT;
			index = left + ((right - left) / 2);
		}
		else if (item >= items[index])
		{
			// System.out.println();
			// return binSearch(items, item, midPoint, right);
			left = index + 1;
			result = Result.RIGHT;
			index = left + ((right - left) / 2);
		}
		else
		{
			// System.out.println("Something went wrong");
			// return -1;
			index = left + ((right - left) / 2);
		}

	}

	public Object[] getRow()
	{
		return new Object[] { steps, index, result.toString(), diff_nano_time, total_nano_time, left, right };
	}
}
