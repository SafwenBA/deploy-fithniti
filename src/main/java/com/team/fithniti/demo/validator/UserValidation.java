package com.team.fithniti.demo.validator;

import com.team.fithniti.demo.model.AppUser;

import java.util.ArrayList;
import java.util.List;

public class UserValidation {
    public static List<String> validate(AppUser user){
        ArrayList<String> errors = new ArrayList<>();
        if (!user.getPhoneNumber().matches("\\+216[0-9]{8}"))
            errors.add("Invalid Phone number");

        return errors;
    }
}
