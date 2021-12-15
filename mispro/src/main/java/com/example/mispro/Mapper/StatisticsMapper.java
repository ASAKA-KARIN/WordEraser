package com.example.mispro.Mapper;


import com.example.mispro.Pojo.StatisticsInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticsMapper {
    StatisticsInfo getInfo(Integer sid, Integer uid);
    void updateInfo(StatisticsInfo info);

}
