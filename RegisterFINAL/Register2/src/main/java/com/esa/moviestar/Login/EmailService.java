package com.esa.moviestar.Login;

import java.util.Properties;

public class EmailService {
    private String senderemail;
    private final Properties emailProperties;

    public EmailService(){
        emailProperties = new Properties();
        senderemail = "moviestar@gmail.com";
    }

}
