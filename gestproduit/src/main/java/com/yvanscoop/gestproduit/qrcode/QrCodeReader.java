package com.yvanscoop.gestproduit.qrcode;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QrCodeReader {

    private final Logger log = LoggerFactory.getLogger(QrCodeReader.class);
    public String decodeQRCode(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }
    /*public String decodeQRCode() throws IOException {

        try {
                Webcam webcam = Webcam.getWebcams().get(0); // non-default (e.g. USB) webcam can be used too
                webcam.getViewSize();
                log.debug("sa taille "+webcam.getViewSize());
                WebcamPanel panel = new WebcamPanel(webcam);
                panel.setPreferredSize( WebcamResolution.QVGA.getSize());
                panel.setFPSDisplayed(true);
                webcam.open();
                Result result = null;
                BufferedImage image = null;
                image = webcam.getImage();
                *//*if ((image = webcam.getImage()) == null) {
                    continue;
                }
                *//*
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                result = new MultiFormatReader().decode(bitmap);
                log.debug("QR code data is: " + result.getText());
                return result.getText();
        } catch (NotFoundException e) {
            // fall thru, it means there is no QR code in image
            log.debug("pas d'identifiant!!!");
            return  "desole";
        }
    }*/
}