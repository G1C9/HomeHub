package com.example.homehub.repository.jooq;

import com.example.homehub.entity.Car;
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

@Repository
@Profile("jooq")
@RequiredArgsConstructor
public class JooqCarRepository implements CarRepository {

    private final DSLContext dslContext;

    private final JooqCarMapper carMapper;

    public Optional<Car> findById(UUID id) {
        return dslContext.selectFrom(CAR)
                .where(CAR.ID.eq(id))
                .fetchOptional()
                .map(carMapper::map);
    }

    @Override
    public List<Car> findAllByOwnerId(UUID ownerId) {
        return dslContext.selectFrom(CAR)
                .where(CAR.OWNER_ID.eq(ownerId))
                .fetch()
                .map(carMapper::map);
    }

    @Override
    public List<Car> findAll() {
        return dslContext.selectFrom(CAR)
                .fetch()
                .map(carMapper::map);
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
