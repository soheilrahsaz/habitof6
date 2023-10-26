package com.moses33.habitof6.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {
    private Integer id;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
}
