package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.date.AddDateInterval;
import com.epam.brest.courses.rc.model.Client;
import com.epam.brest.courses.rc.stub.ClientStub;

import java.util.Optional;
import java.util.stream.Stream;

public interface ClientDao {

    Stream<Client> findAll();

    Stream<ClientStub> findAllStubs();

    Optional<Client> findById(Integer clientId);

    Optional<ClientStub> findStubById(Integer clientId);

    Optional<Client> add(Client client);

    Stream<ClientStub> findStubByDate(AddDateInterval interval);

    void update(Client client);

    void delete(int clientId);

}
