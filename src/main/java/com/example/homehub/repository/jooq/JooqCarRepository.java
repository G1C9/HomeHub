package com.example.homehub.repository.jooq;

import com.example.homehub.entity.Car;
import com.example.homehub.entity.Owner;
import com.example.homehub.entity.Passport;
import com.example.homehub.mapper.jooq.JooqCarMapper;
import com.example.homehub.repository.CarRepository;
import com.example.homehub.tables.records.CarRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.example.homehub.Tables.CAR;
import static com.example.homehub.Tables.OWNER;
import static com.example.homehub.Tables.PASSPORT;

@Repository
@Profile("jooq")
@RequiredArgsConstructor
public class JooqCarRepository implements CarRepository {

    private final DSLContext dslContext;

    private final JooqCarMapper carMapper;

    public Optional<Car> findById(UUID id) {
        return dslContext.select(
                CAR.ID.as("car_id"),
                CAR.BRAND,
                CAR.MODEL,
                OWNER.ID.as("owner_id"),
                OWNER.FIRST_NAME,
                OWNER.LAST_NAME,
                OWNER.GENDER,
                PASSPORT.ID.as("passport_id"),
                PASSPORT.SERIES,
                PASSPORT.NUMBER
        ).from(CAR)
                .innerJoin(OWNER).on(CAR.OWNER_ID.eq(OWNER.ID))
                .innerJoin(PASSPORT).on(OWNER.PASSPORT_ID.eq(PASSPORT.ID))
                .where(CAR.ID.eq(id))
                .fetchOptional()
                .map(rec -> {
                    Passport passport = Passport.builder()
                            .id(rec.get("passport_id", UUID.class))
                            .series(rec.get(PASSPORT.SERIES))
                            .number(rec.get(PASSPORT.NUMBER))
                            .build();

                    Owner owner = Owner.builder()
                            .id(rec.get("owner_id", UUID.class))
                            .firstName(rec.get(OWNER.FIRST_NAME))
                            .lastName(rec.get(OWNER.LAST_NAME))
                            .gender(rec.get(OWNER.GENDER))
                            .passport(passport)
                            .build();

                    return Car.builder()
                            .id(rec.get("car_id", UUID.class))
                            .brand(rec.get(CAR.BRAND))
                            .model(rec.get(CAR.MODEL))
                            .owner(owner)
                            .build();
                });
    }

    @Override
    public List<Car> findAllByOwnerId(UUID ownerId) {
        return dslContext.select(
                        CAR.ID.as("car_id"),
                        CAR.BRAND,
                        CAR.MODEL,
                        OWNER.ID.as("owner_id"),
                        OWNER.FIRST_NAME,
                        OWNER.LAST_NAME,
                        OWNER.GENDER,
                        PASSPORT.ID.as("passport_id"),
                        PASSPORT.SERIES,
                        PASSPORT.NUMBER
                ).from(CAR)
                .innerJoin(OWNER).on(CAR.OWNER_ID.eq(OWNER.ID))
                .innerJoin(PASSPORT).on(OWNER.PASSPORT_ID.eq(PASSPORT.ID))
                .where(CAR.OWNER_ID.eq(ownerId))
                .fetch()
                .map(rec -> {
                    Passport passport = Passport.builder()
                            .id(rec.get("passport_id", UUID.class))
                            .series(rec.get(PASSPORT.SERIES))
                            .number(rec.get(PASSPORT.NUMBER))
                            .build();

                    Owner owner = Owner.builder()
                            .id(rec.get("owner_id", UUID.class))
                            .firstName(rec.get(OWNER.FIRST_NAME))
                            .lastName(rec.get(OWNER.LAST_NAME))
                            .gender(rec.get(OWNER.GENDER))
                            .passport(passport)
                            .build();

                    return Car.builder()
                            .id(rec.get("car_id", UUID.class))
                            .brand(rec.get(CAR.BRAND))
                            .model(rec.get(CAR.MODEL))
                            .owner(owner)
                            .build();
                });
    }

    @Override
    public List<Car> findAll() {
        return dslContext.select(
                        CAR.ID.as("car_id"),
                        CAR.BRAND,
                        CAR.MODEL,
                        OWNER.ID.as("owner_id"),
                        OWNER.FIRST_NAME,
                        OWNER.LAST_NAME,
                        OWNER.GENDER,
                        PASSPORT.ID.as("passport_id"),
                        PASSPORT.SERIES,
                        PASSPORT.NUMBER
                ).from(CAR)
                .innerJoin(OWNER).on(CAR.OWNER_ID.eq(OWNER.ID))
                .innerJoin(PASSPORT).on(OWNER.PASSPORT_ID.eq(PASSPORT.ID))
                .fetch()
                .map(rec -> {
                    Passport passport = Passport.builder()
                            .id(rec.get("passport_id", UUID.class))
                            .series(rec.get(PASSPORT.SERIES))
                            .number(rec.get(PASSPORT.NUMBER))
                            .build();

                    Owner owner = Owner.builder()
                            .id(rec.get("owner_id", UUID.class))
                            .firstName(rec.get(OWNER.FIRST_NAME))
                            .lastName(rec.get(OWNER.LAST_NAME))
                            .gender(rec.get(OWNER.GENDER))
                            .passport(passport)
                            .build();

                    return Car.builder()
                            .id(rec.get("car_id", UUID.class))
                            .brand(rec.get(CAR.BRAND))
                            .model(rec.get(CAR.MODEL))
                            .owner(owner)
                            .build();
                });
    }

    @Override
    public Car save(Car car) {
        CarRecord carRecord = dslContext.insertInto(CAR, CAR.ID, CAR.BRAND, CAR.MODEL, CAR.OWNER_ID)
                .values(car.getId(), car.getBrand(), car.getModel(), car.getOwner().getId())
                .onDuplicateKeyUpdate()
                .set(CAR.BRAND, car.getBrand())
                .set(CAR.MODEL, car.getModel())
                .set(CAR.OWNER_ID, car.getOwner().getId())
                .returning()
                .fetchOneInto(CarRecord.class);
        return carMapper.map(carRecord);
    }

    @Override
    public void deleteById(UUID id) {
        dslContext.deleteFrom(CAR)
                .where(CAR.ID.eq(id))
                .execute();
    }

    @Override
    public void deleteAllByOwnerId(UUID id) {
        dslContext.deleteFrom(CAR)
                .where(CAR.OWNER_ID.eq(id))
                .execute();
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(CAR)
                .execute();
    }

}
