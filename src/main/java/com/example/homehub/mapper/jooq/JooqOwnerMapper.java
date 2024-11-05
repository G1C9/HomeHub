package com.example.homehub.mapper.jooq;

import com.example.homehub.entity.Owner;
import com.example.homehub.tables.records.OwnerRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JooqOwnerMapper {

    @Mapping(target = "passport.id", source = "passportId")
    Owner map(OwnerRecord ownerRecord);

}
