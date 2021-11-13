package com.team.fithniti.demo.service;

import com.team.fithniti.demo.dto.request.NewReport;
import com.team.fithniti.demo.dto.request.ReportHandler;
import com.team.fithniti.demo.dto.response.HandledReportDTO;
import com.team.fithniti.demo.dto.response.ReportCard;
import com.team.fithniti.demo.dto.response.RideReportDTO;
import com.team.fithniti.demo.util.ReportFilter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ReportService {
    RideReportDTO createRideReport(NewReport newReport);
    //get all report for specific driver
    Page<RideReportDTO> getAllDriverReportsById(UUID driverId, int page, int size, boolean sorted);//TODO: add default values
    //after the admin handle the report in AdminService the handled report must
    //must be added to ReportHandle table
    HandledReportDTO createHandledReport(ReportHandler newReportHandled);
    //get the list of user with report counter >= x
    List<ReportCard> getGroupedReports(int x, ReportFilter options);//TODO: consider having default value
}