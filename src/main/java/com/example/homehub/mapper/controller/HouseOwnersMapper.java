package com.example.homehub.mapper.controller;

import com.example.homehub.dto.rq.HouseOwnersRq;
import com.example.homehub.dto.rs.HouseOwnersRs;
import com.example.homehub.entity.HouseOwners;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HouseOwnersMapper {

    @Mapping(target = "houseId", source = "house.id")
    @Mapping(target = "ownerId", source = "owner.id")
    HouseOwnersRs map(HouseOwners houseOwners);

    @Mapping(target = "house.id", source = "houseId")
    @Mapping(target = "owner.id", source = "ownerId")
    HouseOwners map(HouseOwnersRq houseOwnersRq);

    List<HouseOwnersRs> map(List<HouseOwners> houseOwners);

}
