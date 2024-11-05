package com.example.homehub.mapper.jooq;

import com.example.homehub.entity.Car;
import com.example.homehub.tables.records.CarRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JooqCarMapper {

    @Mapping(target = "owner.id", source = "ownerId")
    Car map(CarRecord carRecord);

}
