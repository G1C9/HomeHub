package com.example.homehub.service;

import com.example.homehub.entity.Car;
import com.example.homehub.entity.Owner;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.CarRepository;
import com.example.homehub.service.impl.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceUnitTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    public void testGetOneSuccess() {
        Car car = Car.builder()
                .id(UUID.randomUUID())
                .build();

        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        Car result = carService.getOne(car.getId());

        assertNotNull(result);
        assertEquals(car.getId(), result.getId());

        verify(carRepository, times(1)).findById(car.getId());
    }

    @Test
    public void testGetOneNotFound() {
        UUID carId = UUID.randomUUID();

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> carService.getOne(carId));

        verify(carRepository, times(1)).findById(carId);
    }

    @Test
    public void TestFindAllByOwnerId() {
        Owner owner = Owner.builder()
                .id(UUID.randomUUID())
                .build();

        List<Car> cars = List.of(
                Car.builder().id(UUID.randomUUID()).owner(owner).build(),
                Car.builder().id(UUID.randomUUID()).owner(owner).build()
        );

        when(carRepository.findAllByOwnerId(owner.getId())).thenReturn(cars);

        List<Car> result = carService.findAllByOwnerId(owner.getId());

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(carRepository, times(1)).findAllByOwnerId(owner.getId());
    }

    @Test
    public void testGetAll() {
        List<Car> cars = List.of(
                Car.builder().id(UUID.randomUUID()).build(),
                Car.builder().id(UUID.randomUUID()).build()
        );

        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void testCreate() {
        Car car = new Car();

        Car savedCar = Car.builder()
                .id(UUID.randomUUID())
                .build();

        when(carRepository.save(any(Car.class))).thenReturn(savedCar);

        Car result = carService.create(car);

        assertNotNull(result);
        assertEquals(savedCar.getId(), result.getId());

        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    public void testUpdateSuccess() {
        Car car = Car.builder()
                .id(UUID.randomUUID())
                .brand("BMW")
                .model("M5")
                .owner(new Owner())
                .build();

        Car newCar = new Car();

        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenReturn(newCar);

        Car result = carService.update(newCar ,car.getId());

        assertNotNull(result);
        assertEquals(newCar.getId(), result.getId());
        assertEquals(newCar.getBrand(), result.getBrand());
        assertEquals(newCar.getModel(), result.getModel());
        assertEquals(newCar.getOwner(), result.getOwner());

        verify(carRepository, times(1)).findById(car.getId());
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    public void testUpdateNotFound() {
        Car car = Car.builder()
                .id(UUID.randomUUID())
                .build();

        when(carRepository.findById(car.getId())).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> carService.update(car, car.getId()));

        verify(carRepository, times(1)).findById(car.getId());
        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    public void testDelete() {
        Car car = Car.builder()
                .id(UUID.randomUUID())
                .build();

        carService.delete(car.getId());

        verify(carRepository, times(1)).deleteById(car.getId());
    }

    @Test
    public void testDeleteAllByOwnerId() {
        Owner owner = Owner.builder()
                .id(UUID.randomUUID())
                .build();

        Car car = Car.builder()
                .id(UUID.randomUUID())
                .owner(owner)
                .build();

        carService.deleteAllByOwnerId(car.getOwner().getId());

        verify(carRepository, times(1)).deleteAllByOwnerId(car.getOwner().getId());
    }

    @Test
    public void testDeleteAll() {
        carService.deleteAll();

        verify(carRepository, times(1)).deleteAll();
    }

}
