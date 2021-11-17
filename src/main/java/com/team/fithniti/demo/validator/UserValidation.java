package com.team.fithniti.demo.validator;

import com.team.fithniti.demo.dto.request.NewAdmin;
import com.team.fithniti.demo.dto.request.NewUser;

import java.util.ArrayList;
import java.util.List;

public class UserValidation {
    public static List<String> validate(NewUser user){
        ArrayList<String> errors = new ArrayList<>();
        if (!user.getPhoneNumber().matches("\\+216[0-9]{8}"))
            errors.add("Invalid Phone number");
        if (user.getFirstName().length() == 0 || user.getFirstName() == null)
            errors.add("Firstname must not be empty ! ") ;
        if (user.getLastName().length() == 0 || user.getLastName() == null)
            errors.add("LastName must not be empty ! ") ;
        if (user.getPassword().length() < 5)
            errors.add("Weak Password please choose another !") ;
        return errors;
    }

    // this one because of the argument type 
    // TODO: 11/12/2021 rework this
    public static List<String> validateAdmin(NewAdmin admin){
        ArrayList<String> errors = new ArrayList<>();
        if (!admin.getPhoneNumber().matches("\\+216[0-9]{8}"))
            errors.add("Invalid Phone number");
        if (admin.getFirstName().length() == 0 || admin.getFirstName() == null)
            errors.add("Firstname must not be empty ! ") ;
        if (admin.getLastName().length() == 0 || admin.getLastName() == null)
            errors.add("LastName must not be empty ! ") ;
        if (admin.getPassword().length() < 5)
            errors.add("Weak Password please choose another !") ;
        return errors;
    }

    public static boolean validatePhoneNumber(String phoneNumber){
        return phoneNumber.matches("\\+216[0-9]{8}") ;
    }

    public static boolean validatePassword(String password){
        return (password.length() < 5) ;
    }
}
