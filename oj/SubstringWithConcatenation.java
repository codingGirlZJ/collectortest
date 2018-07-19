package oj;

import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubstringWithConcatenation {

	public static void main(String[] args) {
//		Pattern pattern = Pattern.compile("aa");  
//		Matcher matcher = pattern.matcher("aaaa");
//		while (matcher.find()) {  
//		    System.out.println(matcher.start()+" ");  
//		}
		int time= 0;//秒
        GregorianCalendar gc = new GregorianCalendar(); 
        gc.setTimeInMillis(time * 1000);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.print( format.format(gc.getTime()));
	}
}
