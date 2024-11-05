package com.example.homehub.mapper.controller;

import com.example.homehub.dto.rq.CarRq;
import com.example.homehub.dto.rs.CarRs;
import com.example.homehub.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    CarRs map(Car car);

    @Mapping(target = "owner.id", source = "ownerId")
    Car map(CarRq carRq);

    List<CarRs> map(List<Car> cars);

}
