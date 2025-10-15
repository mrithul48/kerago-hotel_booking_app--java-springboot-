package org.kerago.keragobackend.service;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;


    public void sendHtmlMessage(String to,String subject,String templateName, Map<String, Object> variables){

        try{
            log.info("Attempting to send email to: {}", to);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper= new MimeMessageHelper(
                    message,false,"UTF-8"); // false = no attachment mode

            // Render Thymeleaf template
            Context context = new Context();

            context.setVariables(variables);
            String htmlContent = templateEngine.process("booking-confirmation",context);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent,true); // true = HTML
            helper.setFrom("mrithulmridhu05@gmail.com");

            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send email to: {}. Error: {}", to, e.getMessage(),e);
            throw new RuntimeException("Failed to send HTML email" +e);
        }
    }
}
