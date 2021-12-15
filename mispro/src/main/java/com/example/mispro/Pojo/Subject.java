package com.example.mispro.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.sql.In;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
Integer sid;
Integer uid;
String subjectName;


}
