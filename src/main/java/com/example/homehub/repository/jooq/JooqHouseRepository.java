package com.example.homehub.repository.jooq;

import com.example.homehub.constant.EntitiesConstant;
import com.example.homehub.entity.House;
import com.example.homehub.exception.IdNotFoundException;
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
import static com.example.homehub.Tables.HOUSE;

@Repository
@Profile("jooq")
@RequiredArgsConstructor
public class JooqHouseRepository implements HouseRepository {

    private final DSLContext dslContext;

    private final JooqHouseMapper houseMapper;

    @Override
    public Optional<House> findById(UUID id) {
        return Optional.of(dslContext.selectFrom(HOUSE)
                .where(HOUSE.ID.eq(id))
                .fetchOptional()
                .map(houseMapper::map)
                .orElseThrow(() -> new IdNotFoundException(EntitiesConstant.HOUSE, id)));
    }

    @Override
    public List<House> findAll() {
        return dslContext.selectFrom(HOUSE)
                .fetch()
                .map(houseMapper::map);
    }


    @Override
    public List<House> findAllByAddressId(UUID addressId) {
        return dslContext.selectFrom(HOUSE)
                .where(HOUSE.ADDRESS_ID.eq(addressId))
                .fetch()
                .map(houseMapper::map);
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
