package oj.leetcode.problem_039_combination_sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 组合求和
 * 给定一个正整数数组和一个目标，求这个数组中相加等于目标的组合（数组元素可重复使用，但结果不能有重复）
 * @author ZJ
 *
 */
public class demo1 {
	
	public static void main(String[] args) {
		int[] candidates = {4,5,2};
		int target = 16;
		List<List<Integer>> result = combinationSum(candidates, target);
		for(List<Integer> l : result){
			System.out.println("*****");
			printList(l);
		}
	}
	
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Arrays.sort(candidates);
        sum(candidates, target, result, new ArrayList<Integer>());
        return result;
    }
    
    public static void sum(int[] candidates, int target, List<List<Integer>> result, List<Integer> list){
        List<Integer> l = new ArrayList<Integer>();
        l.addAll(list);
        for(int i = 0; i < candidates.length; i++){
            if((target - candidates[i]) > 0){
                 System.out.println(target + "减" + candidates[i] + "大于0");
                l.add(candidates[i]);
                sum(candidates, target - candidates[i], result,l);
                System.out.println("remove: " + l.get(l.size()-1));
                l.remove(l.size() - 1);
            }else if((target - candidates[i]) == 0){
                l.add(candidates[i]);
//                List<Integer> l1 = new ArrayList<Integer>();
//                l1.addAll(l);
                Collections.sort(l);
                if(!result.contains(l)){
                    System.out.print(target + "add: ");
                    printList(l);
                    result.add(l);
                }
//                l = l1;
//                l.remove(l.size()-1);
//                这里l添加到结果后，后面根本不会再有不重复且符合的结果了（数组事先已排序），所以直接返回就好了
//                （这里的l根本不会影响到上一层的list，所以没必要在移除最后添加的元素了，移除的目的是为了寻找本次迭代，除这个元素以外的其他结果，
//                如果最开始数组未排序，则后面可能有比当前元素小的元素，即可能又其他符合的结果，需移除当前元素，继续寻找下一个可能结果，但如果事先排了序，就不需移除了）
                return ;
            }else{
                System.out.println(target + "减" + candidates[i] + "小于0");
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