package com.example.homehub.controller;

import com.example.homehub.dto.rq.HouseOwnersRq;
import com.example.homehub.dto.rs.HouseOwnersRs;
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
import static com.example.homehub.constant.ApiConstant.OWNER;

@RestController
@RequestMapping(HOUSE + OWNER)
public interface HouseOwnersController {

    @GetMapping("/{id}")
    HouseOwnersRs getOne(@PathVariable UUID id);

    @GetMapping()
    List<HouseOwnersRs> getAll();

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    HouseOwnersRs create(@RequestBody @Valid HouseOwnersRq houseOwnersRq);

    @PutMapping("/{id}")
    HouseOwnersRs update(@RequestBody @Valid HouseOwnersRq houseOwnersRq, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id);

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAll();

}
