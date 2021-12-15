package com.example.mispro.Service;

import com.example.mispro.Mapper.StatisticsMapper;
import com.example.mispro.Mapper.SubjectMapper;
import com.example.mispro.Mapper.UserMapper;
import com.example.mispro.Pojo.Option;
import com.example.mispro.Pojo.Question;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ParenthesesOnlineService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    StatisticsMapper statisticsMapper;

    public Map<Integer, String> getAnswerMap(String filePath) {
        Map<Integer, String> answerMap = new TreeMap<>();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
            List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();

            for (int i = 0; i < paragraphs.size(); i++) {
                XWPFParagraph paragraph = paragraphs.get(i);
                if (paragraph.getText().matches(".*[\\(|（].*[\\)|）].*$")) {
                    Map<String, Integer> pos = getPos(paragraph);
                    String answer = answer(paragraph.getText(), pos.get("begin_pos"), pos.get("end_pos"));
                    if (answer.matches("\\s*[A-G]*\\s*")) {
                        answerMap.put(i, answer);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return answerMap;
        }

    }

    public Map<Integer, String> getQuestionMap(String filePath) {
        Map<Integer, String> questionMap = new TreeMap<>();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
            List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
            for (int i = 0; i < paragraphs.size(); i++) {
                String text = paragraphs.get(i).getText();
                if (paragraphs.get(i).getText().matches(".*[\\(|（].*[\\)|）].*$")) {
                    questionMap.put(i, text);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return questionMap;
        }
    }

    public List<Option> getOptions(String filePath) {
        Map<Integer, String> optionsMap = new TreeMap<>();
        List<Option> options = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
            List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
            for (int i = 0; i < paragraphs.size(); i++) {
                String text = paragraphs.get(i).getText();
                if (text.matches("^[A-G].*") || text.matches("^[\\(|（][A-G]*[\\)|）].*$")) {
                    optionsMap.put(i, text);
                }
            }
            options = optionsInitializer(optionsMap);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return options;
    }


    public List<Question> getQuestions(String filePath, Map<Integer, String> answerMap) {
        Map<Integer, String> questionMap = getQuestionMap(filePath);
        List<Option> options = getOptions(filePath);
        List<Question> questions = questionsInitializer(answerMap, questionMap);
        List<Question> questionList = getQuestionsOptions(questions, options);
        return questionList;
    }

    public Map<String, Integer> getPos(XWPFParagraph paragraph) {
        Map<String, Integer> posMap = new HashMap<>();
        posMap.put("begin_pos", null);
        posMap.put("end_pos", null);
        String text = paragraph.getText();
        int i = text.lastIndexOf('(');
        int j = text.lastIndexOf("（");
        if (i > j) {
            posMap.put("begin_pos", i);
        } else {
            posMap.put("begin_pos", j);
        }
        i = text.lastIndexOf("）");
        j = text.lastIndexOf(")");
        if (i > j) {
            posMap.put("end_pos", i);
        } else {
            posMap.put("end_pos", j);
        }
        return posMap;
    }

    public String answer(String text, int begin, int end) {
        String answer = text.substring(begin + 1, end);

        return answer;
    }

    public List<Option> optionsInitializer(Map<Integer, String> optionMap) {
        List<Option> options = new LinkedList<>();
        Iterator<Integer> iterator = optionMap.keySet().iterator();
        while (iterator.hasNext()) {
            int i = iterator.next();
            Option option = new Option();
            option.setOid(i);
            option.setOption(optionMap.get(i));
            option.setValue(optionMap.get(i).charAt(0));
            options.add(option);
        }
        return options;
    }


    public List<Question> questionsInitializer(Map<Integer, String> answerMap,
                                               Map<Integer, String> questionMap
    ) {
        List<Question> questions = new LinkedList<>();
        Iterator<Integer> answerKeys = answerMap.keySet().iterator();
        Iterator<Integer> questionKeys = questionMap.keySet().iterator();
        while (answerKeys.hasNext() && questionKeys.hasNext()) {
            int i = answerKeys.next();
            int j = questionKeys.next();
            if (i == j) {
                Question question = new Question();
                question.setQid(i);
                question.setQuestion(questionMap.get(j));
                question.setAnswer(answerMap.get(i));
                questions.add(question);
            }
        }

        return questions;
    }

    public List<Question> getQuestionsOptions(List<Question> questions, List<Option> options) {

        for (int x = 0; x < questions.size() - 1; x++) {
            int i = questions.get(x).getQid();
            int j = questions.get(x + 1).getQid();
            List<Option> targetOptions = new LinkedList<>();
            for (Option option : options) {
                if (option.getOid() > i && option.getOid() < j) {

                    targetOptions.add(option);
                }
            }
            questions.get(x).setOptions(targetOptions);
        }
        int m = questions.get(questions.size() - 1).getQid();

        List<Option> lastOptions = new LinkedList<>();
        for (Option option : options) {
            if (option.getOid() > m) {
                lastOptions.add(option);
            }
        }
        questions.get(questions.size() - 1).setOptions(lastOptions);


        return questions;
    }


}
