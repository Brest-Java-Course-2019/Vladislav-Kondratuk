package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.Client;

import java.util.Optional;
import java.util.stream.Stream;

public interface ClientDao {

    Stream<Client> findAll();

    Optional<Client> findById(Integer clientId);

    Optional<Client> add(Client client);

    void update(Client client);

    void delete(int clientId);

}
