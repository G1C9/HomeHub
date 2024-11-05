package com.example.homehub.controller;

import com.example.homehub.dto.rq.AddressRq;
import com.example.homehub.dto.rs.AddressRs;
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
import static com.example.homehub.constant.ApiConstant.ADDRESS;

@RestController
@RequestMapping(ADDRESS)
public interface AddressController {

    @GetMapping("/{id}")
    AddressRs getOne(@PathVariable UUID id);

    @GetMapping()
    List<AddressRs> getAll();

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    AddressRs create(@Valid @RequestBody AddressRq addressRq);

    @PutMapping("/{id}")
    AddressRs update(@Valid @RequestBody AddressRq addressRq, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id);

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAll();

}
