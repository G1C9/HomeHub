package com.example.homehub.service.impl;

import com.example.homehub.entity.Car;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.CarRepository;
import com.example.homehub.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import static com.example.homehub.constant.EntitiesConstant.CAR;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Override
    public Car getOne(UUID id) {
        logger.info("GET CAR WITH ID: {}", id);
        return carRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(CAR, id));
    }

    @Override
    public List<Car> findAllByOwnerId(UUID ownerId) {
        logger.info("FIND ALL CARS BY OWNER ID: {}", ownerId);
        return carRepository.findAllByOwnerId(ownerId);
    }

    @Override
    public List<Car> getAll() {
        logger.info("GET ALL CARS");
        return carRepository.findAll();
    }

    @Override
    public Car create(Car car) {
        logger.info("CREATE CAR");
        return carRepository.save(Car.builder()
                .id(UUID.randomUUID())
                .brand(car.getBrand())
                .model(car.getModel())
                .owner(car.getOwner())
                .build());
    }

    @Override
    public Car update(Car car, UUID id) {
        return carRepository.findById(id)
                .map(existingCar -> {
                    Car updatedCar = existingCar.toBuilder()
                            .brand(car.getBrand())
                            .model(car.getModel())
                            .owner(car.getOwner())
                            .build();
                    logger.info("UPDATE CAR WITH ID: {}", id);
                    return carRepository.save(updatedCar);
                })
                .orElseThrow(() -> new IdNotFoundException(CAR, id));
    }

    @Override
    public void delete(UUID id) {
        logger.info("DELETE CAR");
        carRepository.deleteById(id);
    }

    @Override
    public void deleteAllByOwnerId(UUID ownerId) {
        logger.info("DELETE ALL CARS BY OWNER ID: {}", ownerId);
        carRepository.deleteAllByOwnerId(ownerId);
    }

    @Override
    public void deleteAll() {
        logger.info("DELETE ALL CARS");
        carRepository.deleteAll();
    }

}
