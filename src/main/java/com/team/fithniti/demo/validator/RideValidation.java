package com.team.fithniti.demo.validator;

import com.team.fithniti.demo.model.Ride;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RideValidation {
    public static List<String> validate(Ride ride){
        List<String> errors = new ArrayList<>();
        if ( ride == null ) {
            errors.add("Ride is null");
            return errors;
        }
        if (ride.getDescription().isEmpty())
            errors.add("Missing required field: 'Description'");
        if (ride.getMaxPlaces() == null)
            errors.add("Missing required field: 'MaxPlaces'");
        if (ride.getMaxPlaces() == 0)
            errors.add("Max places should should be >= 1");

        if (ride.getStartTime() == null )
            errors.add("Missing required field: 'StartTime'");
        if (!ride.getStartTime().isAfter(LocalTime.now().plusMinutes(15))) // start time > now + 15min
            errors.add("Invalid Start time...");

        // TODO: 11/4/21 validate pathway : from != to
        //* complete the rest
        return errors;
    }
}
