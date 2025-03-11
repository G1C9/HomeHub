package com.example.homehub.repository.jooq;

import com.example.homehub.entity.Address;
import com.example.homehub.entity.House;
import com.example.homehub.entity.HouseOwners;
import com.example.homehub.entity.Owner;
import com.example.homehub.entity.Passport;
import com.example.homehub.mapper.jooq.JooqHouseOwnersMapper;
import com.example.homehub.repository.HouseOwnersRepository;
import com.example.homehub.tables.records.HouseOwnersRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.homehub.Tables.ADDRESS;
import static com.example.homehub.Tables.HOUSE;
import static com.example.homehub.Tables.HOUSE_OWNERS;
import static com.example.homehub.Tables.OWNER;
import static com.example.homehub.Tables.PASSPORT;

@Repository
@Profile("jooq")
@RequiredArgsConstructor
public class JooqHouseOwnersRepository implements HouseOwnersRepository {

    private final DSLContext dslContext;

    private final JooqHouseOwnersMapper houseOwnersMapper;

    @Override
    public Optional<HouseOwners> findById(UUID id) {
        return dslContext.select(
                        HOUSE_OWNERS.ID,
                        HOUSE.ID.as("house_id"),
                        HOUSE.NUMBER,
                        HOUSE.SQUARE,
                        ADDRESS.ID.as("address_id"),
                        ADDRESS.COUNTRY,
                        ADDRESS.CITY,
                        ADDRESS.STREET,
                        OWNER.ID.as("owner_id"),
                        OWNER.FIRST_NAME,
                        OWNER.LAST_NAME,
                        OWNER.GENDER,
                        PASSPORT.ID.as("passport_id"),
                        PASSPORT.SERIES,
                        PASSPORT.NUMBER.as("passport_number")
                ).from(HOUSE_OWNERS)
                .innerJoin(HOUSE).on(HOUSE.ID.eq(HOUSE_OWNERS.HOUSE_ID))
                .innerJoin(ADDRESS).on(ADDRESS.ID.eq(HOUSE.ADDRESS_ID))
                .innerJoin(OWNER).on(OWNER.ID.eq(HOUSE_OWNERS.OWNER_ID))
                .innerJoin(PASSPORT).on(PASSPORT.ID.eq(OWNER.PASSPORT_ID))
                .where(HOUSE_OWNERS.ID.eq(id))
                .fetchOptional()
                .map(rec -> {
                    Address address = Address.builder()
                            .id(rec.get("address_id", UUID.class))
                            .country(rec.get(ADDRESS.COUNTRY))
                            .city(rec.get(ADDRESS.CITY))
                            .street(rec.get(ADDRESS.STREET))
                            .build();

                    House house = House.builder()
                            .id(rec.get("house_id", UUID.class))
                            .number(rec.get(HOUSE.NUMBER))
                            .square(rec.get(HOUSE.SQUARE))
                            .address(address)
                            .build();

                    Passport passport = Passport.builder()
                            .id(rec.get("passport_id", UUID.class))
                            .series(rec.get(PASSPORT.SERIES))
                            .number(rec.get("passport_number", Integer.class))
                            .build();

                    Owner owner = Owner.builder()
                            .id(rec.get("owner_id", UUID.class))
                            .firstName(rec.get(OWNER.FIRST_NAME))
                            .lastName(rec.get(OWNER.LAST_NAME))
                            .gender(rec.get(OWNER.GENDER))
                            .passport(passport)
                            .build();

                    return HouseOwners.builder()
                            .id(rec.get(HOUSE_OWNERS.ID))
                            .house(house)
                            .owner(owner)
                            .build();
                });
    }

    @Override
    public Optional<HouseOwners> findHouseOwnersByHouseId(UUID id) {
        return dslContext.select(
                        HOUSE_OWNERS.ID,
                        HOUSE.ID.as("house_id"),
                        HOUSE.NUMBER,
                        HOUSE.SQUARE,
                        ADDRESS.ID.as("address_id"),
                        ADDRESS.COUNTRY,
                        ADDRESS.CITY,
                        ADDRESS.STREET,
                        OWNER.ID.as("owner_id"),
                        OWNER.FIRST_NAME,
                        OWNER.LAST_NAME,
                        OWNER.GENDER,
                        PASSPORT.ID.as("passport_id"),
                        PASSPORT.SERIES,
                        PASSPORT.NUMBER.as("passport_number")
                ).from(HOUSE_OWNERS)
                .innerJoin(HOUSE).on(HOUSE.ID.eq(HOUSE_OWNERS.HOUSE_ID))
                .innerJoin(ADDRESS).on(ADDRESS.ID.eq(HOUSE.ADDRESS_ID))
                .innerJoin(OWNER).on(OWNER.ID.eq(HOUSE_OWNERS.OWNER_ID))
                .innerJoin(PASSPORT).on(PASSPORT.ID.eq(OWNER.PASSPORT_ID))
                .where(HOUSE_OWNERS.HOUSE_ID.eq(id))
                .fetchOptional()
                .map(rec -> {
                    Address address = Address.builder()
                            .id(rec.get("address_id", UUID.class))
                            .country(rec.get(ADDRESS.COUNTRY))
                            .city(rec.get(ADDRESS.CITY))
                            .street(rec.get(ADDRESS.STREET))
                            .build();

                    House house = House.builder()
                            .id(rec.get("house_id", UUID.class))
                            .number(rec.get(HOUSE.NUMBER))
                            .square(rec.get(HOUSE.SQUARE))
                            .address(address)
                            .build();

                    Passport passport = Passport.builder()
                            .id(rec.get("passport_id", UUID.class))
                            .series(rec.get(PASSPORT.SERIES))
                            .number(rec.get("passport_number", Integer.class))
                            .build();

                    Owner owner = Owner.builder()
                            .id(rec.get("owner_id", UUID.class))
                            .firstName(rec.get(OWNER.FIRST_NAME))
                            .lastName(rec.get(OWNER.LAST_NAME))
                            .gender(rec.get(OWNER.GENDER))
                            .passport(passport)
                            .build();

                    return HouseOwners.builder()
                            .id(rec.get(HOUSE_OWNERS.ID))
                            .house(house)
                            .owner(owner)
                            .build();
                });
    }

    @Override
    public Optional<HouseOwners> findHouseOwnersByOwnerId(UUID id) {
        return dslContext.select(
                        HOUSE_OWNERS.ID,
                        HOUSE.ID.as("house_id"),
                        HOUSE.NUMBER,
                        HOUSE.SQUARE,
                        ADDRESS.ID.as("address_id"),
                        ADDRESS.COUNTRY,
                        ADDRESS.CITY,
                        ADDRESS.STREET,
                        OWNER.ID.as("owner_id"),
                        OWNER.FIRST_NAME,
                        OWNER.LAST_NAME,
                        OWNER.GENDER,
                        PASSPORT.ID.as("passport_id"),
                        PASSPORT.SERIES,
                        PASSPORT.NUMBER.as("passport_number")
                ).from(HOUSE_OWNERS)
                .innerJoin(HOUSE).on(HOUSE.ID.eq(HOUSE_OWNERS.HOUSE_ID))
                .innerJoin(ADDRESS).on(ADDRESS.ID.eq(HOUSE.ADDRESS_ID))
                .innerJoin(OWNER).on(OWNER.ID.eq(HOUSE_OWNERS.OWNER_ID))
                .innerJoin(PASSPORT).on(PASSPORT.ID.eq(OWNER.PASSPORT_ID))
                .where(HOUSE_OWNERS.OWNER_ID.eq(id))
                .fetchOptional()
                .map(rec -> {
                    Address address = Address.builder()
                            .id(rec.get("address_id", UUID.class))
                            .country(rec.get(ADDRESS.COUNTRY))
                            .city(rec.get(ADDRESS.CITY))
                            .street(rec.get(ADDRESS.STREET))
                            .build();

                    House house = House.builder()
                            .id(rec.get("house_id", UUID.class))
                            .number(rec.get(HOUSE.NUMBER))
                            .square(rec.get(HOUSE.SQUARE))
                            .address(address)
                            .build();

                    Passport passport = Passport.builder()
                            .id(rec.get("passport_id", UUID.class))
                            .series(rec.get(PASSPORT.SERIES))
                            .number(rec.get("passport_number", Integer.class))
                            .build();

                    Owner owner = Owner.builder()
                            .id(rec.get("owner_id", UUID.class))
                            .firstName(rec.get(OWNER.FIRST_NAME))
                            .lastName(rec.get(OWNER.LAST_NAME))
                            .gender(rec.get(OWNER.GENDER))
                            .passport(passport)
                            .build();

                    return HouseOwners.builder()
                            .id(rec.get(HOUSE_OWNERS.ID))
                            .house(house)
                            .owner(owner)
                            .build();
                });
    }

    @Override
    public List<HouseOwners> findAll() {
        return dslContext.select(
                        HOUSE_OWNERS.ID,
                        HOUSE.ID.as("house_id"),
                        HOUSE.NUMBER,
                        HOUSE.SQUARE,
                        ADDRESS.ID.as("address_id"),
                        ADDRESS.COUNTRY,
                        ADDRESS.CITY,
                        ADDRESS.STREET,
                        OWNER.ID.as("owner_id"),
                        OWNER.FIRST_NAME,
                        OWNER.LAST_NAME,
                        OWNER.GENDER,
                        PASSPORT.ID.as("passport_id"),
                        PASSPORT.SERIES,
                        PASSPORT.NUMBER.as("passport_number")
                ).from(HOUSE_OWNERS)
                .innerJoin(HOUSE).on(HOUSE.ID.eq(HOUSE_OWNERS.HOUSE_ID))
                .innerJoin(ADDRESS).on(ADDRESS.ID.eq(HOUSE.ADDRESS_ID))
                .innerJoin(OWNER).on(OWNER.ID.eq(HOUSE_OWNERS.OWNER_ID))
                .innerJoin(PASSPORT).on(PASSPORT.ID.eq(OWNER.PASSPORT_ID))
                .fetch()
                .map(rec -> {
                    Address address = Address.builder()
                            .id(rec.get("address_id", UUID.class))
                            .country(rec.get(ADDRESS.COUNTRY))
                            .city(rec.get(ADDRESS.CITY))
                            .street(rec.get(ADDRESS.STREET))
                            .build();

                    House house = House.builder()
                            .id(rec.get("house_id", UUID.class))
                            .number(rec.get(HOUSE.NUMBER))
                            .square(rec.get(HOUSE.SQUARE))
                            .address(address)
                            .build();

                    Passport passport = Passport.builder()
                            .id(rec.get("passport_id", UUID.class))
                            .series(rec.get(PASSPORT.SERIES))
                            .number(rec.get("passport_number", Integer.class))
                            .build();

                    Owner owner = Owner.builder()
                            .id(rec.get("owner_id", UUID.class))
                            .firstName(rec.get(OWNER.FIRST_NAME))
                            .lastName(rec.get(OWNER.LAST_NAME))
                            .gender(rec.get(OWNER.GENDER))
                            .passport(passport)
                            .build();

                    return HouseOwners.builder()
                            .id(rec.get(HOUSE_OWNERS.ID))
                            .house(house)
                            .owner(owner)
                            .build();
                });
    }

    @Override
    public HouseOwners save(HouseOwners houseOwners) {
        HouseOwnersRecord houseOwnersRecord = dslContext.insertInto(HOUSE_OWNERS, HOUSE_OWNERS.ID, HOUSE_OWNERS.HOUSE_ID, HOUSE_OWNERS.OWNER_ID)
                .values(houseOwners.getId(), houseOwners.getHouse().getId(), houseOwners.getOwner().getId())
                .onDuplicateKeyUpdate()
                .set(HOUSE_OWNERS.HOUSE_ID, houseOwners.getHouse().getId())
                .set(HOUSE_OWNERS.OWNER_ID, houseOwners.getOwner().getId())
                .returning()
                .fetchOneInto(HouseOwnersRecord.class);
        return houseOwnersMapper.map(houseOwnersRecord);
    }

    @Override
    public void deleteById(UUID id) {
        dslContext.deleteFrom(HOUSE_OWNERS)
                .where(HOUSE_OWNERS.ID.eq(id))
                .execute();
    }

    @Override
    public void deleteByHouseId(UUID houseID) {
        dslContext.deleteFrom(HOUSE_OWNERS)
                .where(HOUSE_OWNERS.HOUSE_ID.eq(houseID))
                .execute();
    }

    @Override
    public void deleteByOwnerId(UUID ownerId) {
        dslContext.deleteFrom(HOUSE_OWNERS)
                .where(HOUSE_OWNERS.OWNER_ID.eq(ownerId))
                .execute();
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(HOUSE_OWNERS)
                .execute();
    }

}
