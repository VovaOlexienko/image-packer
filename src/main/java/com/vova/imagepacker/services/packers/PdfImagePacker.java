package com.vova.imagepacker.services.packers;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.vova.imagepacker.domain.Image;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class PdfImagePacker implements ImagePacker {

    private static final String FILENAME = "%s.pdf";

    @SneakyThrows
    @Override
    public File packImages(String filename, List<Image> images) {
        File file = new File(String.format(FILENAME, filename));
        try (Document document = new Document(new PdfDocument(new PdfWriter(file.getPath())))) {
            images.stream()
                    .map(img -> ImageDataFactory.create(img.getContent()))
                    .map(com.itextpdf.layout.element.Image::new)
                    .forEach(document::add);
        }
        return file;
    }
}
