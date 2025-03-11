package com.example.homehub.repository.jooq;

import com.example.homehub.entity.Address;
import com.example.homehub.entity.House;
import com.example.homehub.mapper.jooq.JooqHouseMapper;
import com.example.homehub.repository.HouseRepository;
import com.example.homehub.tables.records.HouseRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.example.homehub.Tables.ADDRESS;
import static com.example.homehub.Tables.HOUSE;

@Repository
@Profile("jooq")
@RequiredArgsConstructor
public class JooqHouseRepository implements HouseRepository {

    private final DSLContext dslContext;

    private final JooqHouseMapper houseMapper;

    @Override
    public Optional<House> findById(UUID id) {
        return dslContext.select(
                HOUSE.ID,
                HOUSE.NUMBER,
                HOUSE.SQUARE,
                ADDRESS.ID.as("address_id"),
                ADDRESS.COUNTRY,
                ADDRESS.CITY,
                ADDRESS.STREET
        ).from(HOUSE)
                .innerJoin(ADDRESS).on(HOUSE.ADDRESS_ID.eq(ADDRESS.ID))
                .where(HOUSE.ID.eq(id))
                .fetchOptional()
                .map(rec -> {
                    Address address = Address.builder()
                            .id(rec.get("address_id", UUID.class))
                            .country(rec.get(ADDRESS.COUNTRY))
                            .city(rec.get(ADDRESS.CITY))
                            .street(rec.get(ADDRESS.STREET))
                            .build();

                    return House.builder()
                            .id(rec.get(HOUSE.ID))
                            .number(rec.get(HOUSE.NUMBER))
                            .square(rec.get(HOUSE.SQUARE))
                            .address(address)
                            .build();
                });
    }

    @Override
    public List<House> findAll() {
        return dslContext.select(
                        HOUSE.ID,
                        HOUSE.NUMBER,
                        HOUSE.SQUARE,
                        ADDRESS.ID.as("address_id"),
                        ADDRESS.COUNTRY,
                        ADDRESS.CITY,
                        ADDRESS.STREET
                ).from(HOUSE)
                .innerJoin(ADDRESS).on(HOUSE.ADDRESS_ID.eq(ADDRESS.ID))
                .fetch()
                .map(rec -> {
                    Address address = Address.builder()
                            .id(rec.get("address_id", UUID.class))
                            .country(rec.get(ADDRESS.COUNTRY))
                            .city(rec.get(ADDRESS.CITY))
                            .street(rec.get(ADDRESS.STREET))
                            .build();

                    return House.builder()
                            .id(rec.get(HOUSE.ID))
                            .number(rec.get(HOUSE.NUMBER))
                            .square(rec.get(HOUSE.SQUARE))
                            .address(address)
                            .build();
                });
    }


    @Override
    public List<House> findAllByAddressId(UUID addressId) {
        return dslContext.select(
                        HOUSE.ID,
                        HOUSE.NUMBER,
                        HOUSE.SQUARE,
                        ADDRESS.ID.as("address_id"),
                        ADDRESS.COUNTRY,
                        ADDRESS.CITY,
                        ADDRESS.STREET
                ).from(HOUSE)
                .innerJoin(ADDRESS).on(HOUSE.ADDRESS_ID.eq(ADDRESS.ID))
                .where(HOUSE.ADDRESS_ID.eq(addressId))
                .fetch()
                .map(rec -> {
                    Address address = Address.builder()
                            .id(rec.get("address_id", UUID.class))
                            .country(rec.get(ADDRESS.COUNTRY))
                            .city(rec.get(ADDRESS.CITY))
                            .street(rec.get(ADDRESS.STREET))
                            .build();

                    return House.builder()
                            .id(rec.get(HOUSE.ID))
                            .number(rec.get(HOUSE.NUMBER))
                            .square(rec.get(HOUSE.SQUARE))
                            .address(address)
                            .build();
                });
    }

    @Override
    public House save(House house) {
        HouseRecord houseRecord = dslContext.insertInto(HOUSE, HOUSE.ID, HOUSE.NUMBER, HOUSE.SQUARE, HOUSE.ADDRESS_ID)
                .values(house.getId(), house.getNumber(), house.getSquare(), house.getAddress().getId())
                .onDuplicateKeyUpdate()
                .set(HOUSE.NUMBER, house.getNumber())
                .set(HOUSE.SQUARE, house.getSquare())
                .set(HOUSE.ADDRESS_ID, house.getAddress().getId())
                .returning()
                .fetchOneInto(HouseRecord.class);
        return houseMapper.map(houseRecord);
    }

    @Override
    public void deleteById(UUID id) {
        dslContext.deleteFrom(HOUSE)
                .where(HOUSE.ID.eq(id))
                .execute();
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(HOUSE)
                .execute();
    }

    @Override
    public void deleteAllByAddressId(UUID addressId) {
        dslContext.deleteFrom(HOUSE)
                .where(HOUSE.ADDRESS_ID.eq(addressId))
                .execute();
    }

}
