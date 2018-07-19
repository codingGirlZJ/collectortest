package sfjsrmjd;

public class test {

	public static void main(String[] args) {
		double n = cal(41);
		System.out.println(n%1000000);
		double i;
		for(i = 0; i != 10; i += 0.1){
			System.out.println(i);
			if(i > 11){
				break;
			}
		}
	}
	
	public static double cal(int n){
		double s = 0;
		
		for(int i = 1; i <= n; i++){
			double temp = 1;
			for(int j = 1; j <= i; j++){
				temp *= j;
			}
			s += temp;
		}
		
		return s;
	}
}
