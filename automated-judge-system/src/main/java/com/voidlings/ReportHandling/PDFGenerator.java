package com.voidlings.ReportHandling;

import com.voidlings.ReportHandling.PDFEvaluationReport;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

/**
 * The PDFGenerator class implements the Generator interface and is responsible for
 * generating PDF reports based on the provided EvaluationReport and title.
 *
 * @author Voidlings
 * @version 1.0
 */
public class PDFGenerator implements Generator {

    /**
     * The EvaluationReport to generate the PDF report from.
     */
    private EvaluationReport report;

    /**
     * The title of the report.
     */
    private String title;

    /**
     * Constructs a PDFGenerator with the given EvaluationReport and title.
     *
     * @param report The EvaluationReport to generate the PDF report from.
     * @param title  The title of the report.
     */
    public PDFGenerator(EvaluationReport report, String title) {
        this.report = report;
        this.title = title;
    }

    /**
     * Generates the PDF report and saves it to the specified directory.
     *
     * @throws IOException If an I/O error occurs during the PDF generation.
     */
    public void generate() throws IOException {
        String directory = "SubmissionReports/";
        File dir = new File(directory);
        if (!dir.exists()) {
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
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.beginText();
            int yPosition = 725;
            contentStream.newLineAtOffset(25, yPosition);
            contentStream.showText(title + " Submission Grade Report");
            yPosition -= 20;
            contentStream.newLineAtOffset(0, -20);

            String[] summary = report.getTestCaseSummary().split("\n");
            for (String line : summary) {
                String[] words = line.split(" ");
                for (String word : words) {
                    if (yPosition < 45) {  // Check if near the bottom of the page
                        contentStream.endText();
                        contentStream.close();

                        page = new PDPage();
                        document.addPage(page);

                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.beginText();
                        yPosition = 725;
                        contentStream.newLineAtOffset(25, yPosition);
                    }
                    if (word != null) {
                        contentStream.showText(word + " ");
                    }
                }
                yPosition -= 20;
                contentStream.newLineAtOffset(0, -20);
            }

            String[] results = report.getTestCaseResults().split("\n");
            for (String line : results) {
                String[] words = line.split(" ");
                for (String word : words) {
                    if (yPosition < 40) {  
                        contentStream.endText();
                        contentStream.close();

                        page = new PDPage();
                        document.addPage(page);

                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.beginText();
                        yPosition = 725;
                        contentStream.newLineAtOffset(25, yPosition);
                    }
                    if (word != null) {
                        contentStream.showText(word + " ");
                    }
                }
                yPosition -= 20;
                contentStream.newLineAtOffset(0, -20);
            }
            String reportName = "Report_" + title + ".pdf";
            contentStream.endText();
            contentStream.close();
            document.save(directory + reportName);
        }
    }
}
