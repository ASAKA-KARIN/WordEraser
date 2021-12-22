package com.example.mispro.Service;

import com.example.mispro.MisUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class TextChangeService {
        static boolean flag = true;

    public void changeText(InputStream inputStream,String filePath){
        XWPFDocument xwpfDocument = null;
        FileOutputStream fileOutputStream = null;
        try {
            xwpfDocument = new XWPFDocument(inputStream);

            fileOutputStream = new FileOutputStream(filePath);
            List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
            for (int j = 0;j < paragraphs.size();j++) {
                if (!paragraphs.get(j).getText().matches(".*[\\(|（].*[\\)|）].*$")){
                    if (paragraphs.get(j).getText().contains("正确答案"))
                    {
                        List<XWPFRun> runs = paragraphs.get(j).getRuns();

                        for (int i = 0;i < runs.size();i++)
                        {
                            String s = runs.get(i).text().replaceAll("\\s*", "");
                            runs.get(i).setText(s);
                            paragraphs.get(j).removeRun(i);
                            paragraphs.get(j).insertNewRun(i).setText("");
                        }
                    }
                }
            }
            xwpfDocument.write(fileOutputStream);
//            if (flag) {
//                MisUtil.wordToPDFAndPrint(filePath, filePath);
//            }
            }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally {

            try {
                fileOutputStream.close();
                inputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }



    }

}
