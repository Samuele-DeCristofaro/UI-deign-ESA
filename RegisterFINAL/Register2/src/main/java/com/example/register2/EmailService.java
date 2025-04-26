package com.example.register2;
import jakarta.mail.*;

import java.util.Properties;
import java.util.PropertyPermission;

public class EmailService {
    private String senderemail;
    private final Properties emailProperties;

    public EmailService(){
        emailProperties = new Properties();
        senderemail = "moviestar@gmail.com";
    }

}
