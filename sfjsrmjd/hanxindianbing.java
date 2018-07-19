package sfjsrmjd;

/**
 * 韩信点兵
 * @author ZJ
 *
 */
public class hanxindianbing {

	public static void main(String[] args) {
		int n = cal(2, 1, 6);
		if(n == -1){
			System.out.println("No answer");
		}else{
			System.out.println(n);
		}
	}
	
	public static int cal(int a, int b, int c){
		int sum = -1;
		for(int i = 10; i <= 100; i++){
			if(i%3==a && i%5==b && i%7==c){
				sum = i;
				break;
			}
		}
		return sum;
	}
}
