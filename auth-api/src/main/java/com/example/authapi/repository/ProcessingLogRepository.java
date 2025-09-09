package com.example.authapi.repository;

import com.example.authapi.ProcessingLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessingLogRepository extends JpaRepository<ProcessingLog, Long> {

}