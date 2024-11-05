package com.example.homehub.controller;

import com.example.homehub.dto.rq.CarRq;
import com.example.homehub.dto.rs.CarRs;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;
import static com.example.homehub.constant.ApiConstant.CAR;
import static com.example.homehub.constant.ApiConstant.OWNER;

@RestController
@RequestMapping(CAR)
public interface CarController {

    @GetMapping("/{id}")
    CarRs getOne(@PathVariable UUID id);

    @GetMapping(OWNER + "/{id}")
    List<CarRs> getAllByOwnerId(@PathVariable UUID id);

    @GetMapping()
    List<CarRs> getAll();

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    CarRs create(@RequestBody @Valid CarRq carRq);

    @PutMapping("/{id}")
    CarRs update(@RequestBody @Valid CarRq carRq, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id);

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAll();

}
