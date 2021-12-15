package com.example.mispro.Service;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ColorChangeService {

    public void changeColorForDocx(InputStream inputStream, String filePath) {
        XWPFDocument xwpfDocument = null;
        FileOutputStream fileOutputStream = null;
        try {
            xwpfDocument = new XWPFDocument(inputStream);
            List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {

                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    run.setColor("000000");
                }
            }
            fileOutputStream = new FileOutputStream(filePath);
            xwpfDocument.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void changeColorForDoc(InputStream inputStream, String filePath) {
        FileOutputStream fileOutputStream = null;
        HWPFDocument hwpfDocument = null;
        try {
            hwpfDocument = new HWPFDocument(inputStream);
            Range range = hwpfDocument.getRange();
            int paragraphs = range.numParagraphs();
            for (int i = 0; i < paragraphs; i++) {
                Paragraph paragraph = range.getParagraph(i);
                for (int j = 0; j < paragraph.numCharacterRuns(); j++) {
                    CharacterRun run = paragraph.getCharacterRun(j);
                    run.setColor(0);
                }
            }
            fileOutputStream = new FileOutputStream(filePath);
            hwpfDocument.write(fileOutputStream);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

    }


}
