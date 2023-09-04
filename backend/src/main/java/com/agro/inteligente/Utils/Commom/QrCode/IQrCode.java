package com.agro.inteligente.Utils.Commom.QrCode;

import com.google.zxing.WriterException;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface IQrCode {

    MultipartFile generate(String qrCodeText, int size) throws IOException, WriterException;
}
