/**
 * 
 */
package com.junge.demo.randomstr;

import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;

/**
 * @author liuxj
 *
 */
public class RandomStringTest {
	
	public static String md5(String source) {  
        
	    StringBuffer sb = new StringBuffer(32);  
	          
	    try {  
	        MessageDigest md = MessageDigest.getInstance("MD5");  
	        byte[] array = md.digest(source.getBytes("utf-8"));  
	              
	        for (int i = 0; i < array.length; i++) {  
	            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));  
	        }  
	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	    return sb.toString();
	}
	
	public static void main(String[] args) {
		// 使用字母a-z，生成20个code point(维基百科称之为'码位')的随机字符串
		RandomStringGenerator generator1 = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		String randomLetters = generator1.generate(20);
		System.out.println(StringUtils.center("随机字母字符串", 20, "="));
		System.out.println(randomLetters);

		// 使用数字0-9，生成20个code point(维基百科称之为'码位')的随机字符串
		RandomStringGenerator generator2 = new RandomStringGenerator.Builder().withinRange('0', '9').build();
		String randomNumbers = generator2.generate(8);
		System.out.println(StringUtils.center("随机数字字符串", 20, "="));
		System.out.println(randomNumbers);

		// 使用码位为0到z的字符，生成20个code point(维基百科称之为'码位')的随机字符串
		RandomStringGenerator generator3 = new RandomStringGenerator.Builder().withinRange('0', 'z').build();
		String random = generator3.generate(20);
		System.out.println(StringUtils.center("随机混合字符串", 20, "="));
		System.out.println(random);
		
		//ruuodbwjdcanzpgptlon
		System.out.println("md5:" + md5("ruuodbwjdcanzpgptlon").toLowerCase());
		
	}

}
