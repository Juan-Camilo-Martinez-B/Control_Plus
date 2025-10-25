package com.controlacceso.backend.service;

/**
 * Servicio para el envío de correos electrónicos
 */
public interface EmailService {
    
    /**
     * Envía un correo electrónico simple
     * @param destinatario Correo del destinatario
     * @param asunto Asunto del correo
     * @param contenido Contenido del correo
     * @return true si se envió exitosamente, false en caso contrario
     */
    boolean enviarCorreo(String destinatario, String asunto, String contenido);
    
    /**
     * Envía un correo electrónico con imagen QR adjunta
     * @param destinatario Correo del destinatario
     * @param asunto Asunto del correo
     * @param contenido Contenido del correo
     * @param qrBase64 Código QR en formato Base64
     * @return true si se envió exitosamente, false en caso contrario
     */
    boolean enviarCorreoConQR(String destinatario, String asunto, String contenido, String qrBase64);
}

