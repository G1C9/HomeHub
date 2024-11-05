package com.example.homehub.service;

import com.example.homehub.entity.Owner;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface OwnerService {

    Owner getOne(UUID id);

    Owner getByStreet(@Param("street") String s);

    List<Owner> findMaleWithCarAndHouse();

    List<Owner> getAll();

    Owner create(Owner owner);

    Owner update(Owner owner, UUID id);

    void delete(UUID id);

    void deleteAll();

}
