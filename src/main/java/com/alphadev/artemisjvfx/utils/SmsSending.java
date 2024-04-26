package com.alphadev.artemisjvfx.utils;


import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsSending {
    public static final String ACCOUNT_SID = "ACae5665c0b3278c7d2a21487542122dc3";
    public static final String AUTH_TOKEN = "9a6a3fa610b67f13d64af2f8a1935c55";
    private static final String FROM_NUMBER = "+18583455486";



    public static void sendSms( String message) {
        Message sms = Message.creator(
                new PhoneNumber("+216 27372431"),  // To number
                new PhoneNumber(FROM_NUMBER),  // From Twilio number
                message
        ).create();
        System.out.println("Sent message SID: " + sms.getSid());
    }





}
