package com.example.chatgpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO
 *
 * @Author Jialing Sun
 * @Date 2023/2/15 18:18
 * @Version 1.0
 */

@Data
@AllArgsConstructor
public class ResponseModel<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResponseModel<T> success(T data){
        return new ResponseModel<>(200,"success",data);
    }

    public static <T> ResponseModel<T> fail(T data){
        return new ResponseModel<>(0,"fail",data);
    }

    public static <T> ResponseModel<T> TooManyRequests(T data){
        return new ResponseModel<>(429,"fail",data);
    }


    public static <T> ResponseModel<T> IOErrorPOST(T data){
        return new ResponseModel<>(178,"fail",data);
    }

}

