package com.shubham.HirePilot.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDFormContentStream;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
@Slf4j
public class TextExtractionService {

    public String extractTextFromPdf(byte[] fileData){

        try (PDDocument document = Loader.loadPDF(fileData)){

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            log.info("Successfully extracted text from PDF ({} characters)", text.length());
            return text;
        } catch (IOException e){

            log.error("Error extracting text  from PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to extract text from PDF", e);
        }
    }

    public String extractText(byte[] fileData, String contentType){

        if(contentType.contains("pdf")){
            return extractTextFromPdf(fileData);
        }

        throw new UnsupportedOperationException("file type not supported: " + contentType);
    }

}
