package com.yte.intern.project.manageclients.service;

import com.yte.intern.project.manageclients.entity.Client;
import com.yte.intern.project.manageclients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManageClientService {

    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    public Client findByTcKimlikNo(String tcKimlikNo) {
        return clientRepository.findByTcKimlikNo(tcKimlikNo);
    }

    public void deleteByTcKimlikNo(String tcKimlikNo) {
        clientRepository.deleteByTcKimlikNo(tcKimlikNo);
    }

    public Optional<Client> findByUsername(String username) {
        return clientRepository.findByUsername(username);
    }

}
