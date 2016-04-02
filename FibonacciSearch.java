//reference: http://www.cs.utsa.edu/~wagner/CS3343/binsearch/fibsearch.html
//Author: nabackert@smcm.edu
public class FibonacciSearch {
	int[] search = {1,2,3,5,7,10,16,19,23,27,32,40};
	int item = 17;
	public int[] fib(int item){
		int[]temp = new int[0];
		if(item==0){return temp;}
		int[] f = new int[item];
		f[0]=0;
		f[1]=1;

		for(int i =0;i<item;i++){
			f[i]=f[i-1]+f[i-2];


		}
		return f;
	}
//reference: http://www.cs.utsa.edu/~wagner/CS3343/binsearch/fibsearch.html
	public int fibSearch(int[]search,int item,int[]f){
		int k = 0;
		//finds the closest fibonacci number 
		for(int i=0;i<f.length;i++){
			if(item<f[i]){
				k=f[i];
			}
		}	
		if (k==0){
			System.out.println("Item not in array.");
		}
		int m = f[item-1];
		int mid = f[m]; // F[m] is partway from 0 to F[m+1]
		   int p   = f[m-1] = f[m] - f[m-1];
		   int q   = f[m-2] = 2*f[m-1] - f[m];
		   for (;;) {
		   if(item==mid){
			   return mid;
		   } else if(item<mid){
			   if (q == 0) return -(mid - 1);
		         mid = mid - q;
		         int temp = p;
		         p = q;
		         q = temp - q;
		      }
		      else if (item > mid) {
		         if (p == 1) return -mid;
		         mid = mid + q;
		         p = p - q;
		         q = q - p;
		      }
		   }
		} 
			   
		   }
	
		

