package com.example.mispro.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsInfo {
    Integer uid;
    Integer sid;
    Integer count;
    Integer correct;
    Double ratio;
}
