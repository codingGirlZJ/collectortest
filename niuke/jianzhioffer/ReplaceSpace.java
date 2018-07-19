package niuke.jianzhioffer;
/**
 * 空格替换：
 * 请实现一个函数，将一个字符串中的空格替换成“%20”。
 * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 * @author zj
 *
 */
public class ReplaceSpace {

	public static void main(String[] args) {
		String s = replaceSpace(new StringBuffer(" We Are Happy "));
		System.out.println(s);
	}
	
	/**
	 * 先计算有多少个空格，算出替换后的字符串长度
	 * 再从后往前依次将原字符串字符放到指定位置，遇到空格则依次放入0、2、%三个字符
	 * @param str
	 * @return
	 */
	public static String replaceSpace(StringBuffer str){
		int spaceNum = 0;
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) == ' '){
				spaceNum++;
			}
		}
		int newLen = str.length() + 2*spaceNum;
		int indexOld = str.length()-1;
		int indexNew = newLen-1;
		str.setLength(newLen);
		for(; indexOld >= 0; indexOld--){
			if(str.charAt(indexOld) == ' '){
				str.setCharAt(indexNew--, '0');
				str.setCharAt(indexNew--, '2');
				str.setCharAt(indexNew--, '%');
			}else{
				str.setCharAt(indexNew--, str.charAt(indexOld));
			}
		}
		
		return str.toString();
	}
}
