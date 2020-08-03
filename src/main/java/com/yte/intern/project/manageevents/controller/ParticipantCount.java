package com.yte.intern.project.manageevents.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ParticipantCount {

    private Integer participantCount;
    private LocalDate localDate;
}
