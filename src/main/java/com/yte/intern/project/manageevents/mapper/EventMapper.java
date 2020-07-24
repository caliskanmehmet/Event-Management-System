package com.yte.intern.project.manageevents.mapper;

import com.yte.intern.project.manageevents.dto.EventDTO;
import com.yte.intern.project.manageevents.entity.Event;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDTO mapToDto(Event event);

    List<EventDTO> mapToDto(List<Event> eventList);

    Event mapToEntity(EventDTO eventDTO);

    List<Event> mapToEntity(List<EventDTO> eventDTOList);
}
