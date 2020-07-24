package com.yte.intern.project.manageenrollment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yte.intern.project.common.entity.BaseEntity;
import com.yte.intern.project.manageclients.entity.Client;
import com.yte.intern.project.manageevents.entity.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@SequenceGenerator(name="idgen", sequenceName = "ENROLLMENT_SEQ")
@Table(name="enrollment")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Enrollment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="client_tcKimlikNo")
    //@JsonBackReference
    Client client;

    @ManyToOne
    @JoinColumn(name="event_eventKey")
    //@JsonBackReference
    Event event;

    @ElementCollection
    @CollectionTable(name = "client_enrollment_answers", joinColumns = @JoinColumn(name = "client_tcKimlikNo"))
    @Column(name = "answers")
    List<String> answerForm = new ArrayList<>();

    // ToDo --> database'de event_answers tablosu var. neden?
}
