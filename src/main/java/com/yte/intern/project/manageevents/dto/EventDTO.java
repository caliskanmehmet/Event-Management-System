package com.yte.intern.project.manageevents.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventDTO {

    @Size(min = 3, max = 100, message="Geçersiz etkinlik adı!")
    private String title;

    @FutureOrPresent(message = "Başlangıç tarihi bugün veya daha sonra olmalı!")
    private LocalDateTime beginningTime;

    @AssertTrue(message = "Bitiş tarihi, başlangıç tarihinden sonra olmalı!")
    private LocalDateTime endingTime;

    @Size(min = 8, max = 8, message="Etkinlik anahtarı 8 karakterden oluşmalı!")
    private String eventKey;

    @Min(value = 1, message = "Etkinlik kotası en az 1 olmalı!")
    private int quota;

    @NotNull
    private List<String> questions;

    private boolean isEndingTimeValid() {
        return (endingTime.isAfter(beginningTime));
    }
}
