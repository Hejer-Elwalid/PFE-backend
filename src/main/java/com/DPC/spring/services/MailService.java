package com.DPC.spring.services;

import com.DPC.spring.Canstants.Constant;
import com.DPC.spring.entities.PasswordResetToken;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.PasswordResetTokenRepository;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    @Autowired
    private Configuration config;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendEmailWithAttachment(Utilisateur appUser, String Template, Map<String, Object> model) throws MailException, MessagingException, IOException, TemplateException, MessagingException, IOException, TemplateException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());
        Template t = config.getTemplate(Template);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t,model);
        helper.setTo(appUser.getEmail());
        helper.setSubject("Equipe RH : changer votre mot de passe en un click");
        helper.setText(html,true);
        javaMailSender.send(mimeMessage);
    }
    public void createPasswordResetForUser(Utilisateur user, String token){
        Date d=new Date(new Date().getTime()+28800000);
        PasswordResetToken passwordResetToken = new PasswordResetToken(token,user,d);
        passwordResetTokenRepository.save(passwordResetToken);
    }
    public String ResetPasswordMail(Utilisateur user, String token){
        String url= Constant.redirectUrl +token;
        Map<String,Object> model =new HashMap<>();
        model.put("username",user.getLogin());
        model.put("link",url);
        try {
            sendEmailWithAttachment(user,"ResetPassword.ftl",model);
        } catch (MailException mailException) {
            System.out.println(mailException);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Congratulations! Your mail has been send to the user.";
    }
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;    }
    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getDateExpiration().before(cal.getTime());
    }
}
