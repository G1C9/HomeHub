package com.example.homehub.mapper.controller;

import com.example.homehub.dto.rq.HouseRq;
import com.example.homehub.dto.rs.HouseRs;
import com.example.homehub.entity.House;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HouseMapper {

    @Mapping(target = "addressId", source = "address.id")
    HouseRs map(House house);

    @Mapping(target = "address.id", source = "addressId")
    House map(HouseRq houseRq);

    List<HouseRs> map(List<House> houses);

}
