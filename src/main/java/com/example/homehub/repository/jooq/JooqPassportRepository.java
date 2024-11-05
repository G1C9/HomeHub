package com.example.homehub.repository.jooq;

import com.example.homehub.entity.Passport;
import com.example.homehub.mapper.jooq.JooqPassportMapper;
import com.example.homehub.repository.PassportRepository;
import com.example.homehub.tables.records.PassportRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static com.example.homehub.Tables.OWNER;
import static com.example.homehub.Tables.PASSPORT;

@Repository
@Profile("jooq")
@RequiredArgsConstructor
public class JooqPassportRepository implements PassportRepository {

    private final DSLContext dslContext;

    private final JooqPassportMapper passportMapper;

    @Override
    public Passport getByOwnerId(UUID ownerId) {
        PassportRecord passportRecord = (PassportRecord) dslContext.select()
                .from(PASSPORT)
                .where(PASSPORT.owner().ID.eq(ownerId))
                .fetchOne();
        return passportMapper.map(passportRecord);
    }

    @Override
    public Passport getPassportByOwnerFirstLetter(String firstLetter) {
        PassportRecord passportRecord = (PassportRecord) dslContext.select()
                .from(PASSPORT)
                .join(OWNER).on(OWNER.PASSPORT_ID.eq(PASSPORT.ID))
                .where(OWNER.FIRST_NAME.like(firstLetter + "%"))
                .fetchOne();
        return passportMapper.map(passportRecord);
    }

    @Override
    public Passport save(Passport passport) {
        PassportRecord passportRecord = dslContext.insertInto(PASSPORT, PASSPORT.ID, PASSPORT.SERIES, PASSPORT.NUMBER)
                .values(passport.getId(), passport.getSeries(), passport.getNumber())
                .onDuplicateKeyUpdate()
                .set(PASSPORT.SERIES, passport.getSeries())
                .set(PASSPORT.NUMBER, passport.getNumber())
                .returning()
                .fetchOneInto(PassportRecord.class);
        return passportMapper.map(passportRecord);
    }

    @Override
    public void deleteById(UUID id) {
        dslContext.selectFrom(PASSPORT)
                .where(PASSPORT.ID.eq(id))
                .execute();
    }

}