package com.example.mispro.Controller;

import com.example.mispro.Service.TextChangeService;
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
public class TextChangeController {
    @Autowired
    TextChangeService textChangeService;
    @RequestMapping("/text")
    public String changeText(HttpServletRequest req, HttpServletResponse resp, @RequestParam("upload")MultipartFile multipartFile){

            String filePath = "D:\\"+ UUID.randomUUID()+multipartFile.getOriginalFilename();
            String  s = MisUtil.checkFileFormat(resp,filePath);
        try {
            if (s.equals("ok")) {
                InputStream inputStream = multipartFile.getInputStream();
                textChangeService.changeText(inputStream, filePath);
                MisUtil.download(resp, filePath);
            }
            else {
                return s;
            }
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
            return "success";
    }


}
