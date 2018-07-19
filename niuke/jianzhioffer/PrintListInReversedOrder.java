package niuke.jianzhioffer;

import java.util.ArrayList;

/**
 * 从尾到头打印链表
 * 输入一个链表，从尾到头打印链表每个节点的值。
 * @author zj
 *
 */
public class PrintListInReversedOrder {
	
	public static void main(String[] args) {
		printListFromTailToHead(null);
	}

	public static ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
		ListNode p = listNode;
		ArrayList<Integer> list = new ArrayList<>();
		if(listNode == null){
			return list;
		}
		while(p.next != null){
			list.add(0, p.val);	    //每次都将元素放到第0个位置，头插法
			p = p.next;
		}
		list.add(0, p.val);
		
		for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i));
		}
		
		return list;
    }
}

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}
