package com.example.module.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DIPU on 7/27/20
 */
@Data
@NoArgsConstructor
public class Response <T> implements Serializable {
    private HttpStatus httpStatus;
    private Status status;
    private T body;
    private List<T> errorList;

    public Response(HttpStatus httpStatus, Status status, T body, List<T> errorList) {
        this.setHttpStatus(httpStatus);
        this.setStatus(status);
        this.setBody(body);
        this.errorList = errorList;
    }

    public Response(T body) {

        this.setBody(body);
    }
}
