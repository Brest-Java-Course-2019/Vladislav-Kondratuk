package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.Car;
import com.epam.brest.courses.rc.stub.CarStub;

import java.util.Optional;
import java.util.stream.Stream;

public interface CarDao {

    Stream<Car> findAll();

    Stream<CarStub> findAllStubs();

    Optional<Car> findById(Integer carId);

    Optional<CarStub> findStubById(Integer carId);

    Optional<Car> add(Car car);

    void update(Car car);

    void delete(int carId);
}
