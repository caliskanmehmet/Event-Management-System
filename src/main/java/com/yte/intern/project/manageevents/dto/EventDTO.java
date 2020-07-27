package com.yte.intern.project.manageevents.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class EventDTO {

    @Size(min = 3, max = 100, message="Geçersiz etkinlik adı!")
    private String title;

    private ZonedDateTime beginningTime;

    private ZonedDateTime endingTime;

    @Size(min = 8, max = 8, message="Etkinlik anahtarı 8 karakterden oluşmalı!")
    private String eventKey;

    @Min(value = 1, message = "Etkinlik kotası en az 1 olmalı!")
    private int quota;

    @NotNull
    private List<String> questions;

    @AssertTrue(message = "Bitiş tarihi, başlangıç tarihinden sonra olmalı!")
    private boolean isEndingTimeValid() {
        return (endingTime.isAfter(beginningTime));
    }

    @AssertTrue(message = "Başlangıç tarihi, şimdiden sonra olmalı!")
    private boolean isBeginningTimeValid() {
        System.out.println("beginning time" + beginningTime );
        System.out.println("now" + ZonedDateTime.now());
        return (beginningTime.isAfter(ZonedDateTime.now()));
    }
}
