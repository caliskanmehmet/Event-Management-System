package com.yte.intern.project.manageenrollment.dto;

import com.yte.intern.project.manageclients.dto.ClientDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EnrollmentDTO implements Comparable<EnrollmentDTO> {

    private ClientDTO clientDTO;

    private List<String> answerForm;

    private LocalDate enrollmentDate;

    @Override
    public int compareTo(EnrollmentDTO enrollmentDTO) {
        return enrollmentDTO.getEnrollmentDate().hashCode();
    }
}