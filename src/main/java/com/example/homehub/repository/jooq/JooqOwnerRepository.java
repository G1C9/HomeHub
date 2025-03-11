package com.example.homehub.repository.jooq;

import com.example.homehub.entity.Address;
import com.example.homehub.entity.Car;
import com.example.homehub.entity.House;
import com.example.homehub.entity.HouseOwners;
import com.example.homehub.entity.Owner;
import com.example.homehub.entity.Passport;
import com.example.homehub.mapper.jooq.JooqOwnerMapper;
import com.example.homehub.repository.OwnerRepository;
import com.example.homehub.tables.records.OwnerRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import static com.example.homehub.Tables.CAR;
import static com.example.homehub.Tables.OWNER;
import static com.example.homehub.Tables.HOUSE_OWNERS;
import static com.example.homehub.Tables.HOUSE;
import static com.example.homehub.Tables.ADDRESS;
import static com.example.homehub.Tables.PASSPORT;

@Repository
@Profile("jooq")
@RequiredArgsConstructor
public class JooqOwnerRepository implements OwnerRepository {

    private final DSLContext dslContext;

    private final JooqOwnerMapper ownerMapper;

    @Override
    public Optional<Owner> findById(UUID id) {
        return dslContext.select(
                OWNER.ID,
                OWNER.FIRST_NAME,
                OWNER.LAST_NAME,
                OWNER.GENDER,
                PASSPORT.ID.as("passport_id"),
                PASSPORT.SERIES,
                PASSPORT.NUMBER
                ).from(OWNER)
                .innerJoin(PASSPORT).on(OWNER.PASSPORT_ID.eq(PASSPORT.ID))
                .where(OWNER.ID.eq(id))
                .fetchOptional()
                .map(rec -> {
                    Passport passport = Passport.builder()
                            .id(rec.get("passport_id", UUID.class))
                            .series(rec.get(PASSPORT.SERIES))
                            .number(rec.get(PASSPORT.NUMBER))
                            .build();

                    return Owner.builder()
                            .id(rec.get(OWNER.ID))
                            .firstName(rec.get(OWNER.FIRST_NAME))
                            .lastName(rec.get(OWNER.LAST_NAME))
                            .gender(rec.get(OWNER.GENDER))
                            .passport(passport)
                            .build();
                });
    }

    @Override
    public List<Owner> findByStreet(String street) {
        return dslContext.select(
                        OWNER.ID.as("owner_id"),
                        OWNER.FIRST_NAME,
                        OWNER.LAST_NAME,
                        OWNER.GENDER,
                        PASSPORT.ID.as("passport_id"),
                        PASSPORT.SERIES,
                        PASSPORT.NUMBER.as("passport_number"),
                        HOUSE_OWNERS.ID,
                        HOUSE.ID.as("house_id"),
                        HOUSE.NUMBER,
                        HOUSE.SQUARE,
                        ADDRESS.ID.as("address_id"),
                        ADDRESS.COUNTRY,
                        ADDRESS.CITY,
                        ADDRESS.STREET
                ).from(OWNER)
                .innerJoin(PASSPORT).on(PASSPORT.ID.eq(OWNER.PASSPORT_ID))
                .innerJoin(HOUSE_OWNERS).on(HOUSE_OWNERS.OWNER_ID.eq(OWNER.ID))
                .innerJoin(HOUSE).on(HOUSE.ID.eq(HOUSE_OWNERS.HOUSE_ID))
                .innerJoin(ADDRESS).on(ADDRESS.ID.eq(HOUSE.ADDRESS_ID))
                .where(ADDRESS.STREET.eq(street))
                .fetch()
                .map(rec -> {
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

                    HouseOwners houseOwners = HouseOwners.builder()
                            .id(rec.get(HOUSE_OWNERS.ID))
                            .house(house)
                            .owner(owner)
                            .build();

                    return owner;
                });
    }

    @Override
    public List<Owner> findMaleWithCarAndHouse() {
        return dslContext.select(
                        OWNER.ID.as("owner_id"),
                        OWNER.FIRST_NAME,
                        OWNER.LAST_NAME,
                        OWNER.GENDER,
                        PASSPORT.ID.as("passport_id"),
                        PASSPORT.SERIES,
                        PASSPORT.NUMBER.as("passport_number"),
                        CAR.ID.as("car_id"),
                        CAR.BRAND,
                        CAR.MODEL,
                        HOUSE_OWNERS.ID,
                        HOUSE.ID.as("house_id"),
                        HOUSE.NUMBER,
                        HOUSE.SQUARE,
                        ADDRESS.ID.as("address_id"),
                        ADDRESS.COUNTRY,
                        ADDRESS.CITY,
                        ADDRESS.STREET
                ).from(OWNER)
                .innerJoin(PASSPORT).on(PASSPORT.ID.eq(OWNER.PASSPORT_ID))
                .innerJoin(CAR).on(CAR.OWNER_ID.eq(OWNER.ID))
                .innerJoin(HOUSE_OWNERS).on(HOUSE_OWNERS.OWNER_ID.eq(OWNER.ID))
                .innerJoin(HOUSE).on(HOUSE.ID.eq(HOUSE_OWNERS.HOUSE_ID))
                .innerJoin(ADDRESS).on(ADDRESS.ID.eq(HOUSE.ADDRESS_ID))
                .where(OWNER.GENDER.eq("Male"))
                .fetch()
                .map(rec -> {
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

                    Car car = Car.builder()
                            .id(rec.get("car_id", UUID.class))
                            .brand(rec.get(CAR.BRAND))
                            .model(rec.get(CAR.MODEL))
                            .owner(owner)
                            .build();

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

                    HouseOwners houseOwners = HouseOwners.builder()
                            .id(rec.get(HOUSE_OWNERS.ID))
                            .house(house)
                            .owner(owner)
                            .build();

                    return owner;
                });
    }

    @Override
    public List<Owner> findAll() {
        return dslContext.select(
                        OWNER.ID,
                        OWNER.FIRST_NAME,
                        OWNER.LAST_NAME,
                        OWNER.GENDER,
                        PASSPORT.ID.as("passport_id"),
                        PASSPORT.SERIES,
                        PASSPORT.NUMBER
                ).from(OWNER)
                .innerJoin(PASSPORT).on(OWNER.PASSPORT_ID.eq(PASSPORT.ID))
                .fetch()
                .map(rec -> {
                    Passport passport = Passport.builder()
                            .id(rec.get("passport_id", UUID.class))
                            .series(rec.get(PASSPORT.SERIES))
                            .number(rec.get(PASSPORT.NUMBER))
                            .build();

                    return Owner.builder()
                            .id(rec.get(OWNER.ID))
                            .firstName(rec.get(OWNER.FIRST_NAME))
                            .lastName(rec.get(OWNER.LAST_NAME))
                            .gender(rec.get(OWNER.GENDER))
                            .passport(passport)
                            .build();
                });
    }

    @Override
    public Owner save(Owner owner) {
        OwnerRecord ownerRecord = dslContext.insertInto(OWNER, OWNER.ID, OWNER.FIRST_NAME, OWNER.LAST_NAME, OWNER.GENDER, OWNER.PASSPORT_ID)
                .values(owner.getId(), owner.getFirstName(), owner.getLastName(), owner.getGender(), owner.getPassport().getId())
                .onDuplicateKeyUpdate()
                .set(OWNER.FIRST_NAME, owner.getFirstName())
                .set(OWNER.LAST_NAME, owner.getLastName())
                .set(OWNER.GENDER, owner.getGender())
                .set(OWNER.PASSPORT_ID, owner.getPassport().getId())
                .returning()
                .fetchOneInto(OwnerRecord.class);
        return ownerMapper.map(ownerRecord);
    }

    @Override
    public void deleteById(UUID id) {
        dslContext.deleteFrom(OWNER)
                .where(OWNER.ID.eq(id))
                .execute();
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(OWNER)
                .execute();
    }

}
