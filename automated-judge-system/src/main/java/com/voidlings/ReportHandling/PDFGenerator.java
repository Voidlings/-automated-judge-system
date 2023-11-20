package com.voidlings.ReportHandling;

import com.voidlings.ReportHandling.PDFEvaluationReport;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;


public class PDFGenerator implements Generator{
    private EvaluationReport report;
    private String title; 

    public PDFGenerator(EvaluationReport report, String title) {
        this.report = report;
        this.title = title;
    }

    public void generate() throws IOException {
        String directory = "Reports/" ;
        File dir = new File(directory);
        if (!dir.exists()){
            dir.mkdirs();
        }
    
        File file = new File(directory + title);
        if (file.exists()) {
            file.delete();
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 9);
            contentStream.beginText();
            int yPosition = 725;
            contentStream.newLineAtOffset(25, yPosition);
            contentStream.showText(title + " Submission Grade Report");
            yPosition -= 20;
            contentStream.newLineAtOffset(0, -20);

            String[] summary = report.getTestCaseSummary().split("\n");
            for (String line : summary) {
                if (yPosition < 45) {  // Check if near the bottom of the page
                    contentStream.endText();
                    contentStream.close();

                    page = new PDPage();
                    document.addPage(page);

                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 9);
                    contentStream.beginText();
                    yPosition = 725;
                    contentStream.newLineAtOffset(25, yPosition);
                }
                contentStream.showText(line);
                yPosition -= 20;
                contentStream.newLineAtOffset(0, -20);
            }

            String[] results = report.getTestCaseResults().split("\n");
            for (int i = 0; i < results.length; i++) {
                String[] words = results[i].split(" ");
                for (String word : words) {
                    contentStream.showText(word + " ");
                }
                yPosition -= 20;
                contentStream.newLineAtOffset(0, -20);
                if (yPosition < 40) {  // Check if near the bottom of the page
                    contentStream.endText();
                    contentStream.close();

                    page = new PDPage();
                    document.addPage(page);

                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 9);
                    contentStream.beginText();
                    yPosition = 725;
                    contentStream.newLineAtOffset(25, yPosition);
                }
            }
            String reportName = "Report_" + title + ".pdf";
            contentStream.endText();
            contentStream.close();
            document.save(directory + reportName);
        }
    }
}
        