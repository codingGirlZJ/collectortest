package niuke.jianzhioffer;

import java.util.ArrayList;

/**
 * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 * 例如
 * 	输入数组	{3，32，321}
 * 	则打印出这三个数字能排成的最小数字为	321323
 * @author zj
 *
 */
public class PrintMinNumber {
    protected static String result = "";
    protected static String curStr = "";
    
    public static void main(String[] args) {
		int[] numbers = {3, 5, 1, 4, 2};
		String r = printMinNumber(numbers);
		System.out.println(r);
	}
    
    public static String printMinNumber(int [] numbers) {
        if(numbers.length == 0){
            return result;
        }
        int[] visited = new int[numbers.length];
        permutation(numbers, visited, 0);
        
        return result;
    }
    
    /**
     * 求数组元素的全排列
     * @param numbers	数组
     * @param visited	数组元素对应的是否已被访问标志数组
     * @param len		当前已访问的长度（等于数组长度时说明形成一个全排列）
     */
    public static void permutation(int[] numbers, int[] visited, int len){
        for(int i = 0; i < numbers.length; i++){				//循环数组，找未被访问的
            if(visited[i] == 0){
                String tmp = String.valueOf(numbers[i]);
                curStr += tmp;									//将未被访问的元素加到当前字符串后面
                len++;											//已被访问的元素个数+1
                visited[i] = 1;									//更新访问标志位
                if(len == numbers.length){						//已全部被访问，即形成一个全排列了
                    if(result.equals("")){						//是第一个全排列
                        result = curStr;
                    }else if(!compare(curStr, result)){			//不是第一个全排列，则比较，将小的那个放到result中
                        result = curStr;
                    }
                }else{											//还没形成全排列
                    permutation(numbers, visited, len);			//继续递归放下一个元素
                }
                len--;											//下面三步将该元素归位，以便后续全排列用到
                visited[i] = 0;
                curStr = curStr.substring(0, curStr.length() - tmp.length());
            }
        }
    }
    
    /**
     * 比较两个字符串数字的大小
     * （按依次比较相应位上的字典序）
     * @param a
     * @param b
     * @return
     */
    public static boolean compare(String a, String b){    //a>=b返回true
        boolean flag = true;
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) < b.charAt(i)){					//相同位上a<b说明a小于b
                flag = false;
                break;
            }else if(a.charAt(i) > b.charAt(i)){			//相同位上a>b说明a大于b
            	break;
            }
            //否则相同位上相等，继续比较下一位
        }
//        System.out.println(a + "-" + b + " : " + flag);
        return flag;
    }
}