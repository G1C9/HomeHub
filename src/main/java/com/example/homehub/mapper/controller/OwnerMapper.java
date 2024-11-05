package com.example.homehub.mapper.controller;

import com.example.homehub.dto.rq.OwnerRq;
import com.example.homehub.dto.rs.OwnerRs;
import com.example.homehub.entity.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper {

    @Mapping(target = "passport", source = "passport")
    OwnerRs map(Owner owner);

    Owner map(OwnerRq ownerRq);

    List<OwnerRs> map(List<Owner> owners);

}
