package com.redis.project.api.controller;

import com.redis.project.api.dto.CustomerDTO;
import com.redis.project.domain.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/redis")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> save(@RequestBody CustomerDTO customerDTO) {
        customerService.saveToCache(customerDTO.getId(), customerDTO, 60); // 30 seconds
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") String id){
        return ResponseEntity.ok().body(customerService.getFromCache(id));
    }

    @DeleteMapping("/clear/{id}")
    public ResponseEntity<Void> clear(@PathVariable("id") String id) {
        customerService.clearCache(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
