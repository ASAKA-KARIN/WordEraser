package com.example.mispro.Mapper;

import com.example.mispro.Pojo.Subject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper {
     void insertSubject(Subject subject);
     Integer getSubById(Integer uid,String subjectName);
     List<Subject> getAllSubByUid(Integer uid);

}
