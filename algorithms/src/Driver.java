
import java.util.*;
import gui.*;
import search.*;

public class Driver
{

	public static void main(String[] args)
	{
		// System.out.println("please enter a one line string with integers
		// seperated by any delimiter");
		// Scanner scan = new Scanner( System.in );
		// String str = "";
		// // while(scan.hasNextLine()){
		// str = str + scan.nextLine();
		// // }
		//
		// System.out.println(str);
		// String[] stArr = str.split("^\\d+$");
		// System.out.println(Arrays.toString(stArr));

		// String str = strReader.nextLine();

		// int[] items = {
		// -900, -5, -1,
		// 0, 9, 36, 37, 38, 39,
		// 40, 42, 43, 45, 48, 50,
		// 55, 60, 67, 80, 100, 120,
		// 121, 123, 124, 125, 126,
		// 127, 128, 129, 131, 140,
		// 200, 289, 900, 10001
		// };

		int[] items = new int[10023];
		for (int i = 0; i < 10023; i++)
		{
			items[i] = i;
		}

		int item = 10000;

		Arrays.sort(items);
		// int item = 80;

		FibonacciSearch search = new FibonacciSearch(items, item);

		int step = 0;

		boolean stop = false;

		while (stop == false && step < 2200)
		{
			// System.out.println(search.result);
			// search.search();

			search.next();
			System.out.println(" ");
			System.out.println(search.toString());

			if ((search.result == Result.EQUAL) || (search.result == Result.NOTFOUND))
			{
				System.out.println(" ");
				// System.out.println(search.result);
				stop = true;
				// break;
			}

			step += 1;

		}
	}

}
