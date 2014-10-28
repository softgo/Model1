package com.bruce.gogo.crypt;

/**
 * 加密算法工厂 
 * 
 * @version $id$
 */
public class CryptFactory {
	
	private static String PACKEG = "com.bruce.gogo.crypt.impl.";

	public static ICrypt getCryptor(String algorithmName) throws Exception {
		try {
			ICrypt crypt = (ICrypt) Class.forName(PACKEG + algorithmName + "Imp").newInstance();
			return crypt;
		} catch (Exception e) {
			System.out.println("加密出问题了.");
			return null;
		}
	}
}