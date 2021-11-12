package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.model.ReportType;
import com.team.fithniti.demo.service.ReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class ReportTypeController {

    @Autowired
    private ReportTypeService reportTypeService;

    @GetMapping("/report-types")
    public ResponseEntity<List<ReportType>> getAllReportTypes() {
        List<ReportType> reportTypes = reportTypeService.getAllReportTypes();
        return new ResponseEntity<>(reportTypes, HttpStatus.OK);
    }

    @GetMapping("/report-types/{id}")
    public ResponseEntity<ReportType> getReportTypeById(@PathVariable Long id) {
        ReportType reportType = reportTypeService.getReportType(id);
        return new ResponseEntity<>(reportType, HttpStatus.OK);
    }

    @PostMapping("/report-types")
    public ResponseEntity<String> addReportType(@RequestBody ReportType reportType) {
        reportTypeService.create(reportType);
        return new ResponseEntity<>("New report type added", HttpStatus.CREATED);
    }

    @PutMapping("/report-types/{id}")
    public ResponseEntity<String> updateReportType(@PathVariable("id") Long id, @RequestBody ReportType reportType) {
        reportTypeService.update(id, reportType);
        return new ResponseEntity<>("Report type updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/report-types/{id}")
    public ResponseEntity<String> deleteReportType(@PathVariable("id") Long id) {
        reportTypeService.delete(id);
        return new ResponseEntity<>("Report type deleted successfully", HttpStatus.OK);
    }
}
