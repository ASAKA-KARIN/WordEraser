package com.example.mispro.Controller;

import com.example.mispro.Mapper.StatisticsMapper;
import com.example.mispro.Mapper.SubjectMapper;
import com.example.mispro.Mapper.UserMapper;

import com.example.mispro.Pojo.StatisticsInfo;
import com.example.mispro.Pojo.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Controller
public class BaserController {


    @RequestMapping("/")
    public String toIndex() {
        return "index";
    }

    @RequestMapping("/print")
    public String toPrint() {
        return "print";
    }

    @RequestMapping("/test")
    public String toTest() {
        return "test";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String toRegister() {
        return "register";
    }

    @RequestMapping(value = "/xAxis", method = RequestMethod.POST)
    public void xAxis(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Map<Integer, String> subject = (java.util.Map<Integer, String>) request.getSession().getAttribute("subject");
        ArrayList<String> arr = new ArrayList<>();
        Iterator iterator = subject.keySet().iterator();
        while (iterator.hasNext()) {
            arr.add(subject.get(iterator.next()));
        }
        try {
            response.getWriter().write(gson.toJson(arr));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    @RequestMapping(value = "/total", method = RequestMethod.POST)
    public void toTotal(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        List<StatisticsInfo> infos = (List<StatisticsInfo>) request.getSession().getAttribute("infos");
        Map<Integer, String> subject = (java.util.Map<Integer, String>) request.getSession().getAttribute("subject");
        ArrayList<Integer> total = new ArrayList<>();
        Iterator<Integer> iterator = subject.keySet().iterator();
        for (StatisticsInfo info : infos) {
            total.add(info.getCount());
        }
        try {
            response.getWriter().write(gson.toJson(total));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @RequestMapping(value = "/correct", method = RequestMethod.POST)
    public void toCorrect(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        List<StatisticsInfo> infos = (List<StatisticsInfo>) request.getSession().getAttribute("infos");
        Map<Integer, String> subject = (java.util.Map<Integer, String>) request.getSession().getAttribute("subject");
        ArrayList<Integer> rights = new ArrayList<>();
        Iterator<Integer> iterator = subject.keySet().iterator();
        for (StatisticsInfo info : infos) {
                    rights.add(info.getCorrect());

        }
        try {
            response.getWriter().write(gson.toJson(rights));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }


}
