package com.paresh.paresh.advices;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    //default
    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    //data constructor
    public ApiResponse(T data) {
        this();
        this.data = data;
    }


    //error constructor
    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }


}
