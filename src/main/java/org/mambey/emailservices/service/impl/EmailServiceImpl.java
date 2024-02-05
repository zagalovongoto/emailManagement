package org.mambey.emailservices.service.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.hibernate.pretty.MessageHelper;
import org.mambey.emailservices.service.EmailService;
import org.mambey.emailservices.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

import static org.mambey.emailservices.utils.EmailUtils.getEmailMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender emailSender;

    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Email Verification message");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(getEmailMessage(name, host, token));
            emailSender.send(message);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageWithAttachments(String name, String to, String token) {
        try{
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setPriority(1);
            helper.setSubject("Email Verification message");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(getEmailMessage(name, host, token));

            //add attachements
            FileSystemResource fort = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/avocat.jpeg"));
            FileSystemResource fort1 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/GIM_FICHE DE DEMANDE DE FORMATION_Edem_AGBETI_Anglais.pdf"));
            FileSystemResource fort2 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/sonar_info.txt"));

            helper.addAttachment(fort.getFilename(), fort);
            helper.addAttachment(fort1.getFilename(), fort1);
            helper.addAttachment(fort2.getFilename(), fort2);

            emailSender.send(message);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageWithEmbeddedImages(String name, String to, String token) {
        try{
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setPriority(1);
            helper.setSubject("Email Verification message");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(getEmailMessage(name, host, token));

            //add attachements
            FileSystemResource avocat = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/avocat.jpeg"));
            FileSystemResource orange = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/orange.jpg"));

            helper.addInline(getContentId(avocat.getFilename()), avocat);
            helper.addInline(getContentId(orange.getFilename()), orange);


            emailSender.send(message);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {
        try{
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setPriority(1);
            helper.setSubject("Email Verification message");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(getEmailMessage(name, host, token));

            //add attachements
            FileSystemResource file1 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/GIM_FICHE DE DEMANDE DE FORMATION_Edem_AGBETI_Anglais.pdf"));
            FileSystemResource file2 = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/sonar_info.txt"));

            helper.addInline(getContentId(file1.getFilename()), file1);
            helper.addInline(getContentId(file2.getFilename()), file2);

            emailSender.send(message);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendHtmlEmails(String name, String to, String token) {

    }

    @Override
    @Async
    public void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token) {

    }

    private MimeMessage getMimeMessage(){
        return emailSender.createMimeMessage();
    }

    private String getContentId(String fileName){
        return "<" + fileName + ">";
    }
}
