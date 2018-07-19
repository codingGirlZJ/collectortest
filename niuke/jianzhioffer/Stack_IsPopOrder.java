package niuke.jianzhioffer;

import java.util.Collection;
import java.util.Stack;

/**
 * 求出栈序列是否正确
 * @author zj
 *
 */
public class Stack_IsPopOrder {
	
	public static void main(String[] args) {
		int[] pushA = {1,2,3,4,5};
		int[] popA = {3,5,4,1,2};
		System.out.println(IsPopOrder(pushA, popA));
	}

	/**
	 * 将出栈序列保存到临时栈中，将入栈序列依次入栈
	 * 每入栈一个元素都与临时栈中栈顶元素比较，若相等，则两个栈中都出栈该元素，若下一个元素还相等，再出栈，直至两个栈栈顶元素不等，
	 * 继续入栈，直到所有入栈序列入栈完毕
	 * 若该出栈序列正确，那么入栈完毕两个栈中元素也应该出栈完毕，未出栈完就说明出栈序列错误
	 * @param pushA
	 * @param popA
	 * @return
	 */
	public static boolean IsPopOrder(int [] pushA,int [] popA) {
        Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();
        for(int i = popA.length-1; i >= 0; i--){
            stack2.push(popA[i]);
        }
        for(int i = 0; i < pushA.length; i++){
            stack1.push(pushA[i]);
            System.out.println(pushA[i]);
            while(!stack1.isEmpty() && !stack2.isEmpty() && stack1.peek() == stack2.peek()){
                stack1.pop();
                stack2.pop();
            }
        }
        if(!stack1.isEmpty() || !stack2.isEmpty()){
            return false;
        }
        return true;
    }
}
