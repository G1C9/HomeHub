package com.example.homehub.mapper.jooq;

import com.example.homehub.entity.HouseOwners;
import com.example.homehub.tables.records.HouseOwnersRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JooqHouseOwnersMapper {

    @Mapping(target = "house.id", source = "houseId")
    @Mapping(target = "owner.id", source = "ownerId")
    HouseOwners map(HouseOwnersRecord houseOwnersRecord);

}
