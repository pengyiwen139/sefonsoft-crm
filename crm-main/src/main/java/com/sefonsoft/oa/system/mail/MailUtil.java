package com.sefonsoft.oa.system.mail;

import com.google.common.collect.ImmutableList;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

/**
 * @author xielf
 */
public class MailUtil {

  private static final String FROM_EMAIL = "crmoa@sefonsoft.com";
  private static final String EMAIL_PASSWORD = "Sf1234";
  private static final String STMP_QQ_COM = "smtp.exmail.qq.com";
  private static final String PERSONAL = "CRM系统";
  private static final String CHARSET = "UTF-8";

  private static Properties PROPS;

  static {
    PROPS = new Properties();
    PROPS.setProperty("mail.smtp.host", STMP_QQ_COM);
    PROPS.setProperty("mail.transport.protocol", "smtp");
    PROPS.setProperty("mail.smtp.auth", "true");
  }

  private static Message createMessage(Session session, List<String> toEmails, String title, String content) throws MessagingException, UnsupportedEncodingException {

    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(FROM_EMAIL, PERSONAL, CHARSET));
    for (String toEmail : toEmails) {
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
    }
    message.setSubject(title);

    // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
    Multipart multipart = new MimeMultipart();

    // 设置邮件的文本内容
    BodyPart contentPart = new MimeBodyPart();
    contentPart.setContent(content, "text/html;charset=UTF-8");
    multipart.addBodyPart(contentPart);

    /*// 添加附件
    BodyPart messageBodyPart = new MimeBodyPart();
    //attachment
    DataSource source = new FileDataSource("E://aa.txt");
    messageBodyPart.setDataHandler(new DataHandler(source));
    // 添加附件的标题
    sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
    messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachName.getBytes()) + "?=");
    multipart.addBodyPart(messageBodyPart);*/

    // 将multipart对象放到message中
    message.setContent(multipart);
    // 保存邮件
    message.saveChanges();

    return message;

  }

  /**
   * xielf
   *
   * @param sendToEmail 需要发送至的邮箱集合
   * @param subject     主题,即title
   * @param content     邮件的内容，html格式的
   * @throws MessagingException
   */
  public static void sendEmail(List<String> sendToEmail, String subject, String content) throws MessagingException {


    Session session = Session.getDefaultInstance(PROPS);
    session.setDebug(false);
    Transport transport = null;
    try {
      transport = session.getTransport();
      transport.connect(STMP_QQ_COM, FROM_EMAIL, EMAIL_PASSWORD);
      final Message message = createMessage(session, sendToEmail, subject, content);
      transport.sendMessage(message, message.getAllRecipients());
    } catch (MessagingException | UnsupportedEncodingException e) {
      e.printStackTrace();
    } finally {
      if (transport != null) {
        transport.close();
      }
    }
  }

}
