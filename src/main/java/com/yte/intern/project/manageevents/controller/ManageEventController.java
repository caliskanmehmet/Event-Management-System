package com.yte.intern.project.manageevents.controller;

import com.yte.intern.project.manageclients.dto.ClientDTO;
import com.yte.intern.project.manageclients.entity.Client;
import com.yte.intern.project.manageclients.mapper.ClientMapper;
import com.yte.intern.project.manageenrollment.entity.Enrollment;
import com.yte.intern.project.manageenrollment.service.ManageEnrollmentService;
import com.yte.intern.project.manageevents.dto.EventDTO;
import com.yte.intern.project.manageevents.entity.Event;
import com.yte.intern.project.manageevents.mapper.EventMapper;
import com.yte.intern.project.manageevents.service.ManageEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ManageEventController {

    private final ManageEventService manageEventService;
    private final ManageEnrollmentService manageEnrollmentService;
    private final EventMapper eventMapper;
    private final ClientMapper clientMapper;

    @GetMapping("/events")
    public List<EventDTO> getEvents() {
        List<Event> events = manageEventService.getAllEvents();
        return eventMapper.mapToDto(events);
    }

    @PostMapping("/events/add") // @Valid ToDO EventDTO mu dönmeli yoksa Event mi?
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Event addEvent(@RequestBody EventDTO eventDTO) {
        Event event = eventMapper.mapToEntity(eventDTO);
        manageEventService.addEvent(event);
        return event;
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

    //@DeleteMapping("/events/{eventKey}/delete/{tcKimlikNo}")
    //public void deleteClient()
}
/* Event ekleme ✓
*  Event silme
*  Evente kayıt olma ✓ */