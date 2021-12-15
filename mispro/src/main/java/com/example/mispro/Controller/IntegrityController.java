package com.example.mispro.Controller;

import com.example.mispro.MisUtil;
import com.example.mispro.Service.ColorChangeService;
import com.example.mispro.Service.ParenthesesChangeService;
import com.example.mispro.Service.TextChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
public class IntegrityController {

    @Autowired
    ColorChangeService colorChangeService;
    @Autowired
    ParenthesesChangeService parenthesesChangeService;
    @Autowired
    TextChangeService textChangeService;

    @RequestMapping("/integrity")
    public String Tree2One(HttpServletResponse response, @RequestParam("upload")MultipartFile multipartFile){
        String filePath = "D:\\"+ UUID.randomUUID()+multipartFile.getOriginalFilename();
        String s = MisUtil.checkFileFormat(response, filePath);
        if (s.equals("ok"))
        {
            try {
                InputStream inputStream = multipartFile.getInputStream();
                colorChangeService.changeColorForDocx(inputStream, filePath);
                inputStream = new FileInputStream(filePath);
                parenthesesChangeService.changeParentheses(inputStream, filePath);
                inputStream = new FileInputStream(filePath);
                textChangeService.changeText(inputStream, filePath);
                MisUtil.download(response,filePath);
            }
            catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
        }else
        {
            return s;
        }

            return "success";
    }

    public void Tree2One(InputStream inputStream,String filePath){
        int index = filePath.lastIndexOf('.');
        String suffix = filePath.substring(index);
        if (suffix.equals(".docx"))
        {
            try {
                colorChangeService.changeColorForDocx(inputStream, filePath);
                inputStream = new FileInputStream(filePath);
                parenthesesChangeService.changeParentheses(inputStream, filePath);
                inputStream = new FileInputStream(filePath);
                textChangeService.changeText(inputStream, filePath);

            }
            catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
            finally {
                try {
                    inputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }else
        {

        }

    }


}
