package com.example.homehub.controller;

import com.example.homehub.dto.rq.OwnerRq;
import com.example.homehub.dto.rs.OwnerRs;
import com.example.homehub.entity.Owner;
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
import static com.example.homehub.constant.ApiConstant.OWNER;

@RestController
@RequestMapping(OWNER)
public interface OwnerController {

    @GetMapping("/{id}")
    OwnerRs getOne(@PathVariable UUID id);

    @GetMapping("/all/{street}")
    List<OwnerRs> getByStreet(@PathVariable String street);

    @GetMapping("/property")
    List<OwnerRs> findMaleWithCarAndHouse();

    @GetMapping()
    List<OwnerRs> getAll();

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    OwnerRs create(@RequestBody @Valid OwnerRq ownerRq);

    @PutMapping("/{id}")
    OwnerRs update(@RequestBody @Valid OwnerRq ownerRq, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id);

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAll();

}
