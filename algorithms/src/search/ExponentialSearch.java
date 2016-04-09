package search;

/**
 * 
 * @author sfrosenblatt@smcm.edu
 *
 */
// Pre: we assume the array sortedArr is sorted in ascending order
// Post: left, right, index, result and next index are updated;
public class ExponentialSearch extends Search
{
	public int length;

	public ExponentialSearch(int[] items, int item)
	{
		super(items, item);
		this.start_time = System.nanoTime();
		length = items.length;
		index = 1;
		calculate();
		end_time = System.nanoTime();
		time = end_time - start_time;
		this.total_time = time;

	}

	public ExponentialSearch(int[] items, int item, long total_time, long start_time, int steps, int index)
	{
		super(items, item);
		length = items.length;
		this.start_time = start_time;
		this.steps = steps;
		this.index = index;
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

		// Compare item against value at index
		boolean out_of_range = (index == length - 1);
		// (index < 0 || index > length - 1);
		search();
		if (out_of_range && (result == Result.RIGHT))
		{
			// Manual override
			result = Result.NOT_FOUND;
		}

	}

	public Search next()
	{
		long next_start_time = System.nanoTime();

		switch (result)
		{
			case LEFT:
				// Found bounds
				return new BinarySearch(items, item, total_time, next_start_time, steps + 1, index / 2, index);
			case RIGHT:
				// Keep going
				int next_index = index * 2;
				if (next_index > length - 1)
				{
					// Hit end
					next_index = index;
					return new BinarySearch(items, item, total_time, next_start_time, steps + 1, next_index, length - 1);
				}
				else
				{
					return new ExponentialSearch(items, item, total_time, next_start_time, steps + 1, next_index);
				}
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

	public Object[] getRow()
	{
		return new Object[] { steps, index, result.toString(), time, total_time, 0, 0 };

	}
	/*
	 * /** public void getNextStep() {
	 * 
	 * if (boundsFound == false) { // If we havent found our upper power of 2
	 * bound
	 * 
	 * if ((index < length && items[index] < item)) { // if our current power of
	 * two is less than the length of the array and // the item we are looking for
	 * is still greater than the item at the // index of the current power of two
	 * this.result = Result.RIGHT; nextIndex *= 2; } else { this.result =
	 * Result.LEFT; boundsFound = true; // System.out.println("index: " + index +
	 * " length:" + length); right = Math.min(index, (length - 1)); left = right /
	 * 2; // System.out.println("Bounds found: Left: " + left + " Right:" + //
	 * right); int midPoint = left + ((right - left) / 2); // System.out.println(
	 * "Left: " + left + "Right: " + right + "midpoint: " // + midPoint +
	 * "index: " + index); nextIndex = midPoint; } } else {
	 * 
	 * binSearch(); } end_nano_time = System.nanoTime(); diff_nano_time =
	 * end_nano_time - start_nano_time; total_nano_time = total_nano_time +
	 * diff_nano_time; steps = steps + 1;
	 * 
	 * // int length = sortedArr.length; // int upper = 1; //current upper bound
	 * of the part of the array we are // searching in
	 * 
	 * // For the purposes of this project we work with // an array of finite size
	 * but this algorithm can be modified // slightly to work with infinite lists
	 * // while (upper < length && sortedArr[upper] < thing){ // upper *= 2; // }
	 * // // upper = Math.min(upper, (length - 1)); // // // //
	 * System.out.println(sortedArr + " " + thing + " " + upper/2 + " " + //
	 * upper); // return binSearch(sortedArr, thing, upper/2, upper); }
	 * 
	 * public void binSearch() { // System.out.println("left: " + left +
	 * " Right: " + right);
	 * 
	 * // System.out.println("index: " + index); index = nextIndex;
	 * 
	 * // System.out.println("index " + index); if (items[index] == item) { //
	 * System.out.println("The element you are searching for is located at //
	 * index:" + index); // return right; result = Result.EQUAL; // index =
	 * midPoint; } else if (right <= left && items[index] != item) { //
	 * System.out.println("item is not an element of the array. The value //
	 * returned is not actually the index of item"); // return -1; result =
	 * Result.NOTFOUND; // index = left + ((right - left)/2);
	 * 
	 * } else if (item <= items[index]) { // return binSearch(items, item, left,
	 * midPoint); right = index - 1; result = Result.LEFT; nextIndex = left +
	 * ((right - left) / 2); } else if (item >= items[index]) { //
	 * System.out.println(); // return binSearch(items, item, midPoint, right);
	 * left = index + 1; result = Result.RIGHT; nextIndex = left + ((right - left)
	 * / 2); } else { // System.out.println("Something went wrong"); // return -1;
	 * // nextIndex = left + ((right - left)/2); }
	 * 
	 * }
	 * 
	 * public Object[] getRow() { return new Object[] { steps, index,
	 * result.toString(), diff_nano_time, total_nano_time, left, right }; } }
	 * 
	 * // could easily modify algorithm to take in as input whether the array is
	 * // sorted in ascending or descending order and search accordingly // Pre:
	 * we assume the array sortedArr is sorted in ascending order // public int
	 * expSearch(int[] sortedArr, int thing){ // // int length = sortedArr.length;
	 * // int upper = 1; //current upper bound of the part of the array we are //
	 * searching in // // //For the purposes of this project we work with // //an
	 * array of finite size but this algorithm can be modified // //slightly to
	 * work with infinite lists // // while (upper < length && sortedArr[upper] <
	 * thing){ // upper *= 2; // } // // upper = Math.min(upper, (length - 1)); //
	 * // // // System.out.println(sortedArr + " " + thing + " " + upper/2 + " " +
	 * upper); // return binSearch(sortedArr, thing, upper/2, upper); // }
	 * 
	 * // public static void main(String[] args) { // // // Scanner scan = new
	 * Scanner(System.in); // Object myVar = new Object(); // // // // boolean
	 * gotIt = false; // // while(gotIt == false){ // System.out.print(
	 * "Please enter the size of your array: "); // myVar = scan.next(); // Object
	 * scanned = myVar; // // if(scanned instanceof Integer){ // int size =
	 * (Integer) scanned; // gotIt = true; // System.out.println(size); // } // //
	 * } // // // /* // Object myVar = new Object(); // // myVar = scan.next(); //
	 * // // Object num = (Number) myVar; // // if(num instanceof Integer){ //
	 * System.out.print("true"); // } // else{ // System.out.print("False"); // }
	 * // }
	 */
	//
	//
	//
	//
	//
	// int[] sortedArr = {
	// -900, -5, -1,
	// 0, 9, 36,
	// 200, 289, 900, 10001
	// };
	//
	// ExponentialSearch drive = new ExponentialSearch();
	//
	// System.out.println(drive.expSearch(sortedArr, 10001));
	//
	// }

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
}
