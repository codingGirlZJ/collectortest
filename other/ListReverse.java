package other;


/**
 * 链表逆序
 * @author ZJ
 *
 */
public class ListReverse {

	public static void main(String[] args) {
		int[] a = {1,2,3,4,5,6,7,8,9};
		MyList myList = initMyList(a);
		printMyList(myList);
		MyList reverseList = reverseMyList(myList);
		printMyList(reverseList);
	}
	
	/**
	 * 递归实现链表逆序
	 * @param myList
	 * @return
	 */
	public static MyList reverseMyList(MyList myList){
		if(myList != null){								//如果链表不为空，为空直接返回null
			if(myList.next != null){					//如果链表不止一个元素，只有一个元素直接返回链表
				MyList p = reverseMyList(myList.next);	//求从第2个元素到最后的逆序，返回的是逆序后的头节点，即原来的最后一个节点
//				MyList p1 = p;
//				while(p1.next != null){					//求逆序后的最后一个节点
//					p1 = p1.next;
//				}
//				p1.next = myList;						//将其的next指向当前节点，实现后一个节点指向前一个节点，逆序
				myList.next.next = myList;	//我最初用上面注释部分，与这一句作用相同，逆序找到的元素就是当前元素的下一个元素，上面的方法简单问题复杂了
				myList.next = null;						//将当前节点的next置null，便于之后找到逆序到哪个节点了
				return p;								//返回p，逆序后的第一个元素节点，每次迭代返回的其实是同一个p
			}else{
				return myList;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 利用数组初始化自定义链表
	 * @param a
	 * @return
	 */
	public static MyList initMyList(int[] a){
		MyList head = null;
		if(a.length > 0){
			head = new MyList(a[0], null);
		}else{
			return null;
		}
		MyList p = head;
		for(int i = 1; i < a.length; i++){
			MyList newNode = new MyList(a[i], null);
			p.next = newNode;
			p = p.next;
		}
		return head;
	}
	
	/**
	 * 打印自定义链表
	 * @param myList
	 */
	public static void printMyList(MyList myList){
		MyList p = myList;
		while(p != null){
			System.out.print(p.data + ",");
			p = p.next;
		}
		System.out.println();
	}
}
