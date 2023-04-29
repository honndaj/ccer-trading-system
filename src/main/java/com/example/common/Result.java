package com.example.common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(SystemString.SUCCESS_STATUS, "", null);
    }

    public static Result success(Object data) {
        return new Result(SystemString.SUCCESS_STATUS, "", data);
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

}
