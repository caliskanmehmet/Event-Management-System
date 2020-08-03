package com.yte.intern.project.manageevents.controller;

import com.yte.intern.project.manageclients.dto.ClientDTO;
import com.yte.intern.project.manageclients.entity.Client;
import com.yte.intern.project.manageclients.mapper.ClientMapper;
import com.yte.intern.project.manageenrollment.dto.EnrollmentDTO;
import com.yte.intern.project.manageenrollment.entity.Enrollment;
import com.yte.intern.project.manageenrollment.mapper.EnrollmentMapper;
import com.yte.intern.project.manageenrollment.service.ManageEnrollmentService;
import com.yte.intern.project.manageevents.dto.EventDTO;
import com.yte.intern.project.manageevents.entity.Event;
import com.yte.intern.project.manageevents.mapper.EventMapper;
import com.yte.intern.project.manageevents.service.ManageEventService;
import com.yte.intern.project.security.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.mail.Part;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ManageEventController {

    private final ManageEventService manageEventService;
    private final ManageEnrollmentService manageEnrollmentService;
    private final EventMapper eventMapper;
    private final ClientMapper clientMapper;
    private final EnrollmentMapper enrollmentMapper;

    @GetMapping("/events")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<EventDTO> getEvents() {
        List<Event> events = manageEventService.getAllEvents();
        return eventMapper.mapToDto(events);
    }

    @PostMapping("/events/add")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addEvent(@Valid @RequestBody EventDTO eventDTO) {
        if (manageEventService.existsByEventKey(eventDTO.getEventKey())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Etkinlik Anahtarı zaten kullanımda!"));
        }

        Event event = eventMapper.mapToEntity(eventDTO);
        manageEventService.addEvent(event);

        return ResponseEntity.ok(new MessageResponse("Event created successfully!"));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/events/delete/{eventKey}")
    public void deleteEvent(@PathVariable String eventKey) {
        Event event = manageEventService.findByEventKey(eventKey);

        //event.setEnrollments(null);

        manageEventService.deleteByEventKey(eventKey);
    }

    @GetMapping("/events/getClients/{eventKey}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ClientDTO> getClients(@PathVariable String eventKey) {
        List<ClientDTO> clientsOfEvent = new ArrayList<>();

        Event event = manageEventService.findByEventKey(eventKey);
        Set<Enrollment> enrollmentSet = event.getEnrollments();

        enrollmentSet.forEach( (enrollment) -> {
            Client client = enrollment.getClient();
            ClientDTO clientDTO = clientMapper.mapToDto(client);
            clientsOfEvent.add(clientDTO);
        } );

        return clientsOfEvent;
    }

    @GetMapping("/events/getEnrollments/{eventKey}")
    @PreAuthorize("hasRole('ADMIN')")
    public Set<ParticipantCount> getEnrollments(@PathVariable String eventKey) {

        Event event = manageEventService.findByEventKey(eventKey);
        Set<Enrollment> enrollmentSet = event.getEnrollments();

        List<EnrollmentDTO> enrollmentDTOList = new ArrayList<>();
        HashMap<LocalDate, Integer> hashMap = new HashMap<>();
        Set<ParticipantCount> participantCountSet = new HashSet<>();

        enrollmentSet.forEach(e -> {
            EnrollmentDTO enrollmentDTO = enrollmentMapper.mapToDto(e);
            enrollmentDTOList.add(enrollmentDTO);
        });

        Collections.sort(enrollmentDTOList);

        enrollmentDTOList.forEach(e -> {
            hashMap.put(e.getEnrollmentDate(), hashMap.getOrDefault(e.getEnrollmentDate(), 0) + 1);
        });

        hashMap.forEach( (date, count) -> {
            ParticipantCount participantCount = new ParticipantCount(count, date);
            participantCountSet.add(participantCount);
        });

        return participantCountSet;
    }

    @GetMapping("/events/getClientsInfo/{eventKey}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<EnrollmentDTO> getClientsInfo(@PathVariable String eventKey) {
        List<EnrollmentDTO> enrollmentDTOList = new ArrayList<>();

        Event event = manageEventService.findByEventKey(eventKey);
        Set<Enrollment> enrollmentSet = event.getEnrollments();

        enrollmentSet.forEach(e -> {
            EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
            enrollmentDTO.setAnswerForm(e.getAnswerForm());
            enrollmentDTO.setClientDTO(clientMapper.mapToDto(e.getClient()));

            enrollmentDTOList.add(enrollmentDTO);
        });

        return enrollmentDTOList;
    }

    @PutMapping("/events/{eventKey}") // @Valid eklendi :)
    public EventDTO updateEvent(@Valid @RequestBody EventDTO eventDTO, @PathVariable String eventKey) {

        Event event = manageEventService.findByEventKey(eventKey);

        event.setTitle(eventDTO.getTitle());
        event.setBeginningTime(eventDTO.getBeginningTime());
        event.setEndingTime(eventDTO.getEndingTime());
        event.setEventKey(eventDTO.getEventKey());
        event.setQuota(eventDTO.getQuota());
        event.setQuestions(eventDTO.getQuestions());

        manageEventService.addEvent(event);

        EventDTO eventDTO1 = eventMapper.mapToDto(event);

        return eventDTO1;
    }

    @GetMapping("/events/getClientCount/{eventKey}")
    @PreAuthorize("hasRole('ADMIN')")
    public int getClientCount(@PathVariable String eventKey) {
        Event event = manageEventService.findByEventKey(eventKey);

        return event.getEnrollments().size();
    }

}
/* Event ekleme ✓
*  Event silme
*  Evente kayıt olma ✓ */