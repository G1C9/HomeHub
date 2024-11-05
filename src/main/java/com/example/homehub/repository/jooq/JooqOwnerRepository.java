package com.example.homehub.repository.jooq;

import com.example.homehub.entity.Owner;
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

@Repository
@Profile("jooq")
@RequiredArgsConstructor
public class JooqOwnerRepository implements OwnerRepository {

    private final DSLContext dslContext;

    private final JooqOwnerMapper ownerMapper;

    @Override
    public Optional<Owner> findById(UUID id) {
        return dslContext.selectFrom(OWNER)
                .where(OWNER.ID.eq(id))
                .fetchOptional()
                .map(ownerMapper::map);
    }

    @Override
    public Owner getByStreet(String street) {
        OwnerRecord ownerRecord = (OwnerRecord) dslContext.select()
                .from(OWNER)
                .join(HOUSE_OWNERS).on(HOUSE_OWNERS.OWNER_ID.eq(OWNER.ID))
                .join(HOUSE).on(HOUSE.ID.eq(HOUSE_OWNERS.HOUSE_ID))
                .join(ADDRESS).on(HOUSE.ADDRESS_ID.eq(ADDRESS.ID))
                .where(ADDRESS.STREET.eq(street))
                .fetchOne();
        return ownerMapper.map(ownerRecord);
    }

    @Override
    public List<Owner> findMaleWithCarAndHouse() {
        Result<OwnerRecord> ownerRecord = (Result<OwnerRecord>) dslContext.select()
                .from(OWNER)
                .join(CAR).on(CAR.OWNER_ID.eq(OWNER.ID))
                .join(HOUSE_OWNERS).on(HOUSE_OWNERS.OWNER_ID.eq(OWNER.ID))
                .join(HOUSE).on(HOUSE_OWNERS.HOUSE_ID.eq(HOUSE.ID))
                .where(OWNER.GENDER.eq("Male"))
                .fetchInto(OwnerRecord.class);
        return ownerRecord.stream().map(ownerMapper::map).collect(Collectors.toList());
    }

    @Override
    public List<Owner> findAll() {
        return dslContext.selectFrom(OWNER)
                .fetch()
                .map(ownerMapper::map);
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
