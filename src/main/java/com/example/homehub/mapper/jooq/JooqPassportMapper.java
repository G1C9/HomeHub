package com.example.homehub.mapper.jooq;

import com.example.homehub.entity.Passport;
import com.example.homehub.tables.records.PassportRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JooqPassportMapper {

    Passport map(PassportRecord passportRecord);

}
