package search;


public class absBinSearch extends Search{

	public absBinSearch(int[] items, int item) {
		super(items, item);
		// TODO Auto-generated constructor stub
	}
	/*
	public int binSearch(int[] items,int item,int left, int right){
		
		System.out.println(items + " " +  item + " " + left + " " + right);
		
		
		
	}
	*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getNextStep() {
		//index = 0;
		
		System.out.println("left:  " + left + " Right: " + right);
		int midPoint = left + ((right - left)/2);
		
		index = midPoint; 
		System.out.println("index:  " + index);
		
		
		
		
		if(items[midPoint] == item){
			System.out.print("The element you are searching for is located at index:");
			//return right;
			result = Result.EQUAL;
			//index = midPoint;
		}
		else if(right == left && items[right] != item){
			System.out.println("item is not an element of the array. The value returned is not actually the index of item");
			//return -1;
			result = Result.UNDEF;
			index = left + ((right - left)/2);
			
		}
		else if(item <= items[midPoint]){
			//return binSearch(items, item, left, midPoint);
			right = midPoint - 1;
			result = Result.LEFT;
			index = left + ((right - left)/2);
		}
		else if(item >= items[midPoint]){
			System.out.println();
			//return binSearch(items, item, midPoint, right);
			left = midPoint + 1;
			result = Result.RIGHT;
			index = left + ((right - left)/2);
		}
		else{
			System.out.println("Something went wrong");
			//return -1;
			index = left + ((right - left)/2);
		}
		
		
	}
	public Object[] getRow()
	{
		return new Object[] { steps, index, result.toString(), left, right };
	}

}
