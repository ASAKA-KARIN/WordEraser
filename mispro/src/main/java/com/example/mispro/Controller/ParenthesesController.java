package com.example.mispro.Controller;

import com.example.mispro.Service.ParenthesesChangeService;
import com.example.mispro.MisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
public class ParenthesesController {

    @Autowired
    ParenthesesChangeService parenthesesChangeService;


    @RequestMapping("/parentpheses")
    public String changerParentheses(HttpServletRequest req, HttpServletResponse resp,
                                     @RequestParam("upload") MultipartFile multipartFile)  {
        String filePath = "D:\\"+ UUID.randomUUID()+multipartFile.getOriginalFilename();
        String s = MisUtil.checkFileFormat(resp, filePath);
        if (s.equals("ok"))
        {
           try {
               InputStream inputStream = multipartFile.getInputStream();
               parenthesesChangeService.changeParentheses(inputStream, filePath);
               MisUtil.download(resp,filePath);

           }catch (IOException ioException)
           {
               ioException.printStackTrace();
           }
           }else {
            return s;
        }

        return "success";
    }




}
