package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.dto.request.NewReport;
import com.team.fithniti.demo.dto.response.HandledReportDTO;
import com.team.fithniti.demo.dto.response.ReportCard;
import com.team.fithniti.demo.dto.response.ReportSubmitted;
import com.team.fithniti.demo.dto.response.RideReportDTO;
import com.team.fithniti.demo.util.ReportFilter;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api
@RequestMapping("/report")
public interface ReportAPI {

    @PostMapping("")
    ReportSubmitted createRideReport(@RequestBody NewReport newReport);

    @GetMapping("/driver/{driverId}")
    Page<RideReportDTO> getAllDriverReportsById(@PathVariable("driverId") UUID driverId,@RequestParam int page,@RequestParam int size ,@RequestParam boolean sorted);

    @GetMapping("/passenger/{passengerId}")
    Page<RideReportDTO> getAllPassengerReportsById(@PathVariable("passengerId") UUID passengerId,@RequestParam int page,@RequestParam int size ,@RequestParam boolean sorted);

    @GetMapping("")
    Page<ReportCard> getUserListWithReportCounter(@RequestParam int x,@RequestParam String option);

    @GetMapping("/admin/{adminId}")
    List<HandledReportDTO> getHandledReportsByAdmin(@PathVariable("adminId") UUID adminId);
}
