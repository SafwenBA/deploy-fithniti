package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.HandledReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HandledReportRepo extends JpaRepository<HandledReport, Long> {
    List<HandledReport> findAllByAdmin(AppUser admin, Pageable pageable);
}
