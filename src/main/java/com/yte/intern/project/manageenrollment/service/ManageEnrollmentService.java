package com.yte.intern.project.manageenrollment.service;

import com.yte.intern.project.manageclients.entity.Client;
import com.yte.intern.project.manageenrollment.entity.Enrollment;
import com.yte.intern.project.manageenrollment.repository.EnrollmentRepository;
import com.yte.intern.project.manageevents.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ManageEnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public List<Enrollment> getEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment addEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void deleteEnrollment(Enrollment enrollment) {
        enrollmentRepository.delete(enrollment);
    }

    public void deleteAllEnrollments(Set<Enrollment> enrollmentSet) {
        enrollmentRepository.deleteAll(enrollmentSet);
    }

    public Enrollment findByEventAndClient(Event event, Client client) {
        return enrollmentRepository.findByEventAndClient(event, client);
    }

}
