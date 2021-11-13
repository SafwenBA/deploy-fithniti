package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.dto.request.NewReport;
import com.team.fithniti.demo.dto.request.ReportHandler;
import com.team.fithniti.demo.dto.response.HandledReportDTO;
import com.team.fithniti.demo.dto.response.ReportCard;
import com.team.fithniti.demo.dto.response.RideReportDTO;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.*;
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

import java.util.List;
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

    @Autowired
    private ReportTypeRepo reportTypeRepo;

    @Autowired
    private DriverRepo driverRepo;

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
        //existence check
        Optional<AppUser> driver = userRepo.findById(driverId);
        if(driver.isEmpty()) throw new ResourceNotFound("driver not even an app user");
        if(driverRepo.findByUser(driver.get()).isEmpty()) throw new ResourceNotFound("driver not found");
        Pageable pageable = sorted ? PageRequest.of(page, size, Sort.by("createdDate")) : PageRequest.of(page, size);
        return rideReportRepo.findAllByDriver(driverId, pageable).map(RideReportDTO::fromEntity);
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
    public List<ReportCard> getGroupedReports(int x, ReportFilter options) {
        switch (options){
            case DRIVER:{

            }
            case PASSENGER:{

            }
            case ALL:{

            }
        }
        return null;
    }
}
