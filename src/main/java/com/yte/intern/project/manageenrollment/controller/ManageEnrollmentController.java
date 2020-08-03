package com.yte.intern.project.manageenrollment.controller;

import com.yte.intern.project.manageclients.entity.Client;
import com.yte.intern.project.manageclients.service.ManageClientService;
import com.yte.intern.project.manageenrollment.entity.Enrollment;
import com.yte.intern.project.manageenrollment.service.ManageEnrollmentService;
import com.yte.intern.project.manageevents.entity.Event;
import com.yte.intern.project.manageevents.service.ManageEventService;
import com.yte.intern.project.security.services.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ManageEnrollmentController {

    private final ManageEnrollmentService manageEnrollmentService;
    private final ManageClientService manageClientService;
    private final ManageEventService manageEventService;
    private final EmailServiceImpl emailService;

    @GetMapping("/enrollments")
    public List<Enrollment> getEnrollments() {
        return manageEnrollmentService.getEnrollments();
    }

    @PostMapping("/enroll/{username}/{eventKey}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Enrollment enroll(@RequestBody Enrollment enrollment, @PathVariable String username,
                             @PathVariable String eventKey) throws Exception {
        Client client = manageClientService.findByUsername(username).get();
        //System.out.println("Enrollment client is:" + client);
        Event event = manageEventService.findByEventKey(eventKey);

        if ( manageEnrollmentService.findByEventAndClient(event, client) == null ) {

            event.setQuota(event.getQuota() - 1);
            event.setParticipantCount(event.getParticipantCount() + 1);

            enrollment.setClient(client);
            enrollment.setEvent(event);
            enrollment.setEnrollmentDate(LocalDate.now());

            String qrCodeText = "Event Title: " + event.getTitle() + " Event Beginning Time: " + event.getBeginningTime() +
                    " Event Ending Time: " + event.getEndingTime() + " Participant full name: " + client.getName() + " " +
                    client.getSurname() + " Participant TC Kimlik No: " + client.getTcKimlikNo();

            //emailService.sendSimpleMessage("adelivodeli1515@hotmail.com", "Deneme", "HMM XD");
            emailService.sendMessageWithAttachment(client.getEmail(), "Event Enrollment",
                    "This QR Code contains some information about the event and user details",
                    qrCodeText);

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
        event.setParticipantCount(event.getParticipantCount() - 1);

        Enrollment enrollment = manageEnrollmentService.findByEventAndClient(event, client);

        manageEnrollmentService.deleteEnrollment(enrollment);


    }
}