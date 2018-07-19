package other;
/**
 * 求1000以内的质数
 * 	单独打印2，然后双重循环：首先外层循环，从3开始依次递增2，因为所有的偶数必然不是质数，可以省去一半计算；内层依次除从3到外层的开方，若有除尽的则不是质数
 * @author ZJ
 *
 */
public class PrimeNumber {

	public static void main(String[] args){
		System.out.print("2,");
		for(int i = 3; i < 1000; i += 2){							//除2以外，所有的偶数都不是质数，所以外层循环可以直接递增2
			int flag = 1;											//标志位，如果一次循环结束还为1，那么该数就是质数
			for(int j = 3; j < Math.ceil(Math.sqrt(i)); j ++){		//不用一直除到i-1，只需除到i的开方即可
				if(i % j == 0){
					flag = 0;
					break;
				}
			}
			if(flag == 1){
				System.out.print(i + ",");
			}
		}
	}
}
