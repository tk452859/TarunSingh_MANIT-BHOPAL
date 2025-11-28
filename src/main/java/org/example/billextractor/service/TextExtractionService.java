package org.example.billextractor.service;


import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class TextExtractionService {
    private Tesseract tesseract;
    private boolean tesseractAvailable = false;

    public TextExtractionService() {
        initializeTesseract();
    }

    private void initializeTesseract() {
        try {
            this.tesseract = new Tesseract();

            // Try different possible Tesseract paths
            String[] possiblePaths = {
                    "/usr/share/tesseract-ocr/4.00/tessdata",
                    "/usr/share/tesseract-ocr/5/tessdata",
                    "/usr/share/tesseract-ocr/tessdata",
                    "C:/Program Files/Tesseract-OCR/tessdata", // Windows
                    "/usr/local/share/tessdata" // macOS
            };

            for (String path : possiblePaths) {
                try {
                    tesseract.setDatapath(path);
                    // Test if the path works by trying to set language
                    tesseract.setLanguage("eng");
                    tesseractAvailable = true;
                    System.out.println("Tesseract initialized successfully with path: " + path);
                    break;
                } catch (Exception e) {
                    System.out.println("Tesseract path failed: " + path);
                    continue;
                }
            }

            if (tesseractAvailable) {
                tesseract.setPageSegMode(1); // Automatic page segmentation
                tesseract.setOcrEngineMode(1); // Default OCR engine
            } else {
                System.err.println("Tesseract could not be initialized. Using fallback methods.");
            }

        } catch (Exception e) {
            System.err.println("Tesseract initialization failed: " + e.getMessage());
            tesseractAvailable = false;
        }
    }

    public String extractTextFromPdf(byte[] pdfBytes) {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            PDFTextStripper stripper = new PDFTextStripper();
            StringBuilder text = new StringBuilder();

            for (int i = 1; i <= document.getNumberOfPages(); i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                text.append("===== Page ").append(i).append(" =====\n");
                text.append(stripper.getText(document));
                text.append("\n");
            }

            return text.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract text from PDF", e);
        }
    }

    public String extractTextFromImage(byte[] imageBytes) {
        if (!tesseractAvailable) {
            return "OCR_NOT_AVAILABLE: Tesseract is not properly installed on the system.";
        }

        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
            if (image == null) {
                throw new RuntimeException("Unsupported image format or corrupted image");
            }

            return tesseract.doOCR(image);
        } catch (TesseractException | IOException e) {
            throw new RuntimeException("Failed to extract text from image: " + e.getMessage(), e);
        }
    }

    public boolean isTesseractAvailable() {
        return tesseractAvailable;
    }

    // Fallback method for simple text extraction without OCR
    public String extractBasicText(byte[] documentBytes, String fileType) {
        if ("PDF".equalsIgnoreCase(fileType)) {
            return extractTextFromPdf(documentBytes);
        } else {
            return "IMAGE_OCR_REQUIRED: Install Tesseract OCR for image processing";
        }
    }

}
