package com.example.homehub.mapper.jooq;

import com.example.homehub.entity.House;
import com.example.homehub.tables.records.HouseRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JooqHouseMapper {

    @Mapping(target = "address.id", source = "addressId")
    House map(HouseRecord houseRecord);

}
