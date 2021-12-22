package com.example.mispro.Controller;

import com.example.mispro.Mapper.StatisticsMapper;
import com.example.mispro.Mapper.SubjectMapper;
import com.example.mispro.Mapper.UserMapper;
import com.example.mispro.Pojo.*;
import com.example.mispro.Service.ParenthesesOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class TestOnlineController {

    @Autowired
    ParenthesesOnlineService parenthesesOnlineService;
    @Autowired
    IntegrityController integrityController;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    StatisticsMapper statisticsMapper;
    List<Question> questions;
    @RequestMapping("/testOnlie")

    public String render(HttpSession session, @RequestParam("upload")MultipartFile multipartFile)
    {
        String filePath = "D:\\"+ UUID.randomUUID()+multipartFile.getOriginalFilename();
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(buffer);
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream = new FileInputStream(filePath);
            Map<Integer, String> answerMap = parenthesesOnlineService.getAnswerMap(filePath);
            integrityController.Tree2One(inputStream,filePath);
           questions = parenthesesOnlineService.getQuestions(filePath,answerMap);
            session.setAttribute("questions",questions);
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        return "onlineTest";
    }

    @RequestMapping("/check")
    public String check(HttpSession session,@RequestParam("answer") String[] checkBox,@RequestParam("subject")String subject)
    {
        User user =(User) session.getAttribute("user");
        StatisticsInfo info = null;
        Integer id = 7;
        if (subject != null)
        {
            if (user!=null)
            {
                id  = subjectMapper.getSubById(user.getUid(), subject);
                if (id == null) {
                    Subject subject1 = new Subject(null, user.getUid(), subject);
                    subjectMapper.insertSubject(subject1);
                }else {
                    info = statisticsMapper.getInfo(id,user.getUid());
                }
            }
        }
        int count = 0;
        List<Question> incorrectQuestion = new LinkedList<>();

        if(checkBox.length > questions.size())
        {
            for (int i = 0;i < questions.size()-1;i++)
            {
                if (questions.get(i).getAnswer().contains(checkBox[i]))
                {
                    count++;
                }
                else {
                    incorrectQuestion.add(questions.get(i));
                }
            }
        }
        else
        {
            for (int i = 0;i < checkBox.length-1;i++)
             {
                if (questions.get(i).getAnswer().contains(checkBox[i]))
                {
                    count++;
                }
                else {
                    incorrectQuestion.add(questions.get(i));
                }
            }
        }
            session.setAttribute("correct",count);
            session.setAttribute("space",questions.size()-checkBox.length<0?0:questions.size()-checkBox.length);
            session.setAttribute("incorrectQuestion",incorrectQuestion);
            if (info !=null)
            {
                    info.setCorrect(info.getCorrect()+count);
                    info.setCount(info.getCount()+checkBox.length);
                    info.setRatio(Double.valueOf(info.getCorrect())/Double.valueOf(info.getCount()));
                    statisticsMapper.updateInfo(info);
            }
            return "statistics";
    }

}
