package com.portal.app.service;

import com.portal.app.helper.SMSProperties;
import com.portal.app.payload.request.SMSRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SMSService {

    private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

    private final SMSProperties smsProperties;

    @Autowired
    public SMSService (SMSProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    public String sendSMS (SMSRequest request) {
        try {
            StringBuffer output = new StringBuffer();
            if(request.getCustomerNumber() > 0) {
                output.append(postSMS("&numbers=" + request.getCustomerNumber(), "&message=" + request.getCustomerMessage()));
            }
            if(request.getOwnerNumber() > 0) {
                output.append(postSMS("&numbers=" + request.getOwnerNumber(), "&message=" + request.getOwnerMessage()));
            }
            return output.toString();
        } catch (Exception e) {
            logger.error("Error SMS " + e);
            return "Error " + e;
        }
    }

    private String postSMS(String number, String message) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(smsProperties.getUrl()).openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        String apiKey = "apikey=" + smsProperties.getApiKey();
        String sender = "&sender=" + smsProperties.getSender();
        String data = apiKey + number + message + sender;
        conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
        conn.getOutputStream().write(data.getBytes("UTF-8"));
        final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        final StringBuffer stringBuffer = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            stringBuffer.append(line);
        }
        rd.close();
        return stringBuffer.toString();
    }

}
