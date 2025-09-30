package org.kerago.keragobackend.service;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;


    public void sendHtmlMessage(String to,String subject,String templateName, Map<String, Object> variables){

        try{
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
            helper.setFrom("no-reply@kerago.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send HTML email" +e);
        }
    }
}
