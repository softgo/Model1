package com.bruce.gogo.utils;

import java.io.*;
import java.util.*;
import java.net.InetAddress;
import java.util.Properties;
import java.util.Date;
import java.util.Vector;

import javax.mail.*;
import javax.mail.internet.*;

import com.sun.mail.smtp.*;

/** 
 * @author zhoulifeng
 *
 */
public class Email {
	
	private	String  to, subject = null, from = null, 
	cc = null, bcc = null, url = null;
	private String mailhost = null;
	private String mailer = "smtpsend";
	private String file = null;
	private String protocol = null, host = null, user = null, password = null;
	private String record = null;	// name of folder in which to record mail
	private boolean debug = false;
	private boolean verbose = false;
	private boolean auth = false;
	private boolean ssl = false;
	private BufferedReader in =	new BufferedReader(new InputStreamReader(System.in));
	private int optind = 0 ;


	
  /***
   * 连接SMTP服务器并发送邮件。
   * <p>
   * @param subject 邮件标题
   * @param msgtxt 邮件内容
   * @param to 收件人邮件地址
   * @exception IOException 失败时抛出异常
   */
	  public boolean sendMailTo(String subject,String msgtxt,String to)
	  throws Exception{
	    boolean retval = false;
	    
	    try{
		Properties prop = new Properties();
		InputStream fis =  getClass().getClassLoader().getResourceAsStream("mail.properties");
		prop.load(fis);
		host = prop.getProperty("host");
		user = prop.getProperty("user");
		password = prop.getProperty("password");
		mailhost = prop.getProperty("mailhost");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
		
		Properties props = System.getProperties();
		if (mailhost != null)
		props.put("mail.smtp.host", mailhost);
		if (auth)
		props.put("mail.smtp.auth", "true");
		
		// Get a Session object
		Session session = Session.getInstance(props, null);
		if (debug)
		session.setDebug(true);
		
		// construct the message
		Message msg = new MimeMessage(session);
		if (from != null)
		msg.setFrom(new InternetAddress(from));
		else
		msg.setFrom();
		
		msg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to, false));
		if (cc != null)
		msg.setRecipients(Message.RecipientType.CC,
				InternetAddress.parse(cc, false));
		if (bcc != null)
		msg.setRecipients(Message.RecipientType.BCC,
				InternetAddress.parse(bcc, false));
		
		msg.setSubject(subject);
		 
		//String text = collect(in);
		String text = msgtxt;
		
		
		if (file != null) {
		// Attach the specified file.
		// We need a multipart message to hold the attachment.
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setText(text);
		MimeBodyPart mbp2 = new MimeBodyPart();
		//mbp2.attachFile(file);
		MimeMultipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		mp.addBodyPart(mbp2);
		msg.setContent(mp);
		} else {
		// If the desired charset is known, you can use
		// setText(text, charset)
		msg.setText(text);
		}
		
		msg.setHeader("X-Mailer", mailer);
		msg.setSentDate(new Date());
		
		// send the thing off
		/*
		* The simple way to send a message is this:
		*
		Transport.send(msg);
		*
		* But we're going to use some SMTP-specific features for
		* demonstration purposes so we need to manage the Transport
		* object explicitly.
		*/
		SMTPTransport t =
		(SMTPTransport)session.getTransport(ssl ? "smtps" : "smtp");
		try {
		if (auth)
		t.connect(mailhost, user, password);
		else
		t.connect();
		t.sendMessage(msg, msg.getAllRecipients());
		} finally {
		if (verbose) 
		//System.out.println("Response: " +
		//			t.getLastServerResponse());
		t.close();
		}
		
		System.out.println("\n邮件发送成功！");
		retval = true;
		// Keep a copy, if requested.
		
		if (record != null) {
		// Get a Store object
		Store store = null;
		if (url != null) {
		URLName urln = new URLName(url);
		store = session.getStore(urln);
		store.connect();
		} else {
		if (protocol != null)		
		store = session.getStore(protocol);
		else
		store = session.getStore();
		
		// Connect
		if (host != null || user != null || password != null)
		store.connect(host, user, password);
		else
		store.connect();
		}
		
		// Get record Folder.  Create if it does not exist.
		Folder folder = store.getFolder(record);
		if (folder == null) {
		System.err.println("Can't get record folder.");
		System.exit(1);
		}
		if (!folder.exists())
		folder.create(Folder.HOLDS_MESSAGES);
		
		Message[] msgs = new Message[1];
		msgs[0] = msg;
		folder.appendMessages(msgs);
		retval = true;
		System.out.println("\n邮件发送成功！");
		}
		
		} catch (Exception e) {
		if (e instanceof SendFailedException) {
		MessagingException sfe = (MessagingException)e;
		if (sfe instanceof SMTPSendFailedException) {
		SMTPSendFailedException ssfe =
			    (SMTPSendFailedException)sfe;
		System.out.println("SMTP SEND FAILED:");
		if (verbose)
		System.out.println(ssfe.toString());
		System.out.println("  Command: " + ssfe.getCommand());
		System.out.println("  RetCode: " + ssfe.getReturnCode());
		System.out.println("  Response: " + ssfe.getMessage());
		} else {
		if (verbose)
		System.out.println("Send failed: " + sfe.toString());
		}
		Exception ne;
		while ((ne = sfe.getNextException()) != null &&
		ne instanceof MessagingException) {
		sfe = (MessagingException)ne;
		if (sfe instanceof SMTPAddressFailedException) {
		SMTPAddressFailedException ssfe =
				(SMTPAddressFailedException)sfe;
		System.out.println("ADDRESS FAILED:");
		if (verbose)
		    System.out.println(ssfe.toString());
		System.out.println("  Address: " + ssfe.getAddress());
		System.out.println("  Command: " + ssfe.getCommand());
		System.out.println("  RetCode: " + ssfe.getReturnCode());
		System.out.println("  Response: " + ssfe.getMessage());
		} else if (sfe instanceof SMTPAddressSucceededException) {
		System.out.println("ADDRESS SUCCEEDED:");
		SMTPAddressSucceededException ssfe =
				(SMTPAddressSucceededException)sfe;
		if (verbose)
		    System.out.println(ssfe.toString());
		System.out.println("  Address: " + ssfe.getAddress());
		System.out.println("  Command: " + ssfe.getCommand());
		System.out.println("  RetCode: " + ssfe.getReturnCode());
		System.out.println("  Response: " + ssfe.getMessage());
		}
		}
		} else {
		System.out.println("Got Exception: " + e);
		if (verbose)
		e.printStackTrace();
		}
		}


	    return retval;
	  }
	



}
