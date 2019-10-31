package com.fh.shop.admin.util;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Properties;

public class Md5Util {
	public final static String md5(String s){
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			System.out.println();
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// 将没个数(int)b进行双字节加密
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	static int i = 0;
	public static int getFactorial(int n) {
		if (n >= 0) {
			if (n == 0) {
				System.out.println(n + "!=1");
				return 1;
			} else {
				System.out.println(n);
				int temp = n * getFactorial(n - 1);
				System.out.println(n + "!=" + temp);
				//i +=temp;
				return temp;
			}
		}
		return -1;
	}

	public static String buildMd5Password(String salt, String password) {
		return md5(md5(password)+ salt);
	}


	public static void main(String[] args) throws Exception {

	}


	/*public static int fib(int n){
		if ((n == 0) || (n == 1))
			return n;
		else
			return fib(n - 1) + fib(n - 2);
	}

	public static int  move(int a) {
		if(a>=0){
			//如果是最后一次 不返回1的话 *0 归0
			if(a==0){
				return 1;
			}else{
				int temp = a * move(a-1);
				System.out.println(temp);
				return temp;
			}
		}
		return -1;
	}*/


}
