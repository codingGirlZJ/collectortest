package oj.leetcode.problem_040_combination_midium2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 组合求和
 * 给定一个正整数数组和一个目标，求这个数组中相加等于目标的组合（数组元素不可重复使用，结果也不能有重复）
 * @author ZJ
 *
 */
public class demo1 {
	
	public static void main(String[] args) {
		int[] candidates = {10,1,2,7,6,1,5};
		int target = 8;
		List<List<Integer>> result = combinationSum(candidates, target);
		for(List<Integer> l : result){
			System.out.println("*****");
			printList(l);
		}
	}
	
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Arrays.sort(candidates);	//{1,1,2,5,6,7,10} 事先将数组排序，节省后面不必要的比较时间
        sum(candidates, target, result,0, new ArrayList<Integer>());
        return result;
    }
    
    public static void sum(int[] candidates, int target, List<List<Integer>> result, int start, List<Integer> list){
        List<Integer> l = new ArrayList<Integer>();	//每次都新建了个list，所以在本次迭代里对l的修改，根本不会影响到上一层的list
        l.addAll(list);
        for(int i = start; i < candidates.length; i++){
            if((target - candidates[i]) > 0){
                 System.out.println(target + "减" + candidates[i] + "大于0");
                l.add(candidates[i]);
                sum(candidates, target - candidates[i], result, i+1, l);
                System.out.println("remove: " + l.get(l.size()-1));
                l.remove(l.size() - 1);
            }else if((target - candidates[i]) == 0){
                l.add(candidates[i]);
                Collections.sort(l);
                if(!result.contains(l)){
                    System.out.print(target + "add: ");
                    printList(l);
                    result.add(l);
                }
                //这里l添加到结果后，后面根本不会再有不重复且符合的结果了（数组事先已排序），所以直接返回就好了
                //（这里的l根本不会影响到上一层的list，所以没必要在移除最后添加的元素了，移除的目的是为了寻找本次迭代，除这个元素以外的其他结果，如果实现排了序，就不需移除了）
                return ;	
            }else{
            	System.out.println(target + "减" + candidates[i] + "小于0");
            	break;
            }
        }
        return ;
    }
    
    public static void printList(List<Integer> list){
    	System.out.print("[");
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i) + ",");
        }
        System.out.print("]");
        System.out.println();
    }
}
