package com.example.homehub.repository.jooq;

import com.example.homehub.entity.HouseOwners;
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
import static com.example.homehub.Tables.HOUSE_OWNERS;

@Repository
@Profile("jooq")
@RequiredArgsConstructor
public class JooqHouseOwnersRepository implements HouseOwnersRepository {

    private final DSLContext dslContext;

    private final JooqHouseOwnersMapper houseOwnersMapper;

    @Override
    public Optional<HouseOwners> findById(UUID id) {
        return dslContext.selectFrom(HOUSE_OWNERS)
                .where(HOUSE_OWNERS.ID.eq(id))
                .fetchOptional()
                .map(houseOwnersMapper::map);
    }

    @Override
    public Optional<HouseOwners> findHouseOwnersByHouseId(UUID id) {
        return dslContext.selectFrom(HOUSE_OWNERS)
                .where(HOUSE_OWNERS.HOUSE_ID.eq(id))
                .fetchOptional()
                .map(houseOwnersMapper::map);
    }

    @Override
    public Optional<HouseOwners> findHouseOwnersByOwnerId(UUID id) {
        return dslContext.selectFrom(HOUSE_OWNERS)
                .where(HOUSE_OWNERS.OWNER_ID.eq(id))
                .fetchOptional()
                .map(houseOwnersMapper::map);
    }

    @Override
    public List<HouseOwners> findAll() {
        return dslContext.selectFrom(HOUSE_OWNERS)
                .fetch()
                .map(houseOwnersMapper::map);
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
