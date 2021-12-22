package com.example.mispro.Service;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParenthesesChangeService {


    public void changeParentheses(InputStream inputStream, String filePath) {

        FileOutputStream fileOutputStream = null;
        try {
            XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
            List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
            fileOutputStream = new FileOutputStream(filePath);
            for (XWPFParagraph paragraph : paragraphs) {
                if (paragraph.getText().matches(".*[\\(|（].*[\\)|）].*$")) {
                    Map<String, Integer> posMap = getPos(paragraph);
                    Integer begin_pos = posMap.get("begin_pos");
                    Integer end_pos = posMap.get("end_pos");
                    if (begin_pos != null && end_pos != null) {
                        if (begin_pos == end_pos) {
                            paragraph = inSameRow(begin_pos, paragraph);
                        }
                        if (end_pos - begin_pos >= 1) {
                            paragraph = inDifferentRow(begin_pos, end_pos, paragraph);
                        }
                    }
                }
            }
            xwpfDocument.write(fileOutputStream);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
                inputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public XWPFParagraph inSameRow(Integer pos,XWPFParagraph paragraph) {

        List<XWPFRun> runs = paragraph.getRuns();
        XWPFRun run = runs.get(pos);
        String newText = replaceSpace(run.text());
        paragraph.removeRun(pos);
        paragraph.insertNewRun(pos).setText(newText);
        return paragraph;
    }
    public XWPFParagraph inDifferentRow(Integer begin_pos,Integer end_pos,XWPFParagraph paragraph){

        List<XWPFRun> runs = paragraph.getRuns();
        XWPFRun beginRun = runs.get(begin_pos);
        XWPFRun endRun = runs.get(end_pos);
        String s = getPosOfFirstParentheses(beginRun.text());
        paragraph.removeRun(begin_pos);
        paragraph.insertNewRun(begin_pos).setText(s);
//
//        if (beginRun.text().length() > 3)
//        {
//            String newText = getPosOfFirstParentheses(beginRun.text());
//            paragraph.removeRun(begin_pos);
//            paragraph.insertNewRun(begin_pos).setText(newText);
//            return paragraph;
//        }

       if (end_pos - begin_pos > 1)
        {
            for (int i = begin_pos+1; i < end_pos;i++)
            {

                paragraph.removeRun(i);
                paragraph.insertNewRun(i).setText("");

            }

        }

            return paragraph;
    }



    public String replaceSpace(String oldString)
    {
        String s = oldString.replaceAll("\\s*","");
        StringBuilder newString = new StringBuilder(s);
        Integer right = getPosOfLastParentheses(s);
        Integer left = getPosOfFirstParentheses1(s);
        for (int i = left+1; i < right;i++)
        {
            newString.setCharAt(i,' ');
        }
        return newString.toString();
    }
    public String getPosOfFirstParentheses(String text)
    {
        int i = text.lastIndexOf('(');
        int j = text.lastIndexOf("（");
        if (i > j)
        {
            return newRunText(i,text);
        }else
        {
            return newRunText(j,text);
        }
    }
    public Integer getPosOfFirstParentheses1(String text)
    {
        int i = text.lastIndexOf('(');
        int j = text.lastIndexOf("（");
        if (i > j)
        {
            return i;
        }else
        {
            return j;
        }
    }
    public Integer getPosOfLastParentheses(String text)
    {
        int i = text.lastIndexOf(')');
        int j = text.lastIndexOf("）");
        if (i > j)
        {
            return i;
        }else
        {
            return j;
        }
    }
    public String newRunText(int pos,String text)
    {
        StringBuilder newText = new StringBuilder(text);
        for (int i = pos+1; i < text.length(); i++)
        {
            newText.setCharAt(i,' ');
        }
        return  newText.toString();
    }


    public Map<String, Integer> getPos(XWPFParagraph paragraph) {
        Map<String, Integer> posMap = new HashMap<>();
        posMap.put("begin_pos", null);
        posMap.put("end_pos", null);
        List<XWPFRun> runs = paragraph.getRuns();
        for (int i = 0; i < runs.size(); i++) {
            String text = runs.get(i).text();
            if ((text.contains("（") || text.contains("("))) {
                for (int j = i; j < runs.size(); j++) {
                    String texts = runs.get(j).text();
                    if ((texts.contains("）") || texts.contains(")"))) {
                        posMap.put("begin_pos", i);
                        posMap.put("end_pos", j);
                    }
                }
            }
        }

        return posMap;
    }


}
