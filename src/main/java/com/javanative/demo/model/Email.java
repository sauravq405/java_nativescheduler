package com.javanative.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String to;
    private String from;
    private String subject;
    private String body;
    private String host;
    private String port;
    private String htmlContent;
}
