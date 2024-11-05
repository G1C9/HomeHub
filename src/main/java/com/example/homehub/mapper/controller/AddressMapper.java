package com.example.homehub.mapper.controller;

import com.example.homehub.dto.rq.AddressRq;
import com.example.homehub.dto.rs.AddressRs;
import com.example.homehub.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    AddressRs map(Address address);

    Address map(AddressRq addressRq);

    List<AddressRs> map(List<Address> addresses);

}
