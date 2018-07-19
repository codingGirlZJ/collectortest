package javaprogrammer;

import java.util.StringTokenizer;

public class testStringAndStringBuffer {

	public static void testString(){
		String s = "Hello";
		String s1 = "world";
		long start = System.currentTimeMillis();
		for(int i = 0; i < 10000; i++){
			s += s1;
		}
		long end = System.currentTimeMillis();
		long runTime = (end - start);
		System.out.println("testString:" + runTime);
	}
	
	public static void testStringBuffer(){
		StringBuffer s= new StringBuffer("Hello");
		String s1 = "Wordl";
		long start = System.currentTimeMillis();
		for(int i = 0; i < 10000; i++){
			s.append(s1);
		}
		long end = System.currentTimeMillis();
		long runTime = (end - start);
		System.out.println("testStringBuffer:" + runTime);
	}
	
	public static void main(String[] args){
		testString();
		testStringBuffer();
		StringTokenizer st = new StringTokenizer("A-B C-D","-");
		while(st.hasMoreTokens()){
			System.out.println(st.nextToken());
		}
	}
}
