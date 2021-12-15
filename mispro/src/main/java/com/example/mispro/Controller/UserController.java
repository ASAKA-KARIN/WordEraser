package com.example.mispro.Controller;

import com.example.mispro.Mapper.StatisticsMapper;
import com.example.mispro.Mapper.SubjectMapper;
import com.example.mispro.Mapper.UserMapper;
import com.example.mispro.Pojo.StatisticsInfo;
import com.example.mispro.Pojo.Subject;
import com.example.mispro.Pojo.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    StatisticsMapper statisticsMapper;
    static Boolean flag = false;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String index(HttpServletRequest req, HttpServletResponse response, User user){
        User name = userMapper.getUserByName(user.getUsername());
        try {
            if (name == null)
            {

                response.getWriter().write("用户不存在！！");

                return "index";
            }else if (!user.getPassword().equals(name.getPassword())) {
                response.getWriter().write("密码不正确！！");
            }
            else
            {
                req.getSession().setAttribute("user",name);
                Cookie cookie = new Cookie("username",user.getUsername());
                Cookie pwd = new Cookie("pwd",user.getPassword());
                cookie.setMaxAge(60*60*24*7);
                response.addCookie(cookie);
                response.addCookie(pwd);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return "index";
    }
    @RequestMapping("/myHome")
   public   String renderHome(HttpSession session){

        User user = (User)session.getAttribute("user");
        List<Subject> subjects = subjectMapper.getAllSubByUid(user.getUid());
        Map<Integer,String> subMap = new TreeMap<>();
        List<StatisticsInfo> statisticsInfos = new LinkedList<>();
        for (Subject subject:subjects)
        {
            subMap.put(subject.getSid(),subject.getSubjectName());
            StatisticsInfo info = statisticsMapper.getInfo(subject.getSid(), user.getUid());
            if(info != null)
            {
                statisticsInfos.add(info);
            }

        }
        session.setAttribute("subject",subMap);
        session.setAttribute("infos",statisticsInfos);
        System.out.println(session.getAttribute("infos"));
        return "user";
    }
    @RequestMapping("/checkUser/{username}/")

    public void checkUser(@PathVariable("username") String username,HttpServletResponse response){
        User userByName = userMapper.getUserByName(username);
        Gson gson = new Gson();
        if (userByName == null)
        {
            try {
                response.getWriter().write(gson.toJson("用户名可用"));
                flag = true;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }else
        {
            try {
                response.getWriter().write(gson.toJson("用户名已存在！！！"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "index";
    }

    @PostMapping("/register")
    @Transactional
    public String register(User user){
    if (flag)
    {
        userMapper.insertUser(user);
        return "login";
    }else
    {
        return "register";
    }


    }

}