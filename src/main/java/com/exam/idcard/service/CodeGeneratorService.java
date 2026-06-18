package com.exam.idcard.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class CodeGeneratorService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String generateQrCode(String text) throws Exception {
        Files.createDirectories(Path.of(uploadDir));

        String fileName = "qr-" + UUID.randomUUID() + ".png";
        Path path = Path.of(uploadDir, fileName);

        BitMatrix matrix = new MultiFormatWriter()
                .encode(text, BarcodeFormat.QR_CODE, 200, 200);

        MatrixToImageWriter.writeToPath(matrix, "PNG", path);

        return "/uploads/" + fileName;
    }

    public String generateBarcode(String text) throws Exception {
        Files.createDirectories(Path.of(uploadDir));

        String fileName = "barcode-" + UUID.randomUUID() + ".png";
        Path path = Path.of(uploadDir, fileName);

        BitMatrix matrix = new MultiFormatWriter()
                .encode(text, BarcodeFormat.CODE_128, 300, 100);

        MatrixToImageWriter.writeToPath(matrix, "PNG", path);

        return "/uploads/" + fileName;
    }
}
