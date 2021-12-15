package com.example.mispro.Service;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TextChangeServiceTest {

    @Test
    void changeText() throws FileNotFoundException {
        TextChangeService textChangeService = new TextChangeService();
        FileInputStream fileInputStream = new FileInputStream("D:\\习题 完全竞争.docx");
        String filePath = "D:\\target.docx";
        textChangeService.changeText(fileInputStream,filePath);

    }
}