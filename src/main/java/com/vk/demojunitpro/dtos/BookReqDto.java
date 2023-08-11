package com.vk.demojunitpro.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReqDto {
    private String bookName;


    private int rate;
}
