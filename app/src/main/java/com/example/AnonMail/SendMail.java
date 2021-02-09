package com.example.AnonMail;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail extends AsyncTask<Void, Void, Void>
{

    String fromName,fromEmail,toEmail,subject,smmessage;
    private Context mContext;
    public SendMail(String fromName, String fromEmail, String toEmail, String subject, String smmessage)
    {
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        this.toEmail = toEmail;
        this.subject = subject;
        this.smmessage = smmessage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //this method will be running on UI thread

    }
    @Override
    protected Void doInBackground(Void... params)
    {

        //this method will be running on background thread so don't update UI frome here
        //do your long running http tasks here,you /dont want to pass argument and u can access the parent class' variable url over here
        final String username = ""; //your smtp username here
        final String password = ""; //your smtp password here
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.smtp2go.com");
        props.put("mail.smtp.port", "2525"); // 8025, 587 and 25 can also be used.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator()
                {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            Multipart mp = new MimeMultipart("alternative");
            BodyPart textmessage = new MimeBodyPart();
            textmessage.setText(smmessage);
            //BodyPart htmlmessage = new MimeBodyPart();
            //htmlmessage.setContent("It is a html message.", "text/html");
            mp.addBodyPart(textmessage);
            //mp.addBodyPart(htmlmessage);
            message.setFrom(new InternetAddress(fromName +  "<"+fromEmail+">"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(mp);
            Transport.send(message);
            Log.d("Email", "Sent");
        }
        catch (MessagingException e)
        {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);

        //this method will be running on UI thread
        Toast.makeText(mContext, "Email Succesfully Sent...", Toast.LENGTH_SHORT).show();



    }

}


