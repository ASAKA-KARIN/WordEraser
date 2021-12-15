package com.example.mispro;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.util.*;

@SpringBootTest
class MisproApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void testWord() throws Exception{
        String filePath = "D:\\Online.docx";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        XWPFDocument document = new NiceXWPFDocument(fileInputStream);
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        Map<Integer,String> questionMap = new HashMap<>();
        Map<Integer,String> answerMap = new HashMap<>();
        List<Integer> order = new LinkedList<>();
        for (int i = 0;i < 100;i++)
        {
            if(paragraphs.get(i).getText().matches(".*[\\(|（].*[\\)|）].*$"))
            {
                questionMap.put(i,paragraphs.get(i).getText());
                order.add(i);
            }
            if (paragraphs.get(i).getText().matches("^[A-G].*"))
            {
                answerMap.put(i,paragraphs.get(i).getText());
                order.add(i);
            }
        }
        Iterator<Integer> iterator = order.iterator();
        while (iterator.hasNext())
        {
            Integer next = iterator.next();
            if (questionMap.get(next) != null)
            {
                System.out.println(questionMap.get(next));
            }
            if (answerMap.get(next)!=null)
            {
                System.out.println(answerMap.get(next));
            }
        }
        fileInputStream.close();
    }

}
