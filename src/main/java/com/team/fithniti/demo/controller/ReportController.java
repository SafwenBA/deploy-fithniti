package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.controller.api.ReportAPI;
import com.team.fithniti.demo.dto.request.NewReport;
import com.team.fithniti.demo.dto.response.HandledReportDTO;
import com.team.fithniti.demo.dto.response.ReportCard;
import com.team.fithniti.demo.dto.response.ReportSubmitted;
import com.team.fithniti.demo.dto.response.RideReportDTO;
import com.team.fithniti.demo.service.ReportService;
import com.team.fithniti.demo.util.ReportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
public class ReportController implements ReportAPI {

    @Autowired
    private ReportService reportService;


    @Override
    public ReportSubmitted createRideReport(NewReport newReport) {
        return reportService.createRideReport(newReport);
    }

    @Override
    public Page<RideReportDTO> getAllDriverReportsById(UUID driverId, int page, int size, boolean sorted) {
        return reportService.getAllDriverReportsById(driverId, page, size, sorted);
    }

    @Override
    public Page<RideReportDTO> getAllPassengerReportsById(UUID passengerId, int page, int size, boolean sorted) {
        return reportService.getAllPassengerReportsById(passengerId, page, size, sorted);
    }

    @Override
    public Page<ReportCard> getUserListWithReportCounter(int x, String option) {
        return reportService.getUserListWithReportCounter(x, ReportFilter.valueOf(option));
    }

    @Override
    public List<HandledReportDTO> getHandledReportsByAdmin(UUID adminId) {
        return reportService.getAllReportsByAdmin(adminId);
    }
}
