package com.example.mispro.Service;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ParenthesesChangeServiceTest {

    @Test
    void changeParentheses() throws Exception {
        ParenthesesChangeService parenthesesChangeService = new ParenthesesChangeService();
        FileInputStream fileInputStream = new FileInputStream("D:\\管理统计学重修试题(1).docx");
        String filePath = "D:\\管理信息系统(2).docx";
        parenthesesChangeService.changeParentheses(fileInputStream,filePath);

    }
}