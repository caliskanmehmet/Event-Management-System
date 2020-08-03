package com.yte.intern.project.security.services;

import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class EmailServiceImpl {

    @Qualifier("getJavaMailSender")
    @Autowired
    private JavaMailSender emailSender;

    public static int noOfQuickServiceThreads = 20; // for async process

    /**
     * this statement create a thread pool of twenty threads
     * here we are assigning send mail task using ScheduledExecutorService.submit();
     */
    private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("alkanchosen1@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        quickService.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    emailSender.send(message);
                }catch(Exception e){
                    System.out.println("Exception occur while send a mail : " + e.toString());
                }
            }
        });

    }

    public void sendMessageWithAttachment(
            String to, String subject, String text, String qrCodeText) throws Exception {

        BufferedImage bufferedImage = generateQRCodeImage(qrCodeText);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        File qrCode = new File("qrCode.png");

        helper.setFrom("alkanchosen1@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        ImageIO.write(bufferedImage, "png", qrCode);

        helper.addAttachment("QRCode.png", qrCode);

        quickService.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    emailSender.send(message);
                }catch(Exception e){
                    System.out.println("Exception occur while sending a mail : " + e.toString());
                }
            }
        });
    }

    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        ByteArrayOutputStream stream = QRCode
                .from(barcodeText)
                .withSize(250, 250)
                .stream();
        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());

        return ImageIO.read(bis);
    }
}
