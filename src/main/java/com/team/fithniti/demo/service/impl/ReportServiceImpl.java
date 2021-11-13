package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.dto.request.NewReport;
import com.team.fithniti.demo.dto.request.ReportHandler;
import com.team.fithniti.demo.dto.response.HandledReportDTO;
import com.team.fithniti.demo.dto.response.ReportCard;
import com.team.fithniti.demo.dto.response.RideReportDTO;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.HandledReport;
import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.model.RideReport;
import com.team.fithniti.demo.repository.*;
import com.team.fithniti.demo.service.ReportService;
import com.team.fithniti.demo.util.ReportAction;
import com.team.fithniti.demo.util.ReportFilter;
import com.team.fithniti.demo.util.ReportStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {

    @Autowired
    private RideRepo rideRepo;

    @Autowired
    private RideReportRepo rideReportRepo;

    @Autowired
    private HandledReportRepo handledReportRepo;

    @Autowired
    private PassengerRepo passengerRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ReportTypeRepo reportTypeRepo;

    @Override
    public RideReportDTO createRideReport(NewReport newReport) {
        Optional<Ride> ride = rideRepo.findById(newReport.getRideId());
        //check if those nibbas exist
        if(ride.isEmpty()) throw new ResourceNotFound("ride not found");//TODO: verify this bitch later
        if(!userRepo.existsById(newReport.getReportedId())) throw new ResourceNotFound("reported not found");
        if(!userRepo.existsById(newReport.getReporterId())) throw new ResourceNotFound("reporter not found");
        //check if they belong to the same ride
        //TODO: check those mfs
        //add rideReport
        RideReport rideReport = RideReport.builder()
                .reportTypes(newReport.getReportTypes().stream().map(
                        element -> reportTypeRepo.getById(element)
                ).collect(Collectors.toList()))
                .ride(ride.get())
                .reported(userRepo.getById(newReport.getReportedId()))
                .reporter(userRepo.getById(newReport.getReporterId()))
                .reportStatus(ReportStatus.valueOf(newReport.getStatus()))
                .build();
        //check whenever reported guy is driver or passenger
        if(newReport.getReportedId() == ride.get().getDriver().getUser().getId() ){
            //reported is the driver
            //Increment driver report counter by
            //DriverService.updateReportCounter(UUID driver)
        }else{
            //he is not the driver
            //Increment Passenger report counter by
            //PassengerService.updateReportCounter(UUID passenger)
        }
        rideReportRepo.save(rideReport);
        return RideReportDTO.fromEntity(rideReport);
    }

    @Override
    public Page<RideReportDTO> getAllDriverReportsById(UUID driverId, int page, int size, boolean sorted) {
        return getAllEntitiesReportsById(driverId, true, page, size, sorted);
    }

    @Override
    public Page<RideReportDTO> getAllPassengerReportsById(UUID passengerId, int page, int size, boolean sorted) {
        return getAllEntitiesReportsById(passengerId, false, page, size, sorted);
    }

    private Page<RideReportDTO> getAllEntitiesReportsById(UUID id, boolean isDriver,  int page, int size, boolean sorted){
        Optional<AppUser> entity = userRepo.findById(id);
        if(entity.isEmpty()) throw new ResourceNotFound("driver not even an app user");
        if(driverRepo.findByUser(entity.get()).isEmpty() && isDriver) throw new ResourceNotFound("driver not found");
        if(passengerRepo.findByUser(entity.get()).isEmpty()) throw new ResourceNotFound("passenger not found");
        Pageable pageable = sorted ? PageRequest.of(page, size, Sort.by("createdDate")) : PageRequest.of(page, size);
        return (isDriver ? rideReportRepo.findAllByDriver(id, pageable) : rideReportRepo.findAllByPassengerId(id, pageable)).map(RideReportDTO::fromEntity);
    }

    @Override
    public HandledReportDTO createHandledReport(ReportHandler newReportHandled) {
        if(!rideReportRepo.existsById(newReportHandled.getRideReportId())) throw new ResourceNotFound("ride report not found");
        //check admin existence after merge
        HandledReport handledReport = HandledReport.builder()
                .action(ReportAction.valueOf(newReportHandled.getReportAction()))
                .reason(newReportHandled.getReason())
                .admin(userRepo.getById(newReportHandled.getAdminId()))//TODO: adminRepo.getAdminById() after merge
                .rideReport(rideReportRepo.getById(newReportHandled.getRideReportId()))
                .build();
        handledReportRepo.save(handledReport);
        return HandledReportDTO.formEntity(handledReport);
    }

    @Override
    public Page<ReportCard> getUserListWithReportCounter(int x, ReportFilter options) {
        switch (options){
            case DRIVER:{
                return driverRepo.getDriversByReportsCountGreaterThan(x,
                        PageRequest.of(1,10, Sort.by("reportsCounter"))//TODO: GET THOSE VALUES
                ).map(driver -> ReportCard.builder()
                        .firstName(driver.getUser().getFirstName())
                        .lastName(driver.getUser().getLastName())
                        .phoneNumber(driver.getUser().getPhoneNumber())
                        .photoURL(driver.getUser().getPhotoUrl())
                        .nbrReport(driver.getReportsCount())
                        .build()
                );
            }
            case PASSENGER:{
                return passengerRepo.getPassengersByReportsCountGreaterThan(x,
                        PageRequest.of(1,10, Sort.by("reportsCounter"))//TODO: GET THOSE VALUES
                ).map(passenger -> ReportCard.builder()
                        .firstName(passenger.getUser().getFirstName())
                        .lastName(passenger.getUser().getLastName())
                        .phoneNumber(passenger.getUser().getPhoneNumber())
                        .photoURL(passenger.getUser().getPhotoUrl())
                        .nbrReport(passenger.getReportsCount())
                        .build()
                );
            }
            case ALL:{
                //TODO: JOIN DRIVE AND PASSENGER
            }
        }
        return null;
    }
}
