package com.controlacceso.backend.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QrService {

    private static final int QR_CODE_WIDTH = 300;
    private static final int QR_CODE_HEIGHT = 300;
    private static final String IMAGE_FORMAT = "png";

    /**
     * Genera un código QR en formato Base64
     * @param qrToken El token que será codificado en el QR
     * @return Imagen QR en formato Base64
     */
    public String generarQrBase64(String qrToken) {
        if (qrToken == null || qrToken.trim().isEmpty()) {
            log.error("Intento de generar QR con token vacío o nulo");
            throw new IllegalArgumentException("El token del QR no puede estar vacío");
        }

        try {
            log.debug("Generando código QR para token: {}", qrToken.substring(0, Math.min(10, qrToken.length())) + "...");
            
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    qrToken,
                    BarcodeFormat.QR_CODE,
                    QR_CODE_WIDTH,
                    QR_CODE_HEIGHT
            );
            
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, IMAGE_FORMAT, baos);
            
            String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
            
            log.info("Código QR generado exitosamente");
            return base64Image;
            
        } catch (WriterException e) {
            log.error("Error al codificar el código QR: {}", e.getMessage());
            throw new RuntimeException("Error al codificar el código QR", e);
        } catch (IOException e) {
            log.error("Error al escribir la imagen del código QR: {}", e.getMessage());
            throw new RuntimeException("Error al generar la imagen del código QR", e);
        }
    }
}
