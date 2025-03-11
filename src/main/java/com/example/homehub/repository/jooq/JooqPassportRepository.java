package com.example.homehub.repository.jooq;

import com.example.homehub.entity.Owner;
import com.example.homehub.entity.Passport;
import com.example.homehub.mapper.jooq.JooqPassportMapper;
import com.example.homehub.repository.PassportRepository;
import com.example.homehub.tables.records.PassportRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Objects;
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
        return Objects.requireNonNull(dslContext.select(
                                PASSPORT.ID.as("passport_id"),
                                PASSPORT.SERIES,
                                PASSPORT.NUMBER,
                                OWNER.ID.as("owner_id"),
                                OWNER.FIRST_NAME,
                                OWNER.LAST_NAME,
                                OWNER.GENDER
                        ).from(PASSPORT)
                        .innerJoin(OWNER).on(OWNER.PASSPORT_ID.eq(PASSPORT.ID))
                        .where(OWNER.ID.eq(ownerId))
                        .fetchOne())
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

                    return passport;
                });
    }

    @Override
    public Passport getPassportByOwnerFirstLetter(String firstLetter) {
        return Objects.requireNonNull(dslContext.select(
                                PASSPORT.ID.as("passport_id"),
                                PASSPORT.SERIES,
                                PASSPORT.NUMBER,
                                OWNER.ID.as("owner_id"),
                                OWNER.FIRST_NAME,
                                OWNER.LAST_NAME,
                                OWNER.GENDER
                        ).from(PASSPORT)
                        .innerJoin(OWNER).on(OWNER.PASSPORT_ID.eq(PASSPORT.ID))
                        .where(OWNER.FIRST_NAME.like(firstLetter + "%"))
                        .fetchOne())
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

                    return passport;
                });
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