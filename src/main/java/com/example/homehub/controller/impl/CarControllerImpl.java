package com.example.homehub.controller.impl;

import com.example.homehub.controller.CarController;
import com.example.homehub.dto.rq.CarRq;
import com.example.homehub.dto.rs.CarRs;
import com.example.homehub.mapper.controller.CarMapper;
import com.example.homehub.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CarControllerImpl implements CarController {

    private final CarService carService;

    private final CarMapper carMapper;

    @Override
    public CarRs getOne(UUID id) {
        return carMapper.map(carService.getOne(id));
    }

    @Override
    public List<CarRs> getAllByOwnerId(UUID id) {
        return carMapper.map(carService.findAllByOwnerId(id));
    }

    @Override
    public List<CarRs> getAll() {
        return carMapper.map(carService.getAll());
    }

    @Override
    public CarRs create(CarRq carRq) {
        return carMapper.map(carService.create(carMapper.map(carRq)));
    }

    @Override
    public CarRs update(CarRq carRq, UUID id) {
        return carMapper.map(carService.update(carMapper.map(carRq), id));
    }

    @Override
    public void delete(UUID id) {
        carService.delete(id);
    }

    @Override
    public void deleteAll() {
        carService.deleteAll();
    }

}
