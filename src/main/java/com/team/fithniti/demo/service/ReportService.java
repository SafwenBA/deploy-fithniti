package com.team.fithniti.demo.service;

import com.team.fithniti.demo.dto.request.NewReport;
import com.team.fithniti.demo.dto.request.ReportHandler;
import com.team.fithniti.demo.dto.response.HandledReportDTO;
import com.team.fithniti.demo.dto.response.RideReportDTO;
import com.team.fithniti.demo.model.HandledReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReportService {
    //user reports a drive: i am not into this shit
    RideReportDTO createRideReport(NewReport report);

    //get all reports for admin
    Page<RideReportDTO> getAllReportsForAdmin(int page, int size);//TODO: maybe remove ForAdmin suffix

    //get all reports related to user
    Page<RideReportDTO> getAllReportsForUser(UUID passenger, int page, int size);//TODO: same goes here for ForUser

    //admin action about report
    HandledReportDTO doSomethingAboutThisReport(ReportHandler reportHandler);

    //Not sure about this : user delete his own report
    //Response deleteReport(); Response is new dto that contains payload(data returned) + status(exp: ok) + errors and so on;

    //for super admin so he can see all handled reports by an admin
    Page<HandledReportDTO> getAllHandledReportsByAdminId(UUID adminId, int page, int size);
}