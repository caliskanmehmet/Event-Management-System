package com.yte.intern.project.manageevents.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yte.intern.project.common.entity.BaseEntity;
import com.yte.intern.project.manageenrollment.entity.Enrollment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "EVENT_SEQ")
@Table(name = "events")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Event extends BaseEntity {

    @Column(name = "TITLE")
    private String title;

    @Column(name = "BEGINNING_DATE")
    private ZonedDateTime beginningTime;

    @Column(name = "END_DATE")
    private ZonedDateTime endingTime;

    @Column(name = "EVENT_KEY", unique = true) // this key will be written by user
    private String eventKey;

    @Column(name = "QUOTA")
    private int quota;

    @Column(name = "PARTICIPANT_COUNT")
    private int participantCount;

    @Column(name = "QUESTIONS")
    @ElementCollection
    private List<String> questions = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade=CascadeType.ALL, orphanRemoval=true)
    //@JsonIgnore
    Set<Enrollment> enrollments = new HashSet<>();
}
