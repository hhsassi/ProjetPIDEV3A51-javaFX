package com.alphadev.artemisjvfx.utils;

import java.time.LocalDateTime;


public class CodeGenerator {






        public static String generateCode(String userName) {
            // Get current date/time
            LocalDateTime now = LocalDateTime.now();

            // Extract different components of the date/time
            int seconds = now.getSecond() % 100;
            char secondLetter = userName.charAt(1);
            int secondLetterOrder = Character.toUpperCase(secondLetter) - 'A' + 1;
            String hour = String.format("%02d", now.getHour());
            char firstLetter = userName.charAt(0);
            int firstLetterOrder = Character.toUpperCase(firstLetter) - 'A' + 1;
            int firstDigitDate = Character.getNumericValue(String.valueOf(now.getDayOfMonth()).charAt(0));

            // Concatenate all these values to form the 6-digit code
            String code = String.format("%02d%02d%s%02d%d", seconds, secondLetterOrder, hour, firstLetterOrder, firstDigitDate);

            return code;
        }
    }


