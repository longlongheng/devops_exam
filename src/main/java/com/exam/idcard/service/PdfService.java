package com.exam.idcard.service;

import com.exam.idcard.model.Profile;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generateProfilePdf(Profile profile) throws Exception {
        String html = """
                <html>
                <head>
                    <style>
                        body { font-family: Arial; }
                        .card {
                            width: 350px;
                            border: 2px solid black;
                            padding: 20px;
                            text-align: center;
                        }
                        .title { font-size: 22px; font-weight: bold; }
                    </style>
                </head>
                <body>
                    <div class='card'>
                        <div class='title'>ID CARD</div>
                        <p><b>Name:</b> %s</p>
                        <p><b>Type:</b> %s</p>
                        <p><b>Department:</b> %s</p>
                        <p><b>Registration:</b> %s</p>
                        <p><b>Email:</b> %s</p>
                    </div>
                </body>
                </html>
                """.formatted(
                profile.getFullName(),
                profile.getProfileType(),
                profile.getDepartment(),
                profile.getRegistrationNumber(),
                profile.getEmail()
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, null);
        builder.toStream(outputStream);
        builder.run();

        return outputStream.toByteArray();
    }
}
