package com.my.youtube.utils;
/**
 * 数据样例：
 * SDNkMu8ZT68	w00dy911	630	People & Blogs	186	10181	3.49	494	257	rjnbgpPJUks
 * 1、过滤不合法数据
 * 2、去掉&符号左右两边的空格
 * 3、 \t 换成&符号
 * @author yigai
 *
 */
public class ETLUtil {

	public static String getETLString(String ori) {
		//1、按制表符（\t）切分数据
		String[] splits = ori.split("\t");
		
		//2、过滤不合法的数据
		if(splits.length < 9)
			return null;
		
		//3、第四个数据去掉&符号左右两边的空格
		splits[3] = splits[3].replaceAll(" ", "");
		
		//4、整理数据：添加 \t 、&符号
			//创建操作容器
		StringBuilder sb = new StringBuilder();
			//遍历数据
		for(int i = 0; i < splits.length; i++) {
			sb.append(splits[i]);
			if(i < 9) {//前九条数据添加制表符
				if(i != splits.length - 1) {
					sb.append("\t");
				}
			}else {//后面的数据用&相连
				if(i != splits.length - 1) {//最后一个数据后不操作
					sb.append("&");
				}
			}
		}
		//5、返回处理后的数据
		return sb.toString();
	}
	/*测试
	 * public static void main(String[] args) {
		String str ="SDNkMu8ZT68	w00dy911	630	People & Blogs	186	10181	3.49	494	257	rjnbgpPJUks	1	2	3	4";
		System.out.println(getETLString(str));
	}*/
} 
