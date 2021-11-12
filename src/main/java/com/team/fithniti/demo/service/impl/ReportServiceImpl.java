package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.dto.request.NewReport;
import com.team.fithniti.demo.dto.request.ReportHandler;
import com.team.fithniti.demo.dto.response.HandledReportDTO;
import com.team.fithniti.demo.dto.response.RideReportDTO;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.*;
import com.team.fithniti.demo.repository.*;
import com.team.fithniti.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
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
    private UserRepo userRepo;

    @Override
    public RideReportDTO createRideReport(NewReport report) {
        Optional<Ride> ride = rideRepo.findById(report.getRideId());
        if(ride.isEmpty())
            throw new ResourceNotFound("ride does not exist");//TODO: maybe refactor
        Optional<Passenger> passenger = passengerRepo.findById(report.getPassengerId());
        if(passenger.isEmpty())
            throw new ResourceNotFound("user does not exist");//TODO: same
        RideReport rideReport = RideReport.builder()
                .ride(ride.get())
                .passenger(passenger.get())
                .reportedBy(report.getReportedBy())
                .reportTypes(new ArrayList<>(report.getReportTypes()))//TODO: CHECK IF THIS IS RIGHT
                .build();
        rideReportRepo.save(rideReport);
        return RideReportDTO.fromEntity(rideReport);
    }

    @Override
    public Page<RideReportDTO> getAllReportsForAdmin(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return rideReportRepo.findAll(pageable).map(RideReportDTO::fromEntity);//TODO: check why other function where pretty much fucked up
    }

    @Override
    public Page<RideReportDTO> getAllReportsForUser(UUID passengerId, int page, int size) {
//        Optional<AppUser> user = userRepo.findById(passengerId);
//        if(user.isEmpty())
//            throw new ResourceNotFound("user not found");
//        return (Page<RideReportDTO>) rideReportRepo.findAllByPassenger(passenger.get(), PageRequest.of(page,size)).stream().map(RideReportDTO::fromEntity).collect(Collectors.toList());
        //TODO: this is not correct it should be user (driver or passenger) who can get his reports => RideReport fucked up really hard
        return null;
    }

    @Override
    public HandledReportDTO doSomethingAboutThisReport(ReportHandler reportHandler) {
        Optional<RideReport> rideReport = rideReportRepo.findById(reportHandler.getRideReportId());
        if(rideReport.isEmpty())
            throw new ResourceNotFound("report does not exist");
        Optional<AppUser> admin = userRepo.findById(reportHandler.getAdminId());
        if(admin.isEmpty())
            throw new ResourceNotFound("admin not found");
        //TODO: should be there a method isAdmin
        switch (reportHandler.getReportAction()){
           //TODO: add methods in AppUser repo for updating state
        }
        HandledReport handledReport = HandledReport.builder()
                .action(reportHandler.getReportAction())
                .reason(reportHandler.getReason())
                .admin(admin.get())
                .rideReport(rideReport.get())
                .build();
        handledReportRepo.save(handledReport);
        return HandledReportDTO.formEntity(handledReport);
    }

    @Override
    public Page<HandledReportDTO> getAllHandledReportsByAdminId(UUID adminId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Optional<AppUser> admin = userRepo.findById(adminId);
        if(admin.isEmpty())
            throw new ResourceNotFound("admin not found");
        //TODO: should be there a method isAdmin
        //TODO: extract this code cuz its repetitive
        return (Page<HandledReportDTO>) handledReportRepo.findAllByAdmin(admin.get(), pageable).stream().map(HandledReportDTO::formEntity).collect(Collectors.toList());
    }//TODO: not sure if this cast is safe
}
