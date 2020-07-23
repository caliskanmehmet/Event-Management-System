package com.yte.intern.project.manageclients.mapper;

import com.yte.intern.project.manageclients.dto.ClientDTO;
import com.yte.intern.project.manageclients.entity.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO mapToDto(Client client);

    List<ClientDTO> mapToDto(List<Client> clientList);

    Client mapToEntity(ClientDTO clientDTO);

    List<Client> mapToEntity(List<ClientDTO> clientDTOList);
}
