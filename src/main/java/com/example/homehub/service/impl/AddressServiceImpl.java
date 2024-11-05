package com.example.homehub.service.impl;

import com.example.homehub.entity.Address;
import com.example.homehub.entity.House;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.AddressRepository;
import com.example.homehub.service.AddressService;
import com.example.homehub.service.HouseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import static com.example.homehub.constant.EntitiesConstant.ADDRESS;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final HouseService houseService;

    private final org.slf4j.Logger logger =  org.slf4j.LoggerFactory.getLogger(getClass());

    @Override
    public Address getOne(UUID id) {
        logger.info("GET ADDRESS WITH ID: {}", id);
        return addressRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ADDRESS, id));
    }

    @Override
    public List<Address> getAll() {
        logger.info("GET ALL ADDRESSES");
        return addressRepository.findAll();
    }

    @Override
    public Address create(Address address) {
        logger.info("CREATE ADDRESS");
        return addressRepository.save(Address.builder()
                .id(UUID.randomUUID())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .build());
    }

    @Override
    public Address update(Address address, UUID id) {
        return addressRepository.findById(id)
                .map(existingAddress -> {
                    Address updatedAddress = existingAddress.toBuilder()
                            .country(address.getCountry())
                            .city(address.getCity())
                            .street(address.getStreet())
                            .build();
                    logger.info("UPDATE ADDRESS WITH ID: {}", id);
                    return addressRepository.save(updatedAddress);
                })
                .orElseThrow(() -> new IdNotFoundException(ADDRESS, id));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        logger.info("DELETE ADDRESS WITH ID: {}", id);

        List<House> houses = houseService.findAllByAddressId(id);
        if (!houses.isEmpty()) {
            houseService.deleteAllByAddressId(id);
        }

        addressRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        logger.info("DELETE ALL ADDRESSES");
        addressRepository.deleteAll();
    }

}
