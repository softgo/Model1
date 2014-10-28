package com.bruce.gogo.crypt.impl;

import com.bruce.gogo.crypt.ICrypt;

public class Test {
	
	public static void main(String[] args) {
		
		ICrypt md = new MD5Imp();
		String word = "1_2_abc";
		System.out.println(md.crypt(word));
		System.out.println(Short.MAX_VALUE);
	}
}
