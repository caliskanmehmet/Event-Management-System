package com.yte.intern.project.manageenrollment.controller;

import com.yte.intern.project.manageclients.entity.Client;
import com.yte.intern.project.manageclients.service.ManageClientService;
import com.yte.intern.project.manageenrollment.entity.Enrollment;
import com.yte.intern.project.manageenrollment.service.ManageEnrollmentService;
import com.yte.intern.project.manageevents.entity.Event;
import com.yte.intern.project.manageevents.service.ManageEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ManageEnrollmentController {

    private final ManageEnrollmentService manageEnrollmentService;
    private final ManageClientService manageClientService;
    private final ManageEventService manageEventService;

    @GetMapping("/enrollments")
    public List<Enrollment> getEnrollments() {
        return manageEnrollmentService.getEnrollments();
    }

    @PostMapping("/enroll/{username}/{eventKey}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Enrollment enroll(@RequestBody Enrollment enrollment, @PathVariable String username,
                             @PathVariable String eventKey) {
        Client client = manageClientService.findByUsername(username).get();
        //System.out.println("Enrollment client is:" + client);
        Event event = manageEventService.findByEventKey(eventKey);

        if ( manageEnrollmentService.findByEventAndClient(event, client) == null ) {

            event.setQuota(event.getQuota() - 1);

            enrollment.setClient(client);
            enrollment.setEvent(event);

            return manageEnrollmentService.addEnrollment(enrollment);
        }

        return null;
    }

    @GetMapping("/enroll/{username}/{eventKey}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Enrollment getEnrollmentDetails(@PathVariable String username, @PathVariable String eventKey) {
        Client client = manageClientService.findByUsername(username).get();
        Event event = manageEventService.findByEventKey(eventKey);
        
        return manageEnrollmentService.findByEventAndClient(event, client);
    }

    @DeleteMapping("/unroll/{username}/{eventKey}")
    @PreAuthorize("hasRole('USER')")
    public void unroll(@PathVariable String username, @PathVariable String eventKey) {

        Client client = manageClientService.findByUsername(username).get();
        Event event = manageEventService.findByEventKey(eventKey);

        event.setQuota(event.getQuota() + 1);

        //employee.getEnrollments().add(enrollment);
        //event.getEnrollments().add(enrollment);

        //manageEmployeeService.addEmployee(employee);
        //manageEventService.addEvent(event);

        Enrollment enrollment = manageEnrollmentService.findByEventAndClient(event, client);

        manageEnrollmentService.deleteEnrollment(enrollment);


    }
}

// TODO: 21.07.2020 Update operations for each entities 