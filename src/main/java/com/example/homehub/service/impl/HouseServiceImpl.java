package com.example.homehub.service.impl;

import com.example.homehub.entity.House;
import com.example.homehub.entity.HouseOwners;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.HouseRepository;
import com.example.homehub.service.HouseOwnersService;
import com.example.homehub.service.HouseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.example.homehub.constant.EntitiesConstant.HOUSE;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository jpaHouseRepository;

    private final HouseOwnersService houseOwnersService;

    private final org.slf4j.Logger logger =  org.slf4j.LoggerFactory.getLogger(getClass());

    @Override
    public House getOne(UUID id) {
        logger.info("GET HOUSE WITH ID: {}", id);
        return jpaHouseRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(HOUSE, id));
    }

    @Override
    public List<House> getAll() {
        logger.info("GET ALL ADDRESSES");
        return jpaHouseRepository.findAll();
    }

    @Override
    public List<House> findAllByAddressId(UUID addressId) {
        logger.info("GET HOUSE WITH ADDRESS ID: {}", addressId);
        return jpaHouseRepository.findAllByAddressId(addressId);
    }

    @Override
    public House create(House house) {
        logger.info("CREATE HOUSE");
        return jpaHouseRepository.save(House.builder()
                .id(UUID.randomUUID())
                .number(house.getNumber())
                .square(house.getSquare())
                .address(house.getAddress())
                .build());
    }

    @Override
    public House update(House house, UUID id) {
        return jpaHouseRepository.findById(id)
                .map(existingHouse -> {
                    House updatedHouse = existingHouse.toBuilder()
                            .number(house.getNumber())
                            .square(house.getSquare())
                            .address(house.getAddress())
                            .build();
                    logger.info("UPDATE HOUSE WITH ID: {}", id);
                    return jpaHouseRepository.save(updatedHouse);
                })
                .orElseThrow(() -> new IdNotFoundException(HOUSE, id));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        logger.info("DELETE HOUSE WITH ID: {}", id);
        Optional<HouseOwners> houseOwners = houseOwnersService.findHouseOwnersByHouseId(id);
        if (houseOwners.isPresent()) {
            houseOwnersService.deleteByHouseId(id);
        }
        jpaHouseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        logger.info("DELETE ALL HOUSES");
        jpaHouseRepository.deleteAll();
    }

    @Override
    public void deleteAllByAddressId(UUID addressId) {
        logger.info("DELETE ALL HOUSES BY ADDRESS WITH ID: {}", addressId);
        jpaHouseRepository.deleteAllByAddressId(addressId);
    }
}
