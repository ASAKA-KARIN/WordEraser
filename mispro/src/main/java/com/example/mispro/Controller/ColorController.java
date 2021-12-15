package com.example.mispro.Controller;

import com.example.mispro.Service.ColorChangeService;
import com.example.mispro.MisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Controller
public class ColorController {

    @Autowired
    ColorChangeService colorChangeService;

@RequestMapping("/color")
    public void ChangeColorControl(HttpServletRequest req, HttpServletResponse resp,
                                     @RequestParam("upload") MultipartFile multipartFile) {

    String filePath = "D:\\"+ UUID.randomUUID()+multipartFile.getOriginalFilename();
    int index = filePath.lastIndexOf(".");
    String suffix = filePath.substring(index);
    if (suffix.equals(".docx"))
    {
        try {
            colorChangeService.changeColorForDocx(multipartFile.getInputStream(),filePath);
            MisUtil.download(resp,filePath);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }else if (suffix.equals(".doc"))
    {
        try {
            colorChangeService.changeColorForDoc(multipartFile.getInputStream(),filePath);
            MisUtil.download(resp,filePath);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


}



}
