package com.example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {
    private Integer code;
    private Object data;
    private String msg;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
