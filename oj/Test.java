package oj;

public class Test{
	public static void main(String[] args) {
		Super s = new Sub();
		System.out.println(s.f());
	}
}

class Super{
	public int f(){
		return 1;
	}
}

class Sub extends Super{
	
}
