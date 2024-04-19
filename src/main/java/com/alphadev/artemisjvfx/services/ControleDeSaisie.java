package com.alphadev.artemisjvfx.services;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControleDeSaisie {
    public ControleDeSaisie() {
    }
    public  boolean isValidEmail(String email) {
        String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }
    public  boolean checkText(String s  )
    {
        return s.matches("[a-zA-Z]*") &&  s.length()>=2 ;
    }
    public boolean chekNumero(String s) {
        return s.matches("[0-9]*") && s.length() ==8 ;
    }

    public  boolean isDateValidAndOver18(LocalDate selectedDate) {

        if (selectedDate.isAfter(LocalDate.now())) return false;
        Period age = Period.between(selectedDate, LocalDate.now());
        return age.getYears() >= 18;
    }

    public  boolean checkPasswordStrength(String password) {
        if (password.length() < 6) return false;
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$");
        Matcher matcher = pattern.matcher(password);

        if (matcher.matches()) return true;
        else return false;

    }

    //ahmed
    public boolean checkName(String name) {
        return name.matches("[a-zA-Z\\s]+") && name.length() >= 2;
    }

    public boolean checkDescription(String description) {
        return !description.isEmpty(); // Basic check, can be extended based on specific needs
    }

    public boolean checkDuration(String duration) {
        try {
            int dur = Integer.parseInt(duration);
            return dur > 0; // Duration should be positive
        } catch (NumberFormatException e) {
            return false; // Not a valid integer
        }
    }

    public boolean checkLevel(String level) {
        try {
            int lvl = Integer.parseInt(level);
            return lvl >= 1 && lvl <= 10; // Assuming levels are between 1 and 10
        } catch (NumberFormatException e) {
            return false; // Not a valid integer
        }
    }

    public boolean checkImage(String imagePath) {
        // Simple check to see if it's not empty and has a proper image file extension
        return imagePath != null && imagePath.matches(".*(\\.png|\\.jpg|\\.jpeg|\\.gif)$");
    }




}



