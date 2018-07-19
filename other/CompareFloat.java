package other;

/**
 * Java中判断两个float类型数据是否相等
 * 相减，然后判断这个商是否小于某个值，这个值是你自己定义的，比如0.000001什么的，如果小于就相等，否则不等
 * @author ZJ
 *
 */
public class CompareFloat {

	 public static void main(String[] args){
	        isEqualOfFloat(10.222222225f, 10.222222229f);
	    }
	    
	    public static void isEqualOfFloat(float a, float b){
	        System.out.println("-------使用==符号比较是否相等：" + (a==b));
	        System.out.println("-------使用Math.abs()方法比较是否相等：" + (Math.abs(a-b)>0) );
	        System.out.println("-------使用Math.abs()方法比较是否相等(比较宽容的限制)：" + (Math.abs(a-b)<0.00000001) );
	        System.out.println("-------比较大小<：" + (a<b));
	        System.out.println("-------比较大小>：" + (a>b));
	    }
}
