package com.example.homehub.service.impl;

import com.example.homehub.entity.HouseOwners;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.HouseOwnersRepository;
import com.example.homehub.service.HouseOwnersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.example.homehub.constant.EntitiesConstant.HOUSE_OWNERS;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseOwnersServiceImpl implements HouseOwnersService {

    private final HouseOwnersRepository houseOwnersRepository;

    @Override
    public HouseOwners getOne(UUID id) {
        log.info("GET HOUSEOWNER WITH ID: {}", id);
        return houseOwnersRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("HOUSEOWNER NOT FOUND WITH ID: {}", id);
                    return new IdNotFoundException(HOUSE_OWNERS, id);
                });
    }

    @Override
    public Optional<HouseOwners> findHouseOwnersByHouseId(UUID houseId) {
        log.info("GET HOUSEOWNER WITH HOUSE ID: {}", houseId);
        return houseOwnersRepository.findHouseOwnersByHouseId(houseId);
    }

    @Override
    public Optional<HouseOwners> findHouseOwnersByOwnerId(UUID ownerId) {
        log.info("GET HOUSEOWNER WITH OWNER ID: {}", ownerId);
        return houseOwnersRepository.findHouseOwnersByOwnerId(ownerId);
    }

    @Override
    public List<HouseOwners> getAll() {
        log.info("GET ALL HOUSEOWNERS");
        return houseOwnersRepository.findAll();
    }

    @Override
    public HouseOwners create(HouseOwners houseOwners) {
        log.info("CREATE HOUSEOWNER");
        return houseOwnersRepository.save(HouseOwners.builder()
                .id(UUID.randomUUID())
                .house(houseOwners.getHouse())
                .owner(houseOwners.getOwner())
                .build());
    }

    @Override
    public HouseOwners update(HouseOwners houseOwners, UUID id) {
        return houseOwnersRepository.findById(id)
                .map(existingHouseOwners -> {
                    HouseOwners updatedHouseOwners = existingHouseOwners.toBuilder()
                            .house(houseOwners.getHouse())
                            .owner(houseOwners.getOwner()) .build();
                    log.info("UPDATE HOUSEOWNER WITH ID: {}", id);
                    return houseOwnersRepository.save(updatedHouseOwners);
                })
                .orElseThrow(() -> new IdNotFoundException(HOUSE_OWNERS, id));
    }

    @Override
    public void delete(UUID id) {
        log.info("DELETE HOUSEOWNER WITH ID: {}", id);
        houseOwnersRepository.deleteById(id);
    }

    @Override
    public void deleteByHouseId(UUID houseId) {
        log.info("DELETE HOUSEOWNER BY HOUSE ID: {}", houseId);
        houseOwnersRepository.deleteByHouseId(houseId);
    }

    @Override
    public void deleteByOwnerId(UUID ownerId) {
        log.info("DELETE HOUSEOWNER BY OWNER ID: {}", ownerId);
        houseOwnersRepository.deleteByOwnerId(ownerId);
    }

    @Override
    public void deleteAll() {
        log.info("DELETE ALL HOUSEOWNERS");
        houseOwnersRepository.deleteAll();
    }

}
