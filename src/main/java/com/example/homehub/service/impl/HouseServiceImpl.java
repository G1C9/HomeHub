package com.example.homehub.service.impl;

import com.example.homehub.entity.House;
import com.example.homehub.entity.HouseOwners;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.HouseRepository;
import com.example.homehub.service.HouseOwnersService;
import com.example.homehub.service.HouseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.example.homehub.constant.EntitiesConstant.HOUSE;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    private final HouseOwnersService houseOwnersService;

    @Override
    public House getOne(UUID id) {
        log.info("GET HOUSE WITH ID: {}", id);
        return houseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("HOUSE NOT FOUND WITH ID: {}", id);
                    return new IdNotFoundException(HOUSE, id);
                });
    }

    @Override
    public List<House> getAll() {
        log.info("GET ALL ADDRESSES");
        return houseRepository.findAll();
    }

    @Override
    public List<House> findAllByAddressId(UUID addressId) {
        log.info("GET HOUSE WITH ADDRESS ID: {}", addressId);
        return houseRepository.findAllByAddressId(addressId);
    }

    @Override
    public House create(House house) {
        log.info("CREATE HOUSE");
        return houseRepository.save(House.builder()
                .id(UUID.randomUUID())
                .number(house.getNumber())
                .square(house.getSquare())
                .address(house.getAddress())
                .build());
    }

    @Override
    public House update(House house, UUID id) {
        return houseRepository.findById(id)
                .map(existingHouse -> {
                    House updatedHouse = existingHouse.toBuilder()
                            .number(house.getNumber())
                            .square(house.getSquare())
                            .address(house.getAddress())
                            .build();
                    log.info("UPDATE HOUSE WITH ID: {}", id);
                    return houseRepository.save(updatedHouse);
                })
                .orElseThrow(() -> new IdNotFoundException(HOUSE, id));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("DELETE HOUSE WITH ID: {}", id);
        Optional<HouseOwners> houseOwners = houseOwnersService.findHouseOwnersByHouseId(id);
        if (houseOwners.isPresent()) {
            houseOwnersService.deleteByHouseId(id);
        }
        houseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        log.info("DELETE ALL HOUSES");
        houseRepository.deleteAll();
    }

    @Override
    public void deleteAllByAddressId(UUID addressId) {
        log.info("DELETE ALL HOUSES BY ADDRESS WITH ID: {}", addressId);
        houseRepository.deleteAllByAddressId(addressId);
    }
}
