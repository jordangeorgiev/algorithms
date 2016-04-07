package search;

import java.util.Scanner;

public class absExponSearch extends Search {
	public int length;
	public boolean boundsFound = false;
	
	public absExponSearch(int[] items, int item) {
		super(items, item);
		length = items.length;
		index = 1;
		nextIndex = 1;
		// TODO Auto-generated constructor stub
	}

//	public static void main(String[] args) {
//		
//		
//		Scanner scan = new Scanner(System.in);
//		Object myVar = new Object();
//		
//		
//		
//		boolean gotIt = false;
//		
//		while(gotIt == false){
//		System.out.print("Please enter the size of your array: ");
//		myVar = scan.next();
//		Object scanned =  myVar;
//		
//		if(scanned instanceof Integer){
//		     int size = (Integer) scanned;
//		     gotIt = true;
//		     System.out.println(size);
//		   }
//		
//		}
//		
//		
//		/*
//		Object myVar = new Object();
//		
//		   myVar = scan.next();
//		   
//		   
//		  Object num = (Number) myVar;
//		   
//		   if(num instanceof Integer){
//		     System.out.print("true");
//		   }
//		   else{
//		     System.out.print("False");
//		   }
//		 } */
//		
//		
//		
//		
//		
//		int[] sortedArr = { 
//			    -900, -5, -1,
//			    0, 9, 36, 
//			    200, 289, 900, 10001
//			};
//		
//		ExponentialSearch drive = new ExponentialSearch();
//		
//		System.out.println(drive.expSearch(sortedArr, 10001));
//
//	}
	
	//could easily modify algorithm to take in as input whether the array is sorted in ascending or descending order and search accordingly
	//Pre: we assume the array sortedArr is sorted in ascending order
//	public int expSearch(int[] sortedArr, int thing){
//		
//		int length = sortedArr.length;
//		int upper = 1; //current upper bound of the part of the array we are searching in
//		
//		//For the purposes of this project we work with
//		//an array of finite size but this algorithm can be modified
//		//slightly to work with infinite lists
//		
//		while (upper < length && sortedArr[upper] < thing){
//			upper *= 2;
//		}
//		
//		upper = Math.min(upper, (length - 1)); 
//		
//		
//		
//		System.out.println(sortedArr + " " +  thing + " " + upper/2 + " " + upper);
//		return binSearch(sortedArr, thing, upper/2, upper);
//	}

	
	

	@Override
	public void getNextStep() {
		
		if(item > items[length - 1]){
			this.result = Result.NOTFOUND;
			return;
		}
		
		if (index < length){
		if(items[index] == item){
			this.result = Result.EQUAL;
		}
		}
			//int length = sortedArr.length;
			//int upper = 1; //current upper bound of the part of the array we are searching in
			
			//For the purposes of this project we work with
			//an array of finite size but this algorithm can be modified
			//slightly to work with infinite lists
			
		if(boundsFound == false){ //If we havent found our upper power of 2 bound
			
			
			if((index < length && items[index] < item)){ //if our current power of two is less than the length of the array and the item we are looking for is still greater than the item at the index of the current power of two
				this.result = Result.RIGHT;
				nextIndex *= 2;
			}
			else{
				this.result = Result.LEFT;
				boundsFound = true;
				//System.out.println("index: " + index + " length:" + length);
				right = Math.min(index, (length - 1));
				left = right/2;
				//System.out.println("Bounds found: Left: " + left + " Right:" + right);
				int midPoint = left + ((right - left)/2);
				
				nextIndex = midPoint; 
			}
		}	
		else{
				
				binSearch();
		}
			
			
			
			
//			while (upper < length && sortedArr[upper] < thing){
//				upper *= 2;
//			}
//			
//			upper = Math.min(upper, (length - 1)); 
//			
//			
//			
//			System.out.println(sortedArr + " " +  thing + " " + upper/2 + " " + upper);
//			return binSearch(sortedArr, thing, upper/2, upper);
		}
	
	public void binSearch(){
		//System.out.println("left:  " + left + " Right: " + right);
		
		//System.out.println("index:  " + index);
		
		
		
		
		if(items[index] == item){
			//System.out.println("The element you are searching for is located at index:" + index);
			//return right;
			result = Result.EQUAL;
			//index = midPoint;
		}
		else if(right == left && items[index] != item){
			//System.out.println("item is not an element of the array. The value returned is not actually the index of item");
			//return -1;
			result = Result.UNDEF;
			nextIndex = left + ((right - left)/2);
			
		}
		else if(item <= items[index]){
			//return binSearch(items, item, left, midPoint);
			right = index - 1;
			result = Result.LEFT;
			nextIndex = left + ((right - left)/2);
		}
		else if(item >= items[index]){
			//System.out.println();
			//return binSearch(items, item, midPoint, right);
			left = index + 1;
			result = Result.RIGHT;
			nextIndex = left + ((right - left)/2);
		}
		else{
			//System.out.println("Something went wrong");
			//return -1;
			nextIndex = left + ((right - left)/2);
		}
		
		
	}
	public Object[] getRow()
	{
		return new Object[] { steps, index, result.toString(), left, right};
	}
}
		
	

