package com.controlacceso.backend.service.impl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.controlacceso.backend.exception.EmailSendingException;
import com.controlacceso.backend.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from:noreply@controlplus.com}")
    private String fromEmail;

    @Override
    public boolean enviarCorreo(String destinatario, String asunto, String contenido) {
        return enviarCorreoConQR(destinatario, asunto, contenido, "");
    }

    @Override
    public boolean enviarCorreoConQR(String destinatario, String asunto, String contenido, String qrBase64) {
        try {
            log.info("Enviando correo a: {}", destinatario);
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            
            // Crear contenido HTML
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body style='font-family: Arial, sans-serif;'>");
            htmlContent.append("<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>");
            htmlContent.append("<h2 style='color: #2563eb;'>ControlPlus</h2>");
            htmlContent.append("<div style='background-color: #f3f4f6; padding: 20px; border-radius: 8px;'>");
            htmlContent.append(contenido.replace("\n", "<br>"));
            htmlContent.append("</div>");
            
            // Si hay QR, agregarlo como imagen embebida
            if (qrBase64 != null && !qrBase64.isEmpty()) {
                htmlContent.append("<div style='text-align: center; margin-top: 20px;'>");
                htmlContent.append("<p><strong>Tu Código QR de Acceso:</strong></p>");
                htmlContent.append("<img src='cid:qrImage' alt='Código QR' style='max-width: 300px; border: 2px solid #2563eb; border-radius: 8px;'/>");
                htmlContent.append("</div>");
            }
            
            htmlContent.append("<div style='margin-top: 30px; padding-top: 20px; border-top: 1px solid #e5e7eb; font-size: 12px; color: #6b7280;'>");
            htmlContent.append("<p>Este es un correo automático, por favor no responder.</p>");
            htmlContent.append("<p>&copy; 2025 ControlPlus - Sistema de Control de Acceso</p>");
            htmlContent.append("</div>");
            htmlContent.append("</div></body></html>");
            
            helper.setText(htmlContent.toString(), true);
            
            // Si hay QR, adjuntarlo como inline
            if (qrBase64 != null && !qrBase64.isEmpty()) {
                byte[] qrBytes = Base64.getDecoder().decode(qrBase64);
                ByteArrayResource qrResource = new ByteArrayResource(qrBytes);
                helper.addInline("qrImage", qrResource, "image/png");
            }
            
            mailSender.send(message);
            log.info("Correo enviado exitosamente a {}", destinatario);
            return true;
            
        } catch (MessagingException e) {
            log.error("Error al enviar correo a {}: {}", destinatario, e.getMessage());
            throw new EmailSendingException("Error al enviar correo electrónico", e);
        }
    }
}
