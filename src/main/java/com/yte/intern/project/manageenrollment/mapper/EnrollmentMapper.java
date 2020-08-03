package com.yte.intern.project.manageenrollment.mapper;

import com.yte.intern.project.manageenrollment.dto.EnrollmentDTO;
import com.yte.intern.project.manageenrollment.entity.Enrollment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    EnrollmentDTO mapToDto(Enrollment enrollment);

    List<EnrollmentDTO> mapToDto(List<Enrollment> enrollmentList);

    Enrollment mapToEntity(EnrollmentDTO enrollmentDTO);

    List<Enrollment> mapToEntity(List<EnrollmentDTO> enrollmentDTOList);
}
