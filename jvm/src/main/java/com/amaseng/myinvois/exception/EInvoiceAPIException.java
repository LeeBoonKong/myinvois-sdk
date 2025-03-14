package com.amaseng.myinvois.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class EInvoiceAPIException extends Exception {
    private Map<String, List<String>> requestHeaders;
    private int statusCode;
    private String message;
    private String responseBody;
}
