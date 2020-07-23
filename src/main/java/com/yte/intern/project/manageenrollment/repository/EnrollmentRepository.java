package com.yte.intern.project.manageenrollment.repository;

import com.yte.intern.project.manageclients.entity.Client;
import com.yte.intern.project.manageenrollment.entity.Enrollment;
import com.yte.intern.project.manageevents.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Enrollment findByEventAndClient(Event event, Client client);
}
