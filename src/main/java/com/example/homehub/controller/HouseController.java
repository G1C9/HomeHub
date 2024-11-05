package com.example.homehub.controller;

import com.example.homehub.dto.rq.HouseRq;
import com.example.homehub.dto.rs.HouseRs;
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
import static com.example.homehub.constant.ApiConstant.HOUSE;

@RestController
@RequestMapping(HOUSE)
public interface HouseController {

    @GetMapping("/{id}")
    HouseRs getOne(@PathVariable UUID id);

    @GetMapping()
    List<HouseRs> getAll();

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    HouseRs create(@RequestBody @Valid HouseRq houseRq);

    @PutMapping("/{id}")
    HouseRs update(@RequestBody @Valid HouseRq houseRq, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id);

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAll();

}
