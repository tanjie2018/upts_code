package com.upi.upts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upi.upts.model.Report;

@Transactional
@Repository
public interface ReportRepository extends JpaRepository<Report, String> {

}
