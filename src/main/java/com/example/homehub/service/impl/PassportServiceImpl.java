package com.example.homehub.service.impl;

import com.example.homehub.entity.Passport;
import com.example.homehub.repository.PassportRepository;
import com.example.homehub.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static com.example.homehub.constant.PassportUtilsConstant.MIN_SERIES;
import static com.example.homehub.constant.PassportUtilsConstant.MAX_SERIES;
import static com.example.homehub.constant.PassportUtilsConstant.MIN_NUMBER;
import static com.example.homehub.constant.PassportUtilsConstant.MAX_NUMBER;
import static com.example.homehub.constant.PassportUtilsConstant.ONE;
import java.security.SecureRandom;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;

    @Override
    public Passport getByOwnerId(UUID id) {
        log.info("GET PASSPORT BY OWNER");
        return passportRepository.getByOwnerId(id);
    }

    @Override
    public Passport getPassportByOwnerFirstName(String firstLetter) {
        log.info("GET PASSPORT BY OWNER FIRSTNAME");
        return passportRepository.getPassportByOwnerFirstLetter(firstLetter);
    }

    @Override
    public Passport create(Passport passport) {
        SecureRandom generateNumber = new SecureRandom();
        return passportRepository.save(Passport.builder()
                .id(UUID.randomUUID())
                .series(generateNumber.nextInt(MAX_SERIES - MIN_SERIES + ONE)
                        + MIN_SERIES)
                .number(generateNumber.nextInt(MAX_NUMBER - MIN_NUMBER + ONE)
                        + MIN_NUMBER)
                .build());
    }

    @Override
    public void delete(UUID id) {
        log.info("DELETE PASSPORT");
        passportRepository.deleteById(id);
    }

}
