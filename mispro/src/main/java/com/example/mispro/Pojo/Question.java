package com.example.mispro.Pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {
    Integer qid;
    String question;
    List<Option> options;
    String answer;
    String subject;
}
