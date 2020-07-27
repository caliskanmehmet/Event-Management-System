package com.yte.intern.project.manageenrollment.dto;

import com.yte.intern.project.manageclients.dto.ClientDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class EnrollmentDTO {

    private ClientDTO clientDTO;

    private List<String> answerForm;

}