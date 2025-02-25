package com.emailer.repository;

import com.emailer.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface EmailRepository extends JpaRepository<Email, String> {
    List<Email> findByUserSoeidAndSendTimeBetweenAndStatus(
        String userSoeid, LocalDateTime startDate, LocalDateTime endDate, String status);
} 