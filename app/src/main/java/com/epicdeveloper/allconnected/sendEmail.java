package com.epicdeveloper.allconnected;

import android.os.StrictMode;

import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendEmail {
    static String mailSession;
     static String passwordSession;
     static Session session;
     static String selectedLang;
     static String support;

    public static void sendEmailMessage(String recipient, String subject, String messageSent){
        mailSession="allconnected.appweb@epicdevelopers.es";
        selectedLang = newuser.selectedLang;
        passwordSession= "@@Drcr1989@@";
        final Properties prop=new Properties();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        prop.put("mail.smtp.host", "smtp.ionos.es");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enabled", true);
        prop.put("mail.smtp.socketFactory.port", "587");

        try{
            session=Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailSession, passwordSession);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(getSupport(selectedLang)+"<Support@epicdevelopers.app>"));
            message.setSubject(subject);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setContent(messageSent,"text/html; charset=UTF-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String getSupport(String lang){
        if (lang.equals("ES")){
            support = "Soporte allConnected";
        }else{
            support = "allConnected Support";
        }
        return support;
    }

}
