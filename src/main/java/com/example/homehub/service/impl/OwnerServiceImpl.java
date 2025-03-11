package com.example.homehub.service.impl;

import com.example.homehub.entity.Car;
import com.example.homehub.entity.HouseOwners;
import com.example.homehub.entity.Owner;
import com.example.homehub.entity.Passport;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.OwnerRepository;
import com.example.homehub.service.CarService;
import com.example.homehub.service.HouseOwnersService;
import com.example.homehub.service.OwnerService;
import com.example.homehub.service.PassportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static com.example.homehub.constant.EntitiesConstant.OWNER;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    private final PassportService passportService;

    private final CarService carService;

    private final HouseOwnersService houseOwnersService;

    @Override
    public Owner getOne(UUID id) {
        log.info("GET OWNER WITH ID: {}", id);
        return ownerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("OWNER NOT FOUND WITH ID: {}", id);
                    return new IdNotFoundException(OWNER, id);
                });
    }

    @Override
    public List<Owner> getByStreet(String s) {
        return ownerRepository.findByStreet(s);
    }

    @Override
    public List<Owner> findMaleWithCarAndHouse() {
        return ownerRepository.findMaleWithCarAndHouse();
    }

    @Override
    public List<Owner> getAll() {
        log.info("GET ALL OWNERS");
        return ownerRepository.findAll();
    }

    @Override
    @Transactional
    public Owner create(Owner owner) {
        log.info("CREATE OWNER WITH PASSPORT");
        Passport passport = new Passport();
        passport = passportService.create(passport);
        return ownerRepository.save(Owner.builder()
                .id(UUID.randomUUID())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .gender(owner.getGender())
                .passport(passport)
                .build());
    }

    @Override
    public Owner update(Owner owner, UUID id) {
        return ownerRepository.findById(id)
                .map(existingOwner -> {
                    Owner updatedOwner = existingOwner.toBuilder()
                            .firstName(owner.getFirstName())
                            .lastName(owner.getLastName())
                            .gender(owner.getGender())
                            .passport(owner.getPassport())
                            .build();
                    log.info("UPDATE OWNER WITH ID: {}", id);
                    return ownerRepository.save(updatedOwner);
                })
                .orElseThrow(() -> new IdNotFoundException(OWNER, id));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("DELETE OWNER");

        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(OWNER, id));

        passportService.delete(owner.getPassport().getId());

        Optional<HouseOwners> houseOwners = houseOwnersService.findHouseOwnersByOwnerId(id);
        if (houseOwners.isPresent()) {
            houseOwnersService.deleteByOwnerId(id);
        }

        List<Car> cars = carService.findAllByOwnerId(owner.getId());
        if (!cars.isEmpty()) {
            carService.deleteAllByOwnerId(owner.getId());
        }

        ownerRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        log.info("DELETE ALL OWNERS");
        ownerRepository.deleteAll();
    }

}
