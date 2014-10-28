package com.bruce.gogo.crypt;

/**
 *加密算法的接口类
 * Copyright (c) 
 * @version $id$
 */
public interface ICrypt {
	/**
	 * the crypt algorithm's name
	 */
	public String CRYPT_NAME = "UnixCrypt";

	/**
	 * Encrypt the given word and return the encrypted result.
	 * @param original The word you want to be encrypted.
	 * @return Encrypted word of original
	 */
	public String crypt(String original);

	/**
	 * Used to judge if the original word is matched with encrypted word.
	 * @param encryptedWord A encrypted word.
	 * @param Original The original word which you want to judge.
	 * @return Judged result,if match return true ,else return false.
	 */
	public boolean matches(String encryptedWord, String Original);
	public long getMd5ID(String original);
}