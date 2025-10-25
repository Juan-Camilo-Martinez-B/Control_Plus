package com.controlacceso.backend.service.impl;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.controlacceso.backend.exception.EmailSendingException;
import com.controlacceso.backend.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${sendgrid.api.key:}")
    private String sendGridApiKey;

    @Value("${spring.mail.from:martinez.b.juan.camilo@gmail.com}")
    private String fromEmail;

    @Override
    public boolean enviarCorreo(String destinatario, String asunto, String contenido) {
        return enviarCorreoConQR(destinatario, asunto, contenido, "");
    }

    @Override
    public boolean enviarCorreoConQR(String destinatario, String asunto, String contenido, String qrBase64) {
        try {
            log.info("Enviando correo a: {} usando SendGrid", destinatario);
            
            // Crear objetos de email
            Email from = new Email(fromEmail, "ControlPlus");
            Email to = new Email(destinatario);
            
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
                htmlContent.append("<img src='data:image/png;base64,")
                          .append(qrBase64)
                          .append("' alt='Código QR' style='max-width: 300px; border: 2px solid #2563eb; border-radius: 8px;'/>");
                htmlContent.append("</div>");
            }
            
            htmlContent.append("<div style='margin-top: 30px; padding-top: 20px; border-top: 1px solid #e5e7eb; font-size: 12px; color: #6b7280;'>");
            htmlContent.append("<p>Este es un correo automático, por favor no responder.</p>");
            htmlContent.append("<p>&copy; 2025 ControlPlus - Sistema de Control de Acceso</p>");
            htmlContent.append("</div>");
            htmlContent.append("</div></body></html>");
            
            Content content = new Content("text/html", htmlContent.toString());
            Mail mail = new Mail(from, asunto, to, content);
            
            // Si hay QR, también adjuntarlo como archivo
            if (qrBase64 != null && !qrBase64.isEmpty()) {
                Attachments attachments = new Attachments();
                attachments.setContent(qrBase64);
                attachments.setType("image/png");
                attachments.setFilename("codigo_qr.png");
                attachments.setDisposition("attachment");
                mail.addAttachments(attachments);
            }
            
            // Enviar usando SendGrid
            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            
            Response response = sg.api(request);
            
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                log.info("Correo enviado exitosamente a {} (Status: {})", destinatario, response.getStatusCode());
                return true;
            } else {
                log.error("Error al enviar correo. Status: {}, Body: {}", response.getStatusCode(), response.getBody());
                throw new EmailSendingException("Error al enviar correo electrónico. Status: " + response.getStatusCode());
            }
            
        } catch (IOException e) {
            log.error("Error al enviar correo a {}: {}", destinatario, e.getMessage());
            throw new EmailSendingException("Error al enviar correo electrónico", e);
        }
    }
}
