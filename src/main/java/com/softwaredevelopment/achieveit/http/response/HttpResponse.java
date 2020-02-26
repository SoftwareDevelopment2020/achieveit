package com.softwaredevelopment.achieveit.http.response;

import lombok.Data;

@Data
public class HttpResponse <T> {

    private boolean success;

    private T data;

}
