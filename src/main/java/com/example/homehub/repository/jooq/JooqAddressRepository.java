package com.example.homehub.repository.jooq;

import com.example.homehub.constant.EntitiesConstant;
import com.example.homehub.entity.Address;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.mapper.jooq.JooqAddressMapper;
import com.example.homehub.repository.AddressRepository;
import com.example.homehub.tables.records.AddressRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.example.homehub.Tables.ADDRESS;

@Repository
@Profile("jooq")
@RequiredArgsConstructor
public class JooqAddressRepository implements AddressRepository {

    private final DSLContext dslContext;

    private final JooqAddressMapper addressMapper;

    @Override
    public Optional<Address> findById(UUID id) {
        return Optional.of(dslContext.selectFrom(ADDRESS)
                .where(ADDRESS.ID.eq(id))
                .fetchOptional()
                .map(addressMapper::map)
                .orElseThrow(() -> new IdNotFoundException(EntitiesConstant.ADDRESS, id)));
    }

    @Override
    public List<Address> findAll() {
        return dslContext.selectFrom(ADDRESS)
                .fetch()
                .map(addressMapper::map);
    }

    @Override
    public Address save(Address address) {
        AddressRecord addressRecord = dslContext.insertInto(ADDRESS, ADDRESS.ID, ADDRESS.COUNTRY, ADDRESS.CITY, ADDRESS.STREET)
                .values(address.getId(), address.getCountry(), address.getCity(), address.getStreet())
                .onDuplicateKeyUpdate()
                .set(ADDRESS.COUNTRY, address.getCountry())
                .set(ADDRESS.CITY, address.getCity())
                .set(ADDRESS.STREET, address.getStreet())
                .returning()
                .fetchOneInto(AddressRecord.class);
        return addressMapper.map(addressRecord);
    }

    @Override
    public void deleteById(UUID id) {
        dslContext.deleteFrom(ADDRESS)
                .where(ADDRESS.ID.eq(id))
                .execute();
    }

    @Override
    public void deleteAll() {
        dslContext.deleteFrom(ADDRESS)
                .execute();
    }

}
