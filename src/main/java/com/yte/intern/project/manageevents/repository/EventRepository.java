package com.yte.intern.project.manageevents.repository;

import com.yte.intern.project.manageevents.entity.Event;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByEventKey(String eventKey);

    void deleteByEventKey(String eventKey);

    List<Event> findAll(Sort sort);

    boolean existsByEventKey(String eventKey);
}
