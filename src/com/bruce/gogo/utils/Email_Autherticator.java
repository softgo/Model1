package com.bruce.gogo.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Email_Autherticator extends Authenticator {
	public Email_Autherticator() 
	{ 
	super(); 
	} 
	public PasswordAuthentication getPasswordAuthentication() 
	{ 
	return new PasswordAuthentication("yitingyu_333","5201314653"); 
	} 

}
