package com.voidlings.SpecificationHandling;

import java.io.*;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;

public class PDFtoText implements FileConverter {

    public PDFtoText() {

    }

    

    public String convert(String filename) throws Exception {

        File file = new File(filename);
        String path = file.getPath();
        PdfDocument pdf = new PdfDocument(path);
        int numberOfPages = pdf.getPages().getCount();
        PdfTable[] tableLists;

        StringBuilder builder = new StringBuilder();
        PdfTableExtractor extractor = new PdfTableExtractor(pdf);
       
        // Loop through the pages in the PDF
        for (int pageIndex = 0; pageIndex < numberOfPages; pageIndex++) {
            // Extract tables from the current page into a PdfTable array
             tableLists = extractor.extractTable(pageIndex);
            
            

            // If any tables are found
            if (tableLists != null && tableLists.length >= 0) {
               
                // Loop through the tables in the array
                for (PdfTable table : tableLists) {
                    // Loop through the rows in the current table
                    for (int i = 0; i < table.getRowCount(); i++) {
                        // Loop through the columns in the current table
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            // Extract data from the current table cell and append to the StringBuilder
                            String text = table.getText(i, j);
                            text = text.replaceAll("(?m)\\n(?!\\z)", " ");
                            builder.append(text).append(" | ");
                        }
                        builder.append("\r\n");
                    }
                }
            }
        }

        // Close the PDF document
        pdf.close();

        // Save the extracted text to a file
        String outputfile = "AssignmentMarkScheme.txt";
        FileWriter fw = new FileWriter(outputfile);
        fw.write(builder.toString());
        fw.flush();
        fw.close();

        return outputfile;
    }
}