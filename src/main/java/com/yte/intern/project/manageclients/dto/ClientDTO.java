package com.yte.intern.project.manageclients.dto;

import com.yte.intern.project.manageclients.validation.TcKimlikNo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Getter
@Setter
public class ClientDTO {

    @Size(min = 2, max = 30, message = "İsim geçersiz!")
    private String name;

    @Size(min = 2, max = 30, message = "Soyisim geçersiz!")
    private String surname;

    @Email(message = "Mail geçersiz!")
    private String email;

    private String tcKimlikNo;

}
