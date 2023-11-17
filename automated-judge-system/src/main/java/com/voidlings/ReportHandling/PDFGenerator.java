package com.voidlings.ReportHandling;

import com.voidlings.TestCases.TestCase;
import com.voidlings.ReportHandling.EvaluationReport;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PDFGenerator {
    private EvaluationReport report;

    public PDFGenerator(EvaluationReport report) {
        this.report = report;
    }

    public void generatePDF(String fileName) throws IOException {
        String directory = "automated-judge-system/src/main/java/com/voidlings/";
        File dir = new File(directory);
        if (!dir.exists()){
            dir.mkdirs();
        }
    
    File file = new File(directory + fileName);
    if (file.exists()) {
        file.delete();
    }

    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(25, 700);
            contentStream.showText(report.getReportHeader().replace("\n", " "));
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText(report.getTestCaseSummary().replace("\n", " "));
            contentStream.newLineAtOffset(0, -15);
            String[] results = report.getTestCaseResults().replace("\n", " ").split(" ");
            for (int i = 0; i < results.length; i++) {
                contentStream.showText(results[i] + " ");
                if (i % 9 == 0) {
                    contentStream.newLineAtOffset(0, -15);
                }
            }
            contentStream.endText();
        }

        document.save(directory + fileName);
    }
}
        

    public static void main(String[] args) {
        // Create some test cases
        TestCase testCase1 = new TestCase("Test1", true, 10, "Passed");
        TestCase testCase2 = new TestCase("Test2", false, 0, "Failed");
        TestCase testCase3 = new TestCase("Test3", true, 10, "Passed");

        // Create a list of test cases
        List<TestCase> testCases = Arrays.asList(testCase1, testCase2, testCase3);

        // Create an evaluation report
        EvaluationReport report = new EvaluationReport(testCases);

        // Create a PDF generator
        PDFGenerator pdfGenerator = new PDFGenerator(report);

        // Generate a PDF report
        try {
            pdfGenerator.generatePDF("report.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}