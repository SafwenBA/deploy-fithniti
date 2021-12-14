package com.team.fithniti.demo.service;

import com.team.fithniti.demo.dto.request.NewReport;
import com.team.fithniti.demo.dto.request.ReportHandler;
import com.team.fithniti.demo.dto.response.HandledReportDTO;
import com.team.fithniti.demo.dto.response.ReportCard;
import com.team.fithniti.demo.dto.response.ReportSubmitted;
import com.team.fithniti.demo.dto.response.RideReportDTO;
import com.team.fithniti.demo.model.HandledReport;
import com.team.fithniti.demo.model.RideReport;
import com.team.fithniti.demo.util.ReportFilter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ReportService {
    ReportSubmitted createRideReport(NewReport newReport);
    //get all report for specific driver
    Page<RideReportDTO> getAllDriverReportsById(UUID driverId, int page, int size, boolean sorted);//TODO: add default values
    //after the admin handle the report in AdminService the handled report must
    //must be added to ReportHandle table
    HandledReportDTO createHandledReport(ReportHandler newReportHandled);//this method is consumed by admin service while handling report
    //get the list of user with report counter >= x
    List<ReportCard> getUserListWithReportCounter(int x, String options);//TODO: consider having default value
    //get all report for specific passenger
    Page<RideReportDTO> getAllPassengerReportsById(UUID passengerId, int page, int size, boolean sorted);

    //get all handled report by an admin
    List<HandledReportDTO> getAllReportsByAdmin(UUID adminId);

    //get reports list
    List<RideReport> getAllReports();
}