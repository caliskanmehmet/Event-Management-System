package com.yte.intern.project.manageenrollment.repository;

import com.yte.intern.project.manageclients.entity.Client;
import com.yte.intern.project.manageenrollment.entity.Enrollment;
import com.yte.intern.project.manageevents.entity.Event;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Enrollment findByEventAndClient(Event event, Client client);
    
    Integer countAllByEnrollmentDate(LocalDate date);

}
