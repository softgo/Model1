package com.bruce.gogo.crypt.impl;

import com.bruce.gogo.crypt.ICrypt;
import com.bruce.gogo.crypt.UnixCrypt;
import com.bruce.gogo.utils.DataConvert;


/**
 * <p>
 * Title: UnixCryptImp
 * </p>
 * <p>
 * Description: Use it to call UnixCrypt class.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class UnixCryptImp implements ICrypt {
	public ICrypt newInstance() {
		return new UnixCryptImp();
	}

	public UnixCryptImp() {
	}

	public static void main(String[] args) {
		UnixCryptImp unixCryptImp1 = new UnixCryptImp();
		unixCryptImp1.matches("", "testtest");
	}

	public String crypt(String pwd) {
		return UnixCrypt.crypt(pwd);
	}

	public boolean matches(String encryptedWord, String original) {
		return UnixCrypt.matches(encryptedWord, original);
	}

	public long getMd5ID(String original) {
		return DataConvert.bytesToLong((UnixCrypt.crypt(original)).getBytes());
	}

}