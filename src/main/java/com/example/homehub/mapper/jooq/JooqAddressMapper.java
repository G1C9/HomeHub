package com.example.homehub.mapper.jooq;

import com.example.homehub.entity.Address;
import com.example.homehub.tables.records.AddressRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JooqAddressMapper {

    Address map(AddressRecord addressRecord);

}
